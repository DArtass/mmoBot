package com.wowBot.wowBot.service;

import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class PixelReadingService {

    private Robot robot;
    private ScreenshotService screenshotService;

    public PixelReadingService(ScreenshotService screenshotService) {
        this.screenshotService = screenshotService;
        try {
            this.robot = new Robot();
        }
        catch (AWTException e) {}
        this.screenshotService = screenshotService;
    }

    public boolean isPixelColor(Point pixel, Color color) {
        Point globPoint = screenshotService.convertToGlobal(pixel);
        Color currColor = robot.getPixelColor(globPoint.x,globPoint.y);

        return color.equals(currColor);
    }

    public boolean isPetBattleActive() {
        return isPixelColor(new Point(15, 15), new Color(0,0,0));
    }

    public boolean isFishingActive() {
        return isPixelColor(new Point(15, 15), new Color(255,255,255));
    }

    public boolean havePM() {
        return isPixelColor(new Point(439, 562), new Color(173, 101, 183)); //change
    }

    public boolean charIsDead() {
        return isPixelColor(new Point(200, 800), new Color(150,150,150)); //change
    }

    public boolean inMasterBattle() {
        return isPixelColor(new Point(1042, 1027), new Color(197, 194, 180)); //change
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

    public org.opencv.core.Point getXYPosition() {
        Rectangle rectangle = screenshotService.getScreenBounds();
        Color colorX = robot.getPixelColor(rectangle.x + rectangle.width / 1920 * 15,
                rectangle.height / 1080 * 45);

        Color colorY = robot.getPixelColor(rectangle.x + rectangle.width / 1920 * 15,
                rectangle.height / 1080 * 75);


        return new org.opencv.core.Point((double) (colorX.getRed() + colorX.getGreen() / 255) / 255 * 100,
                (double) (colorY.getRed() + colorY.getGreen() / 255) / 255 * 100);
    }

    public double getFacing() {
        Rectangle rectangle = screenshotService.getScreenBounds();
        Color color = robot.getPixelColor(rectangle.x + rectangle.width / 1920 * 15,
                rectangle.height / 1080 * 45);
        return color.getBlue() * 7 / 255;
    }
}