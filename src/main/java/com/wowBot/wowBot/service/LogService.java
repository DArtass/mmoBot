package com.wowBot.wowBot.service;

import com.wowBot.wowBot.mapper.MatMapper;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;
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
    private MatMapper matMapper;
    private static final String LOGPATH = "D:/YandexDisk/WowBotLogs";

    public LogService(ScreenshotService screenshotService, MatMapper matMapper) {
        this.screenshotService = screenshotService;
        this.matMapper = matMapper;
    }

    public void recordVideo() {
        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened())
            System.out.println("Error");
        else {
            Mat frame = new Mat();
            while (true) {
                if (camera.read(frame)) {
                    System.out.println("Frame obtained");
                    System.out.println("Captured Frame Width " +
                            frame.width() + " Height " + frame.height());
                    HighGui.imshow("camera.jpg", frame);
                    System.out.println("OK");


                    Date currDate = new Date();

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    String folderPath = LOGPATH + "/videoRecords/" + formatter.format(currDate);
                    formatter = new SimpleDateFormat("HHmmss");
                    String fileName = formatter.format(currDate) + ".jpg";

                    File folder = new File(folderPath);
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    var bi = matMapper.map(frame);
                    File outputfile = new File(folderPath + "/" + fileName);

                    try {
                        ImageIO.write(bi, "jpg", outputfile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                }
            }
        }

    }
    public void saveScreenshot() {
        try {
            System.out.println("saveScreenshot");
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