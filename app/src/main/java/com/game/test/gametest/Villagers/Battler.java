package com.game.test.gametest.Villagers;

import java.util.List;

public interface Battler {

    String getName();
    List<Integer> getCharStats();
    List<Float> getBattleStats();
    void takesDamage(float damage);

    int getSTR();
    int getSTM();
    int getVIT();
    int getDEX();
    int getSPD();
    int getINT();
    int getCHA();
    int getSPI();
    int getWIL();
    int getPER();
    int getLCK();

    float getHP();
    float getMP();
    float getATK();
    float getCC();
    float getCD();
    float getMAG();
    float getDEF();
    float getMDEF();
    float getEVA();
    float getMEVA();
    float getACC();
}
