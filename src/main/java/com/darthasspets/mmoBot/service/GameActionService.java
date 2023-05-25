package com.darthasspets.mmoBot.service;

import com.darthasspets.mmoBot.gameState.GameState;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Point;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.KeyEvent;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class GameActionService {
    final ScreenshotService screenshotService;
    final PixelReadingService pixelReadingService;
    final LogService logService;
    final GameState gameState;
    Robot robot;

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public GameActionService(ScreenshotService screenshotService, PixelReadingService pixelReadingService, GameState gameState, LogService logService) {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.screenshotService = screenshotService;
        this.pixelReadingService = pixelReadingService;
        this.logService = logService;
        this.gameState = gameState;
    }

    public void castFishingRode() {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        sleep(3000L);
    }

    public void rightMouse() {
        robot.mousePress(KeyEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(KeyEvent.BUTTON3_DOWN_MASK);
        sleep(1000L);
    }

    public void leftMouse() {
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
    }

    public void cursorMove(Point point) {
        //Point resPoint = screenshotService.convertToGlobal(point);
        robot.mouseMove((int) point.x, (int) point.y);
    }

    public void cursorMove() {
        Rectangle rectangle = gameState.getScreenBounds();
        cursorMove(screenshotService.convertToGlobal(new Point(rectangle.width / 2, rectangle.height / 2)));
    }

    public void pressKey(int keyEvent) {
        pressKey(keyEvent, 500L, 0);
    }

    public void pressKey(int keyEvent, long millisSleep, long millisPressed) {
        robot.keyPress(keyEvent);
        if (millisPressed > 0)
            sleep(millisPressed);
        robot.keyRelease(keyEvent);
        sleep((long) (millisSleep + Math.random() * millisSleep));
    }

    public void gather() {
        log.info("gather");
        var gatherReps = 10;
        for (int i = -1; gameState.isGatheringActive() && !gameState.isPaused() && i < gatherReps; i++) {
            pressKey(KeyEvent.VK_E);
        }
    }

    public void gatherFight() {
        logService.info("gatherSafe");
        pressKey(KeyEvent.VK_R);
        gather();
        pressKey(KeyEvent.VK_Q);
        gather();
        pressKey(KeyEvent.VK_F);
        gather();
    }

    public void gatherSide(int side, String sideName, int countSide) {
        for (int i = 0; i < countSide && !gameState.isPaused(); i++) {
            log.info("gatherSide " + sideName + " " + i);
            gather();
            pressKey(side, 0, 1000);
        }
    }

    public void gatherSquare() {
        log.info("gatherSquare");
        logService.saveScreenshot();
        gatherSide(KeyEvent.VK_D, "D", 7);
        gatherSide(KeyEvent.VK_A, "A", 7);
    }

    public void gathering() {
        log.info("Start circle gathering");
        logService.saveScreenshot();
        pressKey(KeyEvent.VK_ENTER);
        for (int i = -1; gameState.isGatheringActive() && !gameState.isPaused() && i < 10; i++) {
            gather();
        }
        gameState.setCountErrors(0);
    }

    public void changePetCommand(int commandNumber) {
        System.out.println("changePetCommand");
        openPetCommand();
        Rectangle rectangle = gameState.getScreenBounds();
        cursorMove(screenshotService.convertToGlobal(new Point(rectangle.width * 0.37,
                rectangle.height * 3.625 / 15.5 + rectangle.height * commandNumber * 0.3775 / 15.5)));
        sleep(100L);
        leftMouse();
        openPetCommand();
    }

    public void openPetCommand() {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    @Deprecated
    public void firimBall() {
        System.out.println("firimBall");

        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_L);
        robot.keyRelease(KeyEvent.VK_L);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        sleep(1500L);
    }

    public void floatBoat() {
        System.out.println("floatBoat");

        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        sleep(2000);
    }

    public void setBait() {
        System.out.println("setBait");

        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    public void setFishingRoad() {
        System.out.println("setFishingRoad");

        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_K);
        robot.keyRelease(KeyEvent.VK_K);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    public void sellJunk() {
        System.out.println("sellJunk");

        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        sleep(3000);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        sleep(2000);
        interaction();
    }

    public void interaction() {
        System.out.println("interaction");

        robot.keyPress(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_P);
        sleep(2000);
    }

    public void interactionWithBuff() {
        System.out.println("interactionWithBuff");

        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_L);
        robot.keyRelease(KeyEvent.VK_L);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_SHIFT);

        interaction();
        cursorMove(new Point(180, 300));
        rightMouse();
    }

    public void exitGame() {
        System.out.println("exit game");
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_O);
        robot.keyRelease(KeyEvent.VK_O);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        sleep(22000L);
    }

    public void closeGame() {
        System.out.println("close game");
        sleep(1000);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_F4);
        robot.keyRelease(KeyEvent.VK_F4);
        robot.keyRelease(KeyEvent.VK_ALT);
        sleep(5 * 1000);
    }

    public void nearWindow() {
        System.out.println("near Window");

        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_EQUALS);
        sleep(10 * 1000);
        robot.keyRelease(KeyEvent.VK_EQUALS);
        robot.keyRelease(KeyEvent.VK_ALT);
        sleep(1000);
    }

    public void enterGame() {
        System.out.println("Try to enter game");

        sleep(5 * 1000);
        Rectangle rectangle = gameState.getScreenBounds();
        cursorMove(screenshotService.convertToGlobal(new Point(rectangle.width * 210/1920, rectangle.height * 947/1080)));
        sleep(100);
        leftMouse();
        sleep(90 * 1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        sleep(60 * 1000);

        nearWindow();

        System.out.println("We entered into the game");
    }

    public void callMount() {
        System.out.println("callMount");
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        sleep(3000);
    }

    public void moveUp(long millis) {
        System.out.println("moveUp (" + millis + ")");
        robot.keyPress(KeyEvent.VK_SPACE);
        sleep(millis);
        robot.keyRelease(KeyEvent.VK_SPACE);
    }

    public void moveDown(long millis) {
        System.out.println("moveDown (" + millis + ")");
        robot.keyPress(KeyEvent.VK_X);
        sleep(millis);
        robot.keyRelease(KeyEvent.VK_X);
    }

    public void moveToPoint(Point wantPosition, double toleranceMove) {
        Point pos = pixelReadingService.getXYPosition();
        double posFacing;
        System.out.println("moveToPoint (" + wantPosition.x + ", " + wantPosition.y + ") from (" + pos.x + ", " + pos.y + ")");
        while (true) {
            pos = pixelReadingService.getXYPosition();
            posFacing = pixelReadingService.getFacing();
            startMoving();
            Point dir = getDirection(pos.x, pos.y, wantPosition.x, wantPosition.y, posFacing);
            if (Math.abs(dir.x) < toleranceMove & Math.abs(dir.y) < toleranceMove) break;

            if (Math.abs(dir.x) >= Math.abs(dir.y)) {
                robot.keyRelease(KeyEvent.VK_Q);
                robot.keyRelease(KeyEvent.VK_E);
                if (dir.x <= 0) {
                    robot.keyRelease(KeyEvent.VK_S);
                    robot.keyPress(KeyEvent.VK_W);
                } else {
                    robot.keyRelease(KeyEvent.VK_W);
                    robot.keyPress(KeyEvent.VK_S);
                }
            } else {
                robot.keyRelease(KeyEvent.VK_S);
                robot.keyRelease(KeyEvent.VK_W);
                if (dir.y < 0) {
                    robot.keyRelease(KeyEvent.VK_Q);
                    robot.keyPress(KeyEvent.VK_E);
                } else {
                    robot.keyRelease(KeyEvent.VK_E);
                    robot.keyPress(KeyEvent.VK_Q);
                }
            }
            System.out.println("moveToPoint from (" + dir.x + ", " + dir.y  +")");
        }
        robot.keyRelease(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_Q);
        robot.keyRelease(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_W);
    }

    public void turnTo(double wantFacing) {
        System.out.println("turnTo " + wantFacing);
        startMoving();
        double currFacing = pixelReadingService.getFacing();
        double sinDiff = Math.sin(currFacing - wantFacing);
        Rectangle rectangle = gameState.getScreenBounds();

        int x = rectangle.width / 2 + rectangle.x;

        while (Math.abs(sinDiff) > 0.05) {
            x += (int) (sinDiff * 50);
            robot.mouseMove(x, rectangle.height / 2);
            currFacing = pixelReadingService.getFacing();
            sinDiff = Math.sin(currFacing - wantFacing);
        }
    }

    public void pitchTo(int pixels) {
        System.out.println("pitchTo " + pixels);
        Rectangle rectangle = gameState.getScreenBounds();
        robot.mouseMove(0, rectangle.height / 2 + rectangle.y + pixels);
    }

    private void startMoving() {
        if (gameState.isMoving()) return;
        System.out.println("startMoving");
        gameState.setMoving(true);
        cursorMove();
        robot.mousePress(KeyEvent.BUTTON3_DOWN_MASK);
        sleep(300);
    }

    public void stopMoving() {
        robot.keyRelease(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_Q);
        robot.keyRelease(KeyEvent.VK_E);
        robot.mouseRelease(KeyEvent.BUTTON3_DOWN_MASK);
        gameState.setMoving(false);

        sleep(100);
    }

    private double scalarMult(double a, double b, double x, double y) {
        return a * x + b * y;
    }

    private double vectorMult(double a, double b, double x, double y) {
        return a * y - b * x;
    }

    private Point getDirection(double x, double y, double wx, double wy, double angle) {
        double dx = x - wx;
        double dy = y - wy;
        return new Point(scalarMult(-Math.sin(angle), -Math.cos(angle), dx, dy),
                vectorMult(-Math.sin(angle), -Math.cos(angle), dx, dy));
    }
}
