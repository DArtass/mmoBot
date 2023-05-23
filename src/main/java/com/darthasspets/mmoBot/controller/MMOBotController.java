package com.darthasspets.mmoBot.controller;

import com.darthasspets.mmoBot.service.MainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
@RequiredArgsConstructor
@Api("Методы для управления ботом")
public class MMOBotController {

    MainService mainService;

    @ApiOperation("Запуск приложения")
    @PostMapping(value = "/run")
    public String runMMOBotPostController(@RequestParam int countActsInLog, @RequestParam int maxCountErrors,
                                          @RequestParam int commandPetCount, @RequestParam int screenNumber,
                                          @RequestParam String stopTime, @RequestParam int botType, @RequestParam int gameType) {
        mainService.runMMOBot(countActsInLog, maxCountErrors, commandPetCount, screenNumber, stopTime, botType, gameType);
        return "home";
    }

    @ApiOperation("Пауза приложения")
    @GetMapping(value = "/pause")
    public String pauseMMOBotPostController() {
        mainService.pause();
        return "home";
    }

    @ApiOperation("Снять с паузы приложение")
    @GetMapping(value = "/resume")
    public String resumeMMOBotPostController() {
        mainService.resume();
        return "home";
    }
}