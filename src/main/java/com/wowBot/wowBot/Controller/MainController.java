package com.wowBot.wowBot.Controller;

import com.wowBot.wowBot.aboutMe.AboutMe;
import com.wowBot.wowBot.service.PixelReadingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Iterator;

@Controller
public class MainController {
    private PixelReadingService pixelReadingService;

    public MainController(PixelReadingService pixelReadingService) {
        this.pixelReadingService = pixelReadingService;
    }

    
}