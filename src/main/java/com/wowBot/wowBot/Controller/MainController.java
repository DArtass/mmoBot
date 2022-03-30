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

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "Trofimov Alexandr");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        ArrayList<AboutMe> al = new ArrayList<>();

        al.add(new AboutMe("31", "#", "Age: "));
        al.add(new AboutMe("Samara", "https://www.google.ru/maps/place/Samara,+Samara+Oblast", "City: "));
        al.add(new AboutMe("@GladiatorSanya", "https://t.me/GladiatorSanya", "Telegram: "));
        al.add(new AboutMe("@darax111", "https://join.skype.com/invite/l5JCpR6CgysG", "Skype: "));
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
        return "redirect:";
    }
}