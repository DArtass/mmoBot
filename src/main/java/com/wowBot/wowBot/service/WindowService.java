package com.wowBot.wowBot.Controller;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class WindowService {

    private static String ACTIVATE_SCRIPT_PATH;

    public WindowService() {
        try {
            URL activateURL = getClass().getResource("/activate.vbs");
            File file = new File(activateURL.toURI());
            ACTIVATE_SCRIPT_PATH = file.getAbsolutePath();
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void activateWowWindow() {
        try {
            Runtime.getRuntime().exec("csript @ACTIVATE_SCRIPT_PATH").waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
