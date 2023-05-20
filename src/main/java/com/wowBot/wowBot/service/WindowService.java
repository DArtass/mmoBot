package com.wowBot.wowBot.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class WindowService {

    private static String ACTIVATE_WOW_SCRIPT_PATH;
    private static String ACTIVATE_BN_SCRIPT_PATH;

    public WindowService() {
        try {
            URL activateURL = getClass().getResource("/activateWow.vbs");
            File file = new File(activateURL.toURI());
            ACTIVATE_WOW_SCRIPT_PATH = file.getAbsolutePath();
            activateURL = getClass().getResource("/activateBN.vbs");
            file = new File(activateURL.toURI());
            ACTIVATE_BN_SCRIPT_PATH = file.getAbsolutePath();
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void activateWowWindow() {
        System.out.println("activate Wow Window");
        try {
            Runtime.getRuntime().exec("cscript " + ACTIVATE_WOW_SCRIPT_PATH).waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void activateBNWindow() {
        System.out.println("activate BN Window");
        try {
            Runtime.getRuntime().exec("cscript " + ACTIVATE_BN_SCRIPT_PATH).waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
