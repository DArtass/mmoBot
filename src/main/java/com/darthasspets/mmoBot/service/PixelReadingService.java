package com.darthasspets.mmoBot.service;

import com.darthasspets.mmoBot.gameState.GameState;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class PixelReadingService {
    final GameState gameState;
    final ScreenshotService screenshotService;
    Robot robot;

    public PixelReadingService(ScreenshotService screenshotService, GameState gameState) {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.gameState = gameState;
        this.screenshotService = screenshotService;
    }

    public boolean isPixelColor(Point pixel, Color color) {
        Point globPoint = screenshotService.convertToGlobal(pixel);
        Color currColor = robot.getPixelColor(globPoint.x,globPoint.y);

        return color.equals(currColor);
    }

    public org.opencv.core.Point getXYPosition() {
        Rectangle rectangle = gameState.getScreenBounds();
        Color colorX = robot.getPixelColor(rectangle.x + rectangle.width / 1920 * 15,
                rectangle.height / 1080 * 45);

        Color colorY = robot.getPixelColor(rectangle.x + rectangle.width / 1920 * 15,
                rectangle.height / 1080 * 75);


        return new org.opencv.core.Point((double) (colorX.getRed() + colorX.getGreen() / 255) / 255 * 100,
                (double) (colorY.getRed() + colorY.getGreen() / 255) / 255 * 100);
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

    public boolean isNeedExit() {
        int redEnter1 = 103;
        int blueEnter1 = 1;
        var tolerance = gameState.getToleranceColor();
        Rectangle rectangle = gameState.getScreenBounds();
        var colorPlace = robot.getPixelColor(rectangle.width * 1856 / 1920 + rectangle.x,
                rectangle.height * 1013 / 1080);

        return Math.abs(redEnter1 - colorPlace.getRed()) < tolerance &&
                Math.abs(blueEnter1 - colorPlace.getBlue()) < tolerance;
    }

    public boolean isNeedEnter() {
        int redEnter = 20;
        int greenEnter = 142;
        int blueEnter = 255;
        Rectangle rectangle = gameState.getScreenBounds();
        var colorPlace = robot.getPixelColor(rectangle.width * 257 / 1920 + rectangle.x,
                rectangle.height * 940 / 1080);

        return redEnter == colorPlace.getRed() &&
                greenEnter == colorPlace.getGreen() &&
                blueEnter == colorPlace.getBlue();
    }

    public boolean isInGame() {
        int redEnter1 = 201;
        int redEnter2 = 220;
        int tolerance = 3;
        Rectangle rectangle = gameState.getScreenBounds();

        int redPlace1 = robot.getPixelColor(rectangle.x + (rectangle.width * 1899 / 1920),
                (rectangle.height * 1014 / 1080)).getRed();

        int redPlace2 = robot.getPixelColor(rectangle.x + (rectangle.width * 1896 / 1920),
                (rectangle.height * 1013 / 1080)).getRed();

        return Math.abs(redEnter1 - redPlace1) < tolerance || Math.abs(redEnter2 - redPlace2) < tolerance;
    }

    public double getFacing() {
        Rectangle rectangle = gameState.getScreenBounds();
        Color color = robot.getPixelColor(rectangle.x + rectangle.width / 1920 * 15,
                rectangle.height / 1080 * 45);
        return color.getBlue() * 7 / 255;
    }

    public boolean isNeedReBuff(int sec) {
        return isNeedReBuff(sec, 20);
    }

    public boolean isNeedReBuff(int sec, long intervalSec) {
        return (System.currentTimeMillis() - gameState.getStartTime())/1000L % sec < intervalSec;
    }

    public boolean isSomethingActive(Color color, int x, int y) {
        Rectangle rectangle = gameState.getScreenBounds();
        var colorPlace = robot.getPixelColor(rectangle.width * x / 1920 + rectangle.x,
                rectangle.height * y / 1080);

        return color.equals(colorPlace);
    }

    public boolean isPetBattleActive() {
        return isSomethingActive(new Color(199, 190, 172),53,133);
    }

    public boolean isFishBattleActive() {
        return isSomethingActive(new Color(1,1,2),53,133);
    }

    public boolean isPaused() {
        return isSomethingActive(new Color(119,195,0),53,133);
    }

    public boolean isResumed() {
        return isSomethingActive(new Color(104,13,10),53,133) ||
                isSomethingActive(new Color(103,12,9),53,133);
    }

    public void checkState() {
        if (gameState.isWowActive()) {
            if (isPetBattleActive()) {
                System.out.println("Pet Battle Activated");
                gameState.setPetBattleActive(true);
                gameState.setFishingActive(false);
            }

            if (isFishBattleActive()) {
                System.out.println("Fish Battle Activated");
                gameState.setPetBattleActive(false);
                gameState.setFishingActive(true);
            }
        } else {
            gameState.setGatheringActive(true);
        }

        if (isPaused()) {
            System.out.println("Paused");
            gameState.setPaused(true);
        }

        if (isResumed()) {
            System.out.println("Resumed");
            gameState.setPaused(false);
        }

        /*todo check logout
         if (!gameState.isPetBattleActive()) {
            gameState.setNeedExit(isNeedExit());
            gameState.setNeedEnter(isNeedEnter());
        }
        isInGame()
        gameState.setPetBattleActive(isPixelColor(new Point(15, 15), new Color(0,0,0)));
        gameState.setFishingActive(isPixelColor(new Point(15, 15), new Color(255,255,255)));*/
    }
}