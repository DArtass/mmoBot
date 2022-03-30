package com.wowBot.wowBot.service;

import org.opencv.core.Point;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.KeyEvent;

@Service
public class GameActionService {
    private Robot robot;
    private ScreenshotService screenshotService;
    private Long startTime;

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public GameActionService(ScreenshotService screenshotService) {
        try {
            this.robot = new Robot();
        }
        catch (AWTException e) {}
        this.screenshotService = screenshotService;
    }

    public void fishing() {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_SHIFT);
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
    }

    public void cursorMove(Point point) {
        Point resPoint = screenshotService.convertToGlobal(point);
        robot.mouseMove((int) resPoint.x, (int) resPoint.y);
    }

    public void cursorMove() {
        Rectangle rectangle = screenshotService.getScreenBounds();
        cursorMove(new Point(Math.random() * rectangle.width,Math.random() * rectangle.height));
    }

    public void changePetCommand(int commandNumber) {
        openPetCommand();
        Rectangle rectangle = screenshotService.getScreenBounds();
        cursorMove(new Point(rectangle.width * 0.4,
                rectangle.height * 3.625 / 15.5 + rectangle.height * commandNumber * 0.377 / 15.5));
        sleep(200L);
        leftMouse();
        openPetCommand();
    }

    public void openPetCommand() {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    public void exit() {
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
        Rectangle rectangle = screenshotService.getScreenBounds();
        cursorMove(new Point(rectangle.width/2,rectangle.height * 9.375 / 15.5));
        rightMouse();
    }

    public boolean needReBuff(int sec) {
        if (startTime == null) startTime = System.currentTimeMillis();
        return (System.currentTimeMillis() - startTime) % (sec * 1000L) < 20000L;
    }

    public void firimBall() {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_L);
        robot.keyRelease(KeyEvent.VK_L);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        sleep(1500L);

        System.out.println("Firim ball");
    }

    public void floatBoat() {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        sleep(2000);

        System.out.println("Float boat");
    }

    public void setBait() {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_SHIFT);

        System.out.println("Bait seted");
    }

    public void sellJunk() {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        sleep(3000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        sleep(1000);
        robot.keyPress(KeyEvent.VK_SLASH);
        robot.keyRelease(KeyEvent.VK_SLASH);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
        robot.keyPress(KeyEvent.VK_SPACE); //name
        robot.keyRelease(KeyEvent.VK_SPACE);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        robot.keyPress(KeyEvent.VK_U);
        robot.keyRelease(KeyEvent.VK_U);

        System.out.println("Junk sold");
    }

    public void enterButton() {

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

        System.out.println("We are enter");
    }
}
