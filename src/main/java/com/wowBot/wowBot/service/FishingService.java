package com.wowBot.wowBot.service;

import com.wowBot.wowBot.mapper.MatMapper;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;


public class FishinfService {
    private ScreenshotService screenshotService;
    private MatMapper matMapper;
    private TemplateService templateService;

    public FishinfService(ScreenshotService screenshotService, MatMapper matMapper, TemplateService templateService) {
        this.screenshotService = screenshotService;
        this.matMapper = matMapper;
        this.templateService = templateService;
    }

    public Point getFloatXY() {
        byte[] floatAreaBytes = screenshotService.screenshot(ScreenshotService.FLOAT_SCREEN_BOUNDS);
        Mat floatAreaMat = matMapper.map(floatAreaBytes);
        Mat floatMap = matMapper.map(TemplateService.FLOAT_BYTES);

        Core.MinMaxLocResult matchResult = templateService.matchTemplate(floatAreaMat, floatAreaMat);
        Point maxLoc = matchResult.maxLoc;

        return screenshotService.convertToGlobal(ScreenshotService.FLOAT_BOUNDS, maxLoc);
    }
}
