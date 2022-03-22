package com.wowBot.wowBot.service;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.KeyEvent;

@Service
public class GameActionService {
    private Robot robot;

    public GameActionService() {
        try {
            this.robot = new Robot();
        }
        catch (AWTException e) {}
    }

    public void fishing() {
        robot.keyPress(KeyEvent.VK_6);
        robot.keyRelease(KeyEvent.VK_6);
    }
}
