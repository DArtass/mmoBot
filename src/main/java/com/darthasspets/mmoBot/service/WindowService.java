package com.darthasspets.mmoBot.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class WindowService {

    private static String ACTIVATE_WOW_SCRIPT_PATH;
    private static String ACTIVATE_NW_SCRIPT_PATH;

    public WindowService() {
        try {
            URL activateURL = getClass().getResource("/activateWow.vbs");
            assert activateURL != null;
            File file = new File(activateURL.toURI());
            ACTIVATE_WOW_SCRIPT_PATH = file.getAbsolutePath();
            activateURL = getClass().getResource("/activateNW.vbs");
            assert activateURL != null;
            file = new File(activateURL.toURI());
            ACTIVATE_NW_SCRIPT_PATH = file.getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void activateGameWindow(int gameType) {
        if (gameType == 0)
            activateWowWindow();
        else if (gameType == 1)
            activateNWWindow();
    }

    private void activateWowWindow() {
        System.out.println("activate Wow Window");
        try {
            Runtime.getRuntime().exec("cscript " + ACTIVATE_WOW_SCRIPT_PATH).waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void activateNWWindow() {
        System.out.println("activate NW Window");
        try {
            Runtime.getRuntime().exec("cscript " + ACTIVATE_NW_SCRIPT_PATH).waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
