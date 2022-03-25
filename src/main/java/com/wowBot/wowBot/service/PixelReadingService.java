package com.wowBot.wowBot.service;

import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class pixelReadingService {

    private GameActionService gameActionService;
    private Robot robot;
    private ScreenshotService screenshotService;

    public pixelReadingService(GameActionService gameActionService, ScreenshotService screenshotService) {
        this.gameActionService = gameActionService;
        this.screenshotService = screenshotService;
        try {
            this.robot = new Robot();
        }
        catch (AWTException e) {}
        this.screenshotService = screenshotService;
    }

    public boolean havePM() {
        /*Rectangle rectangle = new Rectangle((int) (ScreenshotService.SCREEN_BOUNDS.x + ScreenshotService.SCREEN_BOUNDS.width * 0.6),
                (int) (ScreenshotService.SCREEN_BOUNDS.height * 0.6), 10, 10);
        screenshotService.screenshot(rectangle);*/
        //code
        Color color = robot.getPixelColor(ScreenshotService.SCREEN_BOUNDS.x + ScreenshotService.SCREEN_BOUNDS.width/2,
                ScreenshotService.SCREEN_BOUNDS.height/2);
        return color == new Color(1);
    }

    public boolean charIsDead() {
        //code
        Color color = robot.getPixelColor(ScreenshotService.SCREEN_BOUNDS.x + ScreenshotService.SCREEN_BOUNDS.width/2,
                ScreenshotService.SCREEN_BOUNDS.height/2);
        return color == new Color(1);
    }

    public boolean haveProblem() {
        boolean result = havePM() | charIsDead();
        if (result) {
            screenshotService.screenshot();
            gameActionService.exit();
        }
        return result;
    }
}
