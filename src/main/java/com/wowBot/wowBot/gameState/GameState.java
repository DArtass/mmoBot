package com.wowBot.wowBot.gameState;

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
    int commandPetCount = 5;
    int currentPetTeam = commandPetCount;
    int countActsInLog = 20;
    int maxCountErrors = 100;
    int countActs;
    int countErrors;
    Long startTime = System.currentTimeMillis();
    Long stopTime = startTime + 3 * 24 * 60 * 60 * 1000;
    int screenNumber;
    boolean fishingActive;
    boolean petBattleActive;
    boolean needExit;
    boolean needEnter;
    Rectangle screenBounds;
    Rectangle floatBounds;
    Rectangle floatScreenBounds;
    boolean paused;
    int toleranceX = 14;
    int toleranceY = 8;
    int toleranceColor = 2;
    long checkSleep = 200L;
    int missBeforePeck = 3;
    boolean Moving;
}
