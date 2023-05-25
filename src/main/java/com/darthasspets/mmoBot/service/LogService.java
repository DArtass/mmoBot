package com.darthasspets.mmoBot.service;

import com.darthasspets.mmoBot.mapper.MatMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class LogService {
    ScreenshotService screenshotService;
    MatMapper matMapper;

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
                    String folderPath = "${logService.screenshot.path}" + "/videoRecords/" + formatter.format(currDate);
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

    public void info(String message) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println(formatter.format(new Date()) + " " + message);
    }

    public void saveScreenshot() {
        try {
            log.info("saveScreenshot");
            Date currDate = new Date();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            //String folderPath = "D:/YandexDisk/_old/mmoBotLogs/screenshots/" + formatter.format(currDate);
            String folderPath = "./log/screenshots/" + formatter.format(currDate);
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