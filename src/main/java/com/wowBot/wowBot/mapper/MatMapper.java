package com.wowBot.wowBot.mapper;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class MatMapper {

    public Mat map(byte[] bytes) {
        return Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_UNCHANGED);
    }

    public BufferedImage map(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, matOfByte);
        BufferedImage bi;
        try {
            bi = ImageIO.read(new ByteArrayInputStream(matOfByte.toArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bi;
    }
}
