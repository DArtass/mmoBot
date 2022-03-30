package com.wowBot.wowBot.Controller;

import com.wowBot.wowBot.service.*;
import org.opencv.core.Point;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WowBotController {

    private FishingService fishingService;
    private WindowService windowService;
    private GameActionService gameActionService;
    private PixelReadingService pixelReadingService;
    private LogService logService;
    private ScreenshotService screenshotService;

    private int commandPetCount;
    private int countActs = 0;
    private int countErrors = 0;

    public WowBotController(FishingService fishingService, WindowService windowService, LogService logService,
                             GameActionService gameActionService, PixelReadingService pixelReadingService,
                             ScreenshotService screenshotService) {
        this.fishingService = fishingService;
        this.windowService = windowService;
        this.gameActionService = gameActionService;
        this.pixelReadingService = pixelReadingService;
        this.logService = logService;
        this.screenshotService = screenshotService;
    }

    @PostMapping(value = "/run")
    public String runWowBot(@RequestParam int countActsInLog, @RequestParam int maxCountErrors,
                            @RequestParam int commandPetCount) {
        this.commandPetCount = commandPetCount;

        windowService.activateWowWindow();

        while (!(countErrors > maxCountErrors)) {
            gameActionService.sleep(2000L);
            /*if (pixelReadingService.isNeedEnter())
                gameActionService.enterButton();*/

            while ((pixelReadingService.isFishingActive() | pixelReadingService.isPetBattleActive())
                    & (countErrors < maxCountErrors + 2)) {
                windowService.activateWowWindow();
                countActs++;
                if (countActs > countActsInLog | countErrors > maxCountErrors) {
                    countActs = 0;
                    logService.saveScreenshot();
                }

                if (pixelReadingService.isFishingActive())
                    fishing();
                else if (pixelReadingService.isPetBattleActive())
                    petBattle();
            }
        }
        return "redirect:";
    }

    private void petBattle() {
        for (int i = 0; i < commandPetCount & pixelReadingService.isPetBattleActive(); i++) {
            gameActionService.changePetCommand(i);
            for (int i1 = 0; i1 < 5 & pixelReadingService.isPetBattleActive(); i1++) {
                gameActionService.pressSpace();
                gameActionService.sleep((long) (1000L + Math.random() * 3000L));
            }
        }
        countErrors = 0;
    }

    private void fishing() {
        if (gameActionService.needReBuff(29 * 60))
            gameActionService.setBait();

        if (gameActionService.needReBuff(181))
            gameActionService.firimBall();

        if (gameActionService.needReBuff(20 * 60))
            gameActionService.floatBoat();

        gameActionService.cursorMove();
        gameActionService.fishing();
        gameActionService.sleep(3000L);

        Point xy = fishingService.getFloatXYGuaranteed();
        if (xy.x < 0) {
            System.out.println("Поплавок не найден");
            countErrors++;
            return;
        }
        System.out.println("Поплавок найден " + screenshotService.pointToString(xy));
        boolean checkPecking = fishingService.checkPecking(xy);
        if (!checkPecking) {
            System.out.println("Не клюет");
            countErrors++;
            return;
        }
        System.out.println("Клюет");
        gameActionService.cursorMove(xy);
        gameActionService.rightMouse();

        countErrors = 0;
    }
}