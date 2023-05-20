package com.darthasspets.mmoBot.gameState;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameState {
    boolean wowActive;
    boolean nwActive;
    boolean fishingActive;
    boolean petBattleActive;
    boolean gatheringActive;
    boolean needExit;
    boolean needEnter;
    boolean paused;
    boolean Moving;
    int commandPetCount = 5;
    int currentPetTeam = commandPetCount;
    int countActsInLog = 20;
    int maxCountErrors = 100;
    int countActs;
    int countErrors;
    int screenNumber;
    int toleranceX = 14;
    int toleranceY = 8;
    int toleranceColor = 2;
    int missBeforePeck = 3;
    long startTime = System.currentTimeMillis();
    long stopTime = startTime + 3 * 24 * 60 * 60 * 1000;
    long checkSleep = 200L;
    Rectangle screenBounds;
    Rectangle floatBounds;
    Rectangle floatScreenBounds;

    public void initialization(int countActsInLogS, int maxCountErrorsS, int commandPetCountS, int screenNumberS,
                               String stopTimeStringS, int botType, int gameType) {
        this.setWowActive(gameType == 0);
        this.setCommandPetCount(commandPetCountS);
        this.setCurrentPetTeam(commandPetCountS);
        this.setCountActsInLog(countActsInLogS);
        this.setMaxCountErrors(maxCountErrorsS);
        this.setScreenNumber(screenNumberS);
        this.setFishingActive(botType == 0);
        this.setPetBattleActive(botType == 1);
        this.setGatheringActive(botType == 2);
        this.setWowActive(gameType == 0);
        this.setWowActive(gameType == 0);

        char[] ct = stopTimeStringS.toCharArray();
        /*todo replace to date format
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = formatter.format(source.getCreatedDate());*/

        if (ct.length > 4) {
            long h = Long.parseLong(String.valueOf(ct[0]) + ct[1]) * 60 * 60 * 1000;
            long m = Long.parseLong(String.valueOf(ct[3]) + ct[4]) * 60 * 1000;
            if (!(h == 0) && !(m == 0)) {
                long currentTimeMillis = System.currentTimeMillis();
                this.setStopTime(currentTimeMillis / 1000 / 60 / 60 / 24 * 1000 * 60 * 60 * 24 + h + m - 6 * 60 * 60 * 1000);
                if (this.getStopTime() < currentTimeMillis)
                    this.setStopTime(this.getStopTime() + 24 * 60 * 60 * 1000);
            }
        }
    }
}
