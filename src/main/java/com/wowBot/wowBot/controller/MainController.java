package com.wowBot.wowBot.controller;

import com.wowBot.wowBot.aboutMe.AboutMe;
import com.wowBot.wowBot.service.PixelReadingService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Iterator;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class MainController {
    PixelReadingService pixelReadingService;

    public MainController(PixelReadingService pixelReadingService) {
        this.pixelReadingService = pixelReadingService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "Trofimov Aleksandr");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        ArrayList<AboutMe> al = new ArrayList<>();

        al.add(new AboutMe("32", "#", "Age: "));
        al.add(new AboutMe("Almaty", "https://www.google.ru/maps/place/Samara,+Samara+Oblast", "City: "));
        al.add(new AboutMe("@AleksandrTrofimovJava", "https://t.me/AleksandrTrofimovJava", "Telegram: "));
        Iterator<AboutMe> ital = al.iterator();
        model.addAttribute("info", ital);
        return "about";
    }

    @GetMapping("/pixelInfo")
    public String pixelInfo(Model model) {
        for (int i = 0; i < 10; i++) {
            pixelReadingService.getPixelInfo();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "home";
    }
}