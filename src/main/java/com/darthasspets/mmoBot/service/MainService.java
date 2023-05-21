package com.darthasspets.mmoBot.service;

import com.darthasspets.mmoBot.gameState.GameState;
import org.opencv.core.Point;
import org.springframework.stereotype.Service;

import java.awt.event.KeyEvent;

@Service
public class MainService {
    private final FishingService fishingService;
    private final WindowService windowService;
    private final GameActionService gameActionService;
    private final PixelReadingService pixelReadingService;
    private final LogService logService;
    private final ScreenshotService screenshotService;
    private final GameState gameState;

    public MainService(FishingService fishingService, WindowService windowService, LogService logService,
                       GameActionService gameActionService, PixelReadingService pixelReadingService,
                       ScreenshotService screenshotService, GameState gameState) {
        this.fishingService = fishingService;
        this.windowService = windowService;
        this.gameActionService = gameActionService;
        this.pixelReadingService = pixelReadingService;
        this.logService = logService;
        this.screenshotService = screenshotService;
        this.gameState = gameState;
    }

    public void runMMOBot(int countActsInLogS, int maxCountErrorsS, int commandPetCountS, int screenNumberS,
                          String stopTimeStringS, int botType, int gameType) {

        gameState.initialization(countActsInLogS, maxCountErrorsS, commandPetCountS, screenNumberS,
                stopTimeStringS, botType, gameType);

        //todo logService.recordVideo();

        screenshotService.changeScreenNumber(screenNumberS);
        windowService.activateGameWindow(gameType);

        long currentTimeMillis = System.currentTimeMillis();
        while (!(gameState.getCountErrors() > gameState.getMaxCountErrors()) &&
                (currentTimeMillis - 60000 < gameState.getStopTime())) {
            gameActionService.sleep(2000L);
            pixelReadingService.checkState();

            currentTimeMillis = System.currentTimeMillis();
            while (!gameState.isPaused() &&
                    (gameState.isFishingActive() || gameState.isPetBattleActive() || gameState.isGatheringActive()) &&
                    (gameState.getCountErrors() < gameState.getMaxCountErrors() + 2) &&
                    (currentTimeMillis - 60000 < gameState.getStopTime())) {

                if (gameState.isNeedExit()) {
                    gameActionService.closeGame();
                    windowService.activateGameWindow(gameType);
                    pixelReadingService.checkState();
                }
                if (gameState.isNeedEnter()) {
                    gameActionService.enterGame();
                }

                currentTimeMillis = System.currentTimeMillis();
                windowService.activateGameWindow(gameType);
                gameState.setCountActs(gameState.getCountActs() + 1);

                if (gameState.getCountActs() > gameState.getCountActsInLog() ||
                        gameState.getCountErrors() > gameState.getMaxCountErrors()) {
                    gameState.setCountActs(0);
                    logService.saveScreenshot();
                }

                if (gameState.isFishingActive())
                    fishing();
                else if (gameState.isPetBattleActive())
                    petBattle();
                else if (gameState.isGatheringActive())
                    gathering();

                pixelReadingService.checkState();

                var stopTime = gameState.getStopTime();
                if (!(stopTime == 0) && currentTimeMillis > stopTime) {
                    logService.saveScreenshot();
                    gameActionService.exitGame();
                }
            }
        }
    }

    private void gathering() {
        System.out.println("start circle gathering");
        var endGather = 20;
        for (int i = -1; gameState.isGatheringActive() && !gameState.isPaused() && i < endGather; i++) {
            gameActionService.gatherSafe();
        }
        gameState.setCountErrors(0);
    }

    private void petBattle() {
        System.out.println("start circle petBattle");
        gameState.setCurrentPetTeam(gameState.getCurrentPetTeam() - 1);
        if (gameState.getCurrentPetTeam() < 1)
            gameState.setCurrentPetTeam(gameState.getCommandPetCount());

        var endChange = gameState.getCommandPetCount() - 1 - gameState.getCurrentPetTeam();
        for (int i = -1; gameState.isPetBattleActive() && !gameState.isPaused() &&
                i < endChange; i++) {
            for (int i1 = 0; i1 < 3 && gameState.isPetBattleActive() && !gameState.isPaused(); i1++)
                gameActionService.pressKey(KeyEvent.VK_SPACE);
            //if (!pixelReadingService.inMasterBattle())
            gameActionService.changePetCommand(i);
        }
        gameState.setCountErrors(0);
    }

    private void fishing() {
        System.out.println("start circle fishing");
        /* todo moving
        if (gameActionService.needReBuff(8 * 60 * 60))
            goToBuff();
            gameActionService.setFishingRoad();
            gameActionService.firimBall();
            gameActionService.setBait();
         */

        if (pixelReadingService.isNeedReBuff(60 * 20)) {
            gameActionService.sellJunk();
            gameActionService.floatBoat();
        }

        gameActionService.cursorMove();
        gameActionService.castFishingRode();

        Point xy = fishingService.getFloatXYGuaranteed();
        if (xy.x < 0) {
            System.out.println("Float not found");
            gameState.setCountErrors(gameState.getCountErrors()+1);
            return;
        }
        System.out.println("Start float: " + screenshotService.pointToString(xy));
        boolean checkPecking = fishingService.checkPecking(xy);
        if (!checkPecking) {
            System.out.println("Not pecked");
            gameState.setCountErrors(gameState.getCountErrors()+1);
        }
        else {
            System.out.println("Pecked");
            gameState.setCountErrors(0);
        }
        gameActionService.cursorMove(xy);
        gameActionService.rightMouse();
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
        if (gameState.isPetBattleActive())
            gameActionService.pressKey(KeyEvent.VK_SPACE);
    }

    public void pause() {
        System.out.println("Paused");
        gameState.setPaused(true);
    }

    public void resume() {
        System.out.println("UnPaused");
        gameState.setPaused(false);
    }
}