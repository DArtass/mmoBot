package com.wowBot.wowBot.service;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class PictureService {

    public Mat markRectangle(Mat mat, Rectangle rectangle) {
        Mat output = new Mat();
        mat.copyTo(output);

        Rect rect = new Rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        Imgproc.rectangle(output, rect, new Scalar(255.0, 239.0, 0.0));

        return output;
    }
}