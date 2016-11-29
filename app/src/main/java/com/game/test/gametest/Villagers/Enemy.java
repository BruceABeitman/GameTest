package com.game.test.gametest.Villagers;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy implements Battler {

    private static String TAG = "/Enemy Obj";

    @SerializedName("type")
    private Type Type;

    @SerializedName("name")
    private String name;

    @SerializedName("charStats")
    private List<Integer> charStats;

    @SerializedName("battleStats")
    private List<Float> battleStats;

    private static int STR = 0;
    private static int STM = 1;
    private static int VIT = 2;
    private static int DEX = 3;
    private static int SPD = 4;
    private static int INT = 5;
    private static int CHA = 6;
    private static int SPI = 7;
    private static int WIL = 8;
    private static int PER = 9;
    private static int LCK = 10;

    private static int HPI = 0;
    private static int MPI = 1;
    private static int ATTACKI = 2;
    private static int CRIT_CHANCEI = 3;
    private static int CRIT_DMGI = 4;
    private static int MAGICI = 5;
    private static int DEFENSEI = 6;
    private static int MDEFENSEI = 7;
    private static int EVASIONI = 8;
    private static int MEVASIONI = 9;
    private static int ACCURACYI = 10;

    public enum Type {
        GRUNT
    }

    public Enemy(Enemy.Type type, String name) {
        this.Type = type;
        this.name = name;
        generateStats(type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void takesDamage(float damage) {
        battleStats.set(HPI, (getHP() - damage));
    }

    private void generateStats(Enemy.Type type) {
        switch(type) {
            case GRUNT:
                // STR, STM, VIT, DEX, SPD, INT, CHA, SPI, WIL, PER, LCK
//                charStats = Arrays.asList(new Integer[] { 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
                if (name == "") {
                    this.name = "Gruntling";
                }
                generateRandomStats(5);
                calculateBattleStats();
        }
    }

    public void generateRandomStats(int max) {
        charStats = new ArrayList<>();
        Random randomGenerator = new Random();
        charStats.add(randomGenerator.nextInt(max) + 1);
        charStats.add(randomGenerator.nextInt(max) + 1);
        charStats.add(randomGenerator.nextInt(max) + 1);
        charStats.add(randomGenerator.nextInt(max) + 1);
        charStats.add(randomGenerator.nextInt(max) + 1);
        charStats.add(randomGenerator.nextInt(max) + 1);
        charStats.add(randomGenerator.nextInt(max) + 1);
        charStats.add(randomGenerator.nextInt(max) + 1);
        charStats.add(randomGenerator.nextInt(max) + 1);
        charStats.add(randomGenerator.nextInt(max) + 1);
        charStats.add(randomGenerator.nextInt(max+1));
    }

    private void calculateBattleStats() {

        battleStats = new ArrayList<>();

        float STRENGTH = charStats.get(STR);
        float STAMINA = charStats.get(STM);
        float VITALITY = charStats.get(VIT);
        float DEXTERITY = charStats.get(DEX);
        float SPEED = charStats.get(SPD);
        float INTELLIGENCE = charStats.get(INT);
        float CHARACTER = charStats.get(CHA);
        float SPIRIT = charStats.get(SPI);
        float WILLPOWER = charStats.get(WIL);
        float PERCEPTION = charStats.get(PER);
        float LUCK = charStats.get(LCK);

        // HP
        battleStats.add((STAMINA * 2f) + STRENGTH + VITALITY);
        // MP
        battleStats.add(VITALITY * (SPIRIT * 10f));
        // Attack
        battleStats.add(STRENGTH);
        // Critical chance
        battleStats.add(DEXTERITY + LUCK);
        // Critical damage
        battleStats.add(SPEED);
        // Magic
        battleStats.add(INTELLIGENCE);
        // Defense
        battleStats.add(VITALITY);
        // Magic Defense
        battleStats.add(VITALITY + (SPIRIT*2));
        // Evasion
        battleStats.add(DEXTERITY + LUCK);
        // Accuracy
        battleStats.add(DEXTERITY + SPEED);
        // Luck
        battleStats.add(LUCK);
    }

    public List<Integer> getCharStats() {
        return charStats;
    }

    public List<Float> getBattleStats() {
        return battleStats;
    }

    public static List<String> getBattleStatNames() {
        List<String> stats = new ArrayList<>();
        stats.add("HP");
        stats.add("MP");
        stats.add("Attack");
        stats.add("Crit. %");
        stats.add("Crit. Dmg");
        stats.add("Magic");
        stats.add("Defense");
        stats.add("Mag. Def.");
        stats.add("Evasion");
        stats.add("Mag. Ev.");
        stats.add("Accuracy");
        return stats;
    }

    public static List<String> getCharStatNames() {
        List<String> stats = new ArrayList<>();
        stats.add("Strength");
        stats.add("Stamina");
        stats.add("Defense");
        stats.add("Dexterity");
        stats.add("Speed");
        stats.add("Intelligence");
        stats.add("Charisma");
        stats.add("Spirit");
        stats.add("Willpower");
        stats.add("Perception");
        stats.add("Luck");
        return stats;
    }

    public int getSTR() {
        return charStats.get(STR);
    }
    public int getSTM() {
        return charStats.get(STM);
    }
    public int getVIT() {
        return charStats.get(VIT);
    }
    public int getDEX() {
        return charStats.get(DEX);
    }
    public int getSPD() {
        return charStats.get(SPD);
    }
    public int getINT() {
        return charStats.get(INT);
    }
    public int getCHA() {
        return charStats.get(CHA);
    }
    public int getSPI() {
        return charStats.get(SPI);
    }
    public int getWIL() {
        return charStats.get(WIL);
    }
    public int getPER() {
        return charStats.get(PER);
    }
    public int getLCK() {
        return charStats.get(LCK);
    }

    public float getHP() {
        return battleStats.get(HPI);
    }
    public float getMP() {
        return battleStats.get(MPI);
    }
    public float getATK() {
        return battleStats.get(ATTACKI);
    }
    public float getCC() {
        return battleStats.get(CRIT_CHANCEI);
    }
    public float getCD() {
        return battleStats.get(CRIT_DMGI);
    }
    public float getMAG() {
        return battleStats.get(MAGICI);
    }
    public float getDEF() {
        return battleStats.get(DEFENSEI);
    }
    public float getMDEF() {
        return battleStats.get(MDEFENSEI);
    }
    public float getEVA() {
        return battleStats.get(EVASIONI);
    }
    public float getMEVA() {
        return battleStats.get(MEVASIONI);
    }
    public float getACC() {
        return battleStats.get(ACCURACYI);
    }

    static public List<Battler> generateEnemyList(Enemy.Type type, int enemyNumber) {
        List<Battler> enemyList = new ArrayList<>();
        switch(type) {
            case GRUNT:
                // STR, STM, VIT, DEX, SPD, INT, CHA, SPI, WIL, PER, LCK
//                charStats = Arrays.asList(new Integer[] { 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
                for (int i=1; i<=enemyNumber; i++) {
                    Log.i(TAG, "Adding enemy: " + (type.toString() + String.valueOf(i)).toString());
                    enemyList.add(new Enemy(type, (type.toString()+String.valueOf(i)).toString()));
                }
        }

        return enemyList;
    }
}
