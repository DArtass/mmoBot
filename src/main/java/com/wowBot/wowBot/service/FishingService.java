package com.wowBot.wowBot.service;

import com.wowBot.wowBot.gameState.GameState;
import com.wowBot.wowBot.mapper.MatMapper;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.springframework.stereotype.Service;

@Service
public class FishingService {
    private ScreenshotService screenshotService;
    private MatMapper matMapper;
    private TemplateService templateService;
    private GameState gameState;


    public FishingService(ScreenshotService screenshotService, MatMapper matMapper, TemplateService templateService,
                          GameState gameState) {
        this.screenshotService = screenshotService;
        this.matMapper = matMapper;
        this.templateService = templateService;
        this.gameState = gameState;
    }

    public Point getFloatXY() {
        byte[] floatAreaBytes = screenshotService.screenshot(gameState.getFloatScreenBounds());
        Mat floatAreaMat = matMapper.map(floatAreaBytes);
        Mat floatMap = matMapper.map(TemplateService.FLOAT_BYTES);

        Core.MinMaxLocResult matchResult = templateService.matchTemplate(floatAreaMat, floatMap);
        Point maxLoc = new Point(matchResult.maxLoc.x +  gameState.getFloatBounds().x, matchResult.maxLoc.y);

        return screenshotService.convertToGlobal(maxLoc);
    }

    public Point getFloatXYGuaranteed(int times) {
        int timesRemain = times;
        Point xy = getFloatXY();
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Point xy1 = getFloatXY();
        boolean result = nearby(xy, xy1);

        while (!result & timesRemain > 0) {
            xy = getFloatXY();
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            xy1 = getFloatXY();
            timesRemain--;
            result = nearby(xy, xy1);
        }

        if (result)
            return xy;
        else
            return new Point(-1, -1);
    }

    public boolean checkPecking(Point xy) {
        long startTime = System.currentTimeMillis();
        long currTime;
        long duration = 0;
        boolean result = false;
        while (!gameState.isPaused() && (duration < 6000 || duration < 16500 && !result)) {
            Point floatxy = getFloatXY();

            if (!nearby(xy, floatxy)) {
                int triesCount = gameState.getMissBeforePeck() * 2;

                int missCount = 1;
                int i = triesCount;
                while(i > 0) {
                    i--;
                    try {
                        Thread.sleep(gameState.getCheckSleep() / triesCount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Point dxy = getFloatXY();
                    if (!nearby(dxy, xy)) {
                        missCount++;
                        System.out.println("Not find Coords: 1." + screenshotService.pointToString(dxy) + " 2." +
                                screenshotService.pointToString(xy));
                    }
                }
                result = missCount >= gameState.getMissBeforePeck() || result;
            }

            System.out.println("Coords: " + screenshotService.pointToString(floatxy) + "; Time left: " + duration/1000);
            try {
                Thread.sleep(gameState.getCheckSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currTime = System.currentTimeMillis();
            duration = currTime - startTime;
        }
        return result;
    }

    public Point getFloatXYGuaranteed() {
        return getFloatXYGuaranteed(5);
    }

    private boolean nearby(Point point1, Point point2) {
        return Math.abs(point1.y - point2.y) < gameState.getToleranceY() & Math.abs(point1.x - point2.x) < gameState.getToleranceX();
    }
}
