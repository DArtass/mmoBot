package com.wowBot.wowBot.Controller;

import com.wowBot.wowBot.service.*;
import org.opencv.core.Point;
import org.springframework.web.bind.annotation.GetMapping;
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

    private int commandPetCount = 5;
    private int countActsInLog = 20;
    private int maxCountErrors = 100;
    private int countActs = 0;
    private int countErrors = 0;
    private Long stopTime = System.currentTimeMillis() + 24 * 60 * 60 * 1000;

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
    public String runWowBotPostController(@RequestParam int countActsInLog, @RequestParam int maxCountErrors,
                            @RequestParam int commandPetCount, @RequestParam int screenNumber,
                                          @RequestParam String stopTime) {
        this.commandPetCount = commandPetCount;
        this.countActsInLog = countActsInLog;
        this.maxCountErrors = maxCountErrors;

        char[] ct = stopTime.toCharArray();

        if (ct.length > 4) {
            Long h = Long.parseLong("" + ct[0] + ct[1]) * 60 * 60 * 1000;
            Long m = Long.parseLong("" + ct[3] + ct[4]) * 60 * 1000;
            Long currentTimeMillis = System.currentTimeMillis();
            this.stopTime = currentTimeMillis / 1000 / 60 / 60 / 24 * 1000 * 60 * 60 * 24 + h + m - 4 * 60 * 60 * 1000;
            if (this.stopTime < currentTimeMillis)
                this.stopTime = this.stopTime + 24 * 60 * 60 * 1000;
        }
        screenshotService.changeScreenNumber(screenNumber);

        runWowBot();

        return "redirect:/";
    }

    @GetMapping(value = "/run")
    public String runWowBotGetController() {
        runWowBot();

        return "redirect:";
    }

    private void runWowBot() {
        windowService.activateWowWindow();

        Long currentTimeMillis = System.currentTimeMillis();
        while (!(countErrors > maxCountErrors) & (currentTimeMillis - 60000 < stopTime)) {
            gameActionService.sleep(2000L);
            /*if (pixelReadingService.isNeedEnter())
                gameActionService.enterButton();*/
            currentTimeMillis = System.currentTimeMillis();
            while ((pixelReadingService.isFishingActive() | pixelReadingService.isPetBattleActive())
                    & (countErrors < maxCountErrors + 2) & (currentTimeMillis - 60000 < stopTime) ) {
                currentTimeMillis = System.currentTimeMillis();
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

                if (currentTimeMillis > stopTime) {
                    logService.saveScreenshot();
                    gameActionService.logout();
                }
            }
        }
    }

    private void petBattle() {
        for (int i = 0; pixelReadingService.isPetBattleActive() & i < commandPetCount; i++) {
            for (int i1 = 0; i1 < 3 & pixelReadingService.isPetBattleActive(); i1++)
                gameActionService.pressSpace();
            if (!pixelReadingService.inMasterBattle())
                gameActionService.changePetCommand(i);
        }
        countErrors = 0;
    }

    private void fishing() {

        if (gameActionService.needReBuff(8 * 60 * 60))
            gameActionService.setFishingRoad();

        if (gameActionService.needReBuff(15 * 60))
            //goToBuff();

        if (gameActionService.needReBuff(2 * 60 * 60))
            gameActionService.sellJunk();

        if (gameActionService.needReBuff(15 * 60, 10))
            gameActionService.setBait();

        if (gameActionService.needReBuff(181))
            gameActionService.firimBall();

        if (gameActionService.needReBuff(20 * 60))
            gameActionService.floatBoat();

        gameActionService.cursorMove();
        gameActionService.castFishingRode();

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

    private void goToBuff() {
        gameActionService.turnTo(6);
        gameActionService.callMount();
        gameActionService.moveUp(2000);
        gameActionService.moveToPoint(new Point(34.637349843979, 56.337487697601), 0.3);
        gameActionService.moveDown(3000);
        gameActionService.moveToPoint(new Point(34.637349843979, 56.337487697601), 0.1);
        gameActionService.interactionWithBuff();

        gameActionService.turnTo(3);
        gameActionService.moveUp(2000);
        gameActionService.moveToPoint(new Point(34.417217969894, 65.928483009338), 0.3);
        gameActionService.moveDown(3000);
        gameActionService.turnTo(6);
        gameActionService.pitchTo(0);
        gameActionService.stopMoving();
    }

    private void rotationHelper() {
        if (pixelReadingService.isPetBattleActive())
            gameActionService.pressSpace();
    }
}