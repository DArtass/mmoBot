package com.wowBot.wowBot.service;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class TemplateService {

    public static byte[] FLOAT_BYTES;

    //@Lazy
    public TemplateService() {
        InputStream resourceAsStream = getClass().getResourceAsStream("/static/Float.jpg");
        try {
            assert resourceAsStream != null;
            FLOAT_BYTES = resourceAsStream.readAllBytes();
            resourceAsStream.close();
        }
        catch (IOException e) {
            FLOAT_BYTES = null;
        }
    }

    public Core.MinMaxLocResult matchTemplate(Mat source, Mat template) {
        Mat outputImage = new Mat();

        Imgproc.matchTemplate(source, template, outputImage, Imgproc.TM_CCOEFF_NORMED);
        return Core.minMaxLoc(outputImage);
    }

}
