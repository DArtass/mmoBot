package com.wowBot.wowBot.service;

import org.opencv.core.Point;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.KeyEvent;

@Service
public class GameActionService {
    private Robot robot;
    private ScreenshotService screenshotService;
    private PixelReadingService pixelReadingService;
    private Long startTime;
    private boolean isMoving = false;

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public GameActionService(ScreenshotService screenshotService, PixelReadingService pixelReadingService) {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
        }
        this.screenshotService = screenshotService;
        this.pixelReadingService = pixelReadingService;
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
    }

    public void leftMouse() {
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
    }

    public void pressSpace() {
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
        sleep((long) (500L + Math.random() * 500L));
    }

    public void cursorMove(Point point) {
        //Point resPoint = screenshotService.convertToGlobal(point);
        robot.mouseMove((int) point.x, (int) point.y);
    }

    public void cursorMove() {
        Rectangle rectangle = screenshotService.getScreenBounds();
        cursorMove(screenshotService.convertToGlobal(new Point(rectangle.width / 2, rectangle.height / 2)));
    }

    public void changePetCommand(int commandNumber) {
        System.out.println("changePetCommand");
        openPetCommand();
        Rectangle rectangle = screenshotService.getScreenBounds();
        cursorMove(screenshotService.convertToGlobal(new Point(rectangle.width * 0.4,
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

    public void logout() {
        System.out.println("logout");
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_O);
        robot.keyRelease(KeyEvent.VK_O);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        sleep(21000L);
    }

    public boolean needReBuff(int sec) {
        return needReBuff(sec, 20);
    }

    public boolean needReBuff(int sec, long intervalSec) {
        if (startTime == null) startTime = System.currentTimeMillis();
        return (System.currentTimeMillis() - startTime) % (sec * 1000L) < intervalSec * 1000L;
    }

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
        sleep(2000);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        interaction();
    }

    public void interaction() {
        System.out.println("interaction");

        sleep(100);
        robot.keyPress(KeyEvent.VK_U);
        robot.keyRelease(KeyEvent.VK_U);
        sleep(100);
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

    public void enterButton() {
        System.out.println("Try enter game");

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        sleep(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        sleep(20 * 1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        sleep(30000);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_PLUS);
        sleep(2000);
        robot.keyRelease(KeyEvent.VK_PLUS);
        robot.keyRelease(KeyEvent.VK_ALT);
        sleep(60 * 1000);

        System.out.println("We are enter game");
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
        double posFacing = pixelReadingService.getFacing();
        System.out.println("moveToPoint (" + wantPosition.x + ", " + + wantPosition.y + ") from (" + pos.x + ", " + + pos.y + ")");
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
        Rectangle rectangle = screenshotService.getScreenBounds();

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
        Rectangle rectangle = screenshotService.getScreenBounds();
        robot.mouseMove(0, rectangle.height / 2 + rectangle.y + pixels);
    }

    private void startMoving() {
        if (isMoving) return;
        System.out.println("startMoving");
        isMoving = true;
        cursorMove();
        robot.mousePress(KeyEvent.BUTTON3_DOWN_MASK);
        sleep(300);
    }

    public void stopMoving() {
        isMoving =false;
        robot.keyRelease(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_Q);
        robot.keyRelease(KeyEvent.VK_E);
        robot.mouseRelease(KeyEvent.BUTTON3_DOWN_MASK);

        sleep(100);
    }

    private double scalarMult(double a, double b, double x, double y) {
        return a * x + b * y;
    }

    private double  vectorMult(double a, double b, double x, double y) {
        return a * y - b * x;
    }
    private Point getDirection(double x, double y, double wx, double wy, double angle) {
        double dx = x - wx;
        double dy = y - wy;
        return new Point(scalarMult(-Math.sin(angle), -Math.cos(angle), dx, dy),
                vectorMult(-Math.sin(angle), -Math.cos(angle), dx, dy));
    }
}
