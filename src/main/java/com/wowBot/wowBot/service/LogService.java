package com.wowBot.wowBot.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class LogService {
    private ScreenshotService screenshotService;
    private static final String LOGPATH = "D:/YandexDisk/WowBotLogs";

    public LogService(ScreenshotService screenshotService) {
        this.screenshotService = screenshotService;
    }

    public void saveScreenshot() {
        try {
            Date currDate = new Date();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String folderPath = LOGPATH + "/screenshots/" + formatter.format(currDate);
            formatter = new SimpleDateFormat("HHmmss");
            String fileName = formatter.format(currDate) + ".jpg";

            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdir();
            }

            BufferedImage bi = screenshotService.screenshotBI();
            File outputfile = new File(folderPath + "/" + fileName);

            ImageIO.write(bi, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
