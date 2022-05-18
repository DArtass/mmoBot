package com.wowBot.wowBot.service;

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
    private int tolerance = 10;
    private long checkSleep = 200L;
    private int missBeforePeck = 3;

    public FishingService(ScreenshotService screenshotService, MatMapper matMapper, TemplateService templateService) {
        this.screenshotService = screenshotService;
        this.matMapper = matMapper;
        this.templateService = templateService;
    }

    public Point getFloatXY() {
        byte[] floatAreaBytes = screenshotService.screenshot(screenshotService.getFloatScreenBounds());
        Mat floatAreaMat = matMapper.map(floatAreaBytes);
        Mat floatMap = matMapper.map(TemplateService.FLOAT_BYTES);

        Core.MinMaxLocResult matchResult = templateService.matchTemplate(floatAreaMat, floatMap);
        Point maxLoc = new Point(matchResult.maxLoc.x +  screenshotService.getFloatBounds().x, matchResult.maxLoc.y);

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
        Long time = System.currentTimeMillis();
        boolean result = false;
        while (System.currentTimeMillis() - time < 21000 & !result) {
            Point floatxy = getFloatXY();

            if (!nearby(xy, floatxy)) {
                int triesCount = missBeforePeck * 2;

                int missCount = 1;
                int i = triesCount;
                while(i > 0) {
                    i--;
                    try {
                        Thread.sleep(checkSleep / triesCount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Point dxy = getFloatXY();
                    if (!nearby(dxy, floatxy)) {
                        missCount++;
                    }
                }
                result = missCount >= missBeforePeck;
            }

            System.out.println("Координаты поплавка " + screenshotService.pointToString(floatxy));
            try {
                Thread.sleep(checkSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Point getFloatXYGuaranteed() {
        return getFloatXYGuaranteed(5);
    }

    private boolean nearby(Point point1, Point point2) {
        return Math.abs(point1.x - point2.x) < tolerance & Math.abs(point1.y - point2.y) < tolerance;
    }
}
