package com.wowBot.wowBot.service;

import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class PixelReadingService {

    private GameActionService gameActionService;
    private Robot robot;
    private ScreenshotService screenshotService;

    public PixelReadingService(GameActionService gameActionService, ScreenshotService screenshotService) {
        this.gameActionService = gameActionService;
        this.screenshotService = screenshotService;
        try {
            this.robot = new Robot();
        }
        catch (AWTException e) {}
        this.screenshotService = screenshotService;
    }

    public boolean havePM() {
        Rectangle rectangle = screenshotService.getScreenBounds();
        Color color = robot.getPixelColor(rectangle.x + rectangle.width/2, rectangle.height/2);
        return color.equals(new Color(1,0,0));//will make plugin info
    }

    public boolean charIsDead() {
        Rectangle rectangle = screenshotService.getScreenBounds();
        Color color = robot.getPixelColor(rectangle.x + rectangle.width/2, rectangle.height/2);
        return color.equals(new Color(1,0,0));//will make plugin info
    }

    public boolean haveProblem() {
        boolean result = havePM() | charIsDead();
        if (result) {
            screenshotService.screenshot();
            gameActionService.exit();
        }
        return result;
    }

    public boolean isPetBattleActive() {
        Rectangle rectangle = screenshotService.getScreenBounds();
        Color color = robot.getPixelColor(rectangle.x + (int) (rectangle.width / 27.5 * 0.25),
                (int) (rectangle.height / 15.5 * 0.25));

        return color.equals(new Color(0,0,0));
    }

    public boolean isFishingActive() {
        Rectangle rectangle = screenshotService.getScreenBounds();
        Color color = robot.getPixelColor(rectangle.x + (int) (rectangle.width / 27.5 * 0.25),
                (int) (rectangle.height / 15.5 * 0.25));

        return !color.equals(new Color(255,255,255));
    }

    public void getPixelInfo() {
        Point point = MouseInfo.getPointerInfo().getLocation();
        Color color = robot.getPixelColor(point.x, point.y);
        System.out.println("Point (x, y): (" + point.x + ", " + point.y + ")" +
                ". Color (r, g, b): (" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")");
    }

    public boolean isNeedEnter() {
        int redEnter = 98;
        int tolerance = 3;
        Rectangle rectangle = screenshotService.getScreenBounds();

        int redPlace1 = robot.getPixelColor(rectangle.x + (rectangle.width * 937 / 1920),
                (rectangle.height * 609 / 1080)).getRed();

        int redPlace2 = robot.getPixelColor(rectangle.x + (rectangle.width * 937 / 1920),
                (rectangle.height * 588 / 1080)).getRed();

        return Math.abs(redEnter - redPlace1) < tolerance | Math.abs(redEnter - redPlace2) < tolerance;
    }
}
