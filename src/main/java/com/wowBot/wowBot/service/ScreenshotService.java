package com.wowBot.wowBot.service;

import org.opencv.core.Point;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ScreenshotService {

    {
        System.setProperty("java.awt.headless","false");
    }

    private Robot robot;
    private Rectangle screenBounds;
    private int screenNumber = 1;
    //public static Rectangle FLOAT_BOUNDS;
    //public static Rectangle FLOAT_SCREEN_BOUNDS;

    //@Lazy
    public ScreenshotService() {
        try {
            this.robot = new Robot();
        }
        catch (AWTException e) {}
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();
        screenBounds = screens[screenNumber].getDefaultConfiguration().getBounds();

        //FLOAT_BOUNDS = new Rectangle((int) (SCREEN_BOUNDS.width * 0.4), 0, (int) (SCREEN_BOUNDS.width * 0.2), (int) (SCREEN_BOUNDS.height * 0.2));
        //FLOAT_SCREEN_BOUNDS = new Rectangle((int) (SCREEN_BOUNDS.width * 0.4) + SCREEN_BOUNDS.x, 0, (int) (SCREEN_BOUNDS.width * 0.2), (int) (SCREEN_BOUNDS.height * 0.2));
    }

    public Rectangle getScreenBounds() {
        return screenBounds;
    }

    public void changeScreenNumber(int screenNumber) {
        this.screenNumber = screenNumber;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();
        screenBounds = screens[screenNumber].getDefaultConfiguration().getBounds();
    }

    public Point convertToGlobal(Point point) {
        return new Point(screenBounds.x + point.x, screenBounds.y + point.y);
    }

    public String pointToString(Point point) {
        return "(x, y): (" + (int) point.x + ", " + (int) + point.y + ")";
    }

    public byte[] screenshot() {
        return screenshot(screenBounds);
    }
    public byte[] screenshot(Rectangle bounds) {
        BufferedImage screenCapture = robot.createScreenCapture(bounds);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] jpegBytes;
        try {
            ImageIO.write(screenCapture, "jpg", baos);
            baos.flush();
            jpegBytes = baos.toByteArray();
            baos.close();
        }
        catch (IOException e) {
            jpegBytes = null;
        }

        return jpegBytes;
    }

    public BufferedImage screenshotBI() {
        return screenshotBI(screenBounds);
    }
    public BufferedImage screenshotBI(Rectangle bounds) {
        return robot.createScreenCapture(bounds);
    }
}
