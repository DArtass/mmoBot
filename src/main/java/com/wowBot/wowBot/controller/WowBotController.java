package com.wowBot.wowBot.controller;

import com.wowBot.wowBot.service.MainService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class WowBotController {

    MainService mainService;

    public WowBotController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping(value = "/run")
    public String runWowBotPostController(@RequestParam int countActsInLog, @RequestParam int maxCountErrors,
                            @RequestParam int commandPetCount, @RequestParam int screenNumber,
                                          @RequestParam String stopTime, @RequestParam int botType) {
        mainService.runGameBot(countActsInLog, maxCountErrors, commandPetCount, screenNumber, stopTime, botType);
        return "home";
    }

    @GetMapping(value = "/pause")
    public String pauseWowBotPostController(Model model) {
        mainService.pause();
        return "home";
    }
    @GetMapping(value = "/resume")
    public String resumeWowBotPostController(Model model) {
        mainService.resume();
        return "home";
    }
}