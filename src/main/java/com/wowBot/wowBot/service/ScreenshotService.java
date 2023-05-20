package com.wowBot.wowBot.service;

import com.wowBot.wowBot.gameState.GameState;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.opencv.core.Point;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class ScreenshotService {

    final GameState gameState;
    Robot robot;

    {
        System.setProperty("java.awt.headless", "false");
    }

    //@Lazy
    public ScreenshotService(GameState gameState) {
        this.gameState = gameState;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();
        gameState.setScreenBounds(screens[gameState.getScreenNumber()].getDefaultConfiguration().getBounds());

        gameState.setFloatBounds(new Rectangle((int) (gameState.getScreenBounds().width * 0.4), 0, (int) (gameState.getScreenBounds().width * 0.2),
                (int) (gameState.getScreenBounds().height * 0.2)));
        gameState.setFloatScreenBounds(new Rectangle((int) (gameState.getScreenBounds().width * 0.4) + gameState.getScreenBounds().x,
                0, (int) (gameState.getScreenBounds().width * 0.2), (int) (gameState.getScreenBounds().height * 0.2)));
    }

    public void changeScreenNumber(int screenNumber) {
        gameState.setScreenNumber(screenNumber);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();
        gameState.setScreenBounds(screens[gameState.getScreenNumber()].getDefaultConfiguration().getBounds());
    }

    public Point convertToGlobal(Point point) {
        return new Point(gameState.getScreenBounds().x + point.x, gameState.getScreenBounds().y + point.y);
    }

    public java.awt.Point convertToGlobal(java.awt.Point point) {
        return new java.awt.Point(gameState.getScreenBounds().x + point.x, gameState.getScreenBounds().y + point.y);
    }


    public String pointToString(Point point) {
        return "(x, y): (" + (int) point.x + ", " + (int) point.y + ")";
    }

    public byte[] screenshot() {
        return screenshot(gameState.getScreenBounds());
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
        } catch (IOException e) {
            jpegBytes = null;
        }

        return jpegBytes;
    }

    public BufferedImage screenshotBI() {
        Rectangle rectangle = new Rectangle();
        rectangle.width = gameState.getScreenBounds().width + gameState.getScreenBounds().x;
        rectangle.height = gameState.getScreenBounds().height;
        return screenshotBI(rectangle);
    }

    public BufferedImage screenshotBI(Rectangle bounds) {
        return robot.createScreenCapture(bounds);
    }
}
