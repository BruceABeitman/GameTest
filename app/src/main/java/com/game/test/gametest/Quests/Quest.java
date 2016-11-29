package com.game.test.gametest.Quests;

import android.util.Log;
import android.widget.TextView;

import com.game.test.gametest.MainActivity;
import com.game.test.gametest.R;
import com.game.test.gametest.Villagers.BattleRank;
import com.game.test.gametest.Villagers.Battler;
import com.game.test.gametest.Villagers.Enemy;
import com.game.test.gametest.Villagers.Villager;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Quest {

    static private String TAG = "/Quest Obj";

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("level")
    private int level;

    @SerializedName("distance")
    private int distance;

    @SerializedName("distance_prog")
    private float distance_prog;

    @SerializedName("distance_prog_rate")
    private float distance_prog_rate;

    @SerializedName("isQuesting")
    private boolean isQuesting = false;

    @SerializedName("isCompleted")
    private boolean isCompleted = false;

    @SerializedName("enemyRanks")
    private BattleRank enemyRanks;

    @SerializedName("allyRanks")
    private BattleRank allyRanks;

    @SerializedName("questLog")
    private List<String> questLog;

    private float proRateInc = 0;

    public Quest(String name, String description, int level, int distance, float distance_prog, float distance_prog_rate, boolean isQuesting, boolean isCompleted, BattleRank allyRanks, BattleRank enemyRanks, List<String> questLog) {

        this.name = name;
        this.description = description;
        this.level = level;
        this.distance = distance;
        this.distance_prog = distance_prog;
        this.distance_prog_rate = distance_prog_rate;
        this.isQuesting = isQuesting;
        this.isCompleted = isCompleted;
        this.allyRanks = allyRanks;
        this.enemyRanks = enemyRanks;
        this.questLog = questLog;
    }

    public Quest(String name, String description, int level, int distance, BattleRank enemyRanks) {

        this.name = name;
        this.description = description;
        this.level = level;
        this.distance = distance;
        this.enemyRanks = enemyRanks;
        this.questLog = new LinkedList<>();
        questLog.add("Quest found!");
    }

    public void startQuesting(BattleRank allyRanks) {

        this.allyRanks = allyRanks;

        // Get slowest battler movement speed
        distance_prog_rate = allyRanks.getSlowestSpeed();
        isQuesting = true;
        Log.i(TAG, "AllyRank size: " + allyRanks.size());
        Log.i(TAG, "distanceprograte: " + distance_prog_rate);
        this.questLog.add("Started quest with " + allyRanks.size() + " allies, traveling at: " + distance_prog_rate + " speed");
    }

    public void incrementProgress(float time) {

        // Increment quest progress if questing
        if (isQuesting) {

            // Check if quest is completed, and we are just returning home
            if (isCompleted) {
                Log.i(TAG, "isComplete proRateInc= " + distance_prog_rate + " * " + time);
                Log.i(TAG, "isComplete distance_prog= " + distance_prog);
                proRateInc = distance_prog_rate * time;

                // If at home then Battlers are back with their spoils
                boolean atHome = ((distance_prog + proRateInc) <= 0);
                if (atHome) {
                    this.questLog.add("Quest complete! " + allyRanks.size() + " allies returned home.");
                    for (String str : questLog) {
                        Log.i(TAG, str);
                    }
                    Log.i(TAG, allyRanks.size() + " are now home.");
                    allyRanks.returnedHome();
                    isQuesting = false;
                    this.setDescription("  completed");

                    // Update the list
                    MainActivity.setUpdateList(true);

                } else {
                    distance_prog -= proRateInc;
                }
            } else {

                // Quest not completed, but we are questing
                Log.i(TAG, "notComplete proRateInc= " + distance_prog_rate + " * " + time);
                Log.i(TAG, "notComplete distance_prog= " + distance_prog);
                Log.i(TAG, "notComplete distance= " + distance);
                proRateInc = distance_prog_rate * time;

                // If completed, perform quest
                boolean atQuest = (Math.round(distance_prog) >= distance);
                Log.i(TAG, "atQuest= " + atQuest);
                if (atQuest) {

                    this.questLog.add("Allies reached quest location!");
                    this.questLog.add(" ---- BATTLE LOG ---- ");

                    // Perform quest
                    // TODO Actual questing
                    battle();

                    // Completed quest
                    // TODO cleanup after completing quest
                    isCompleted = true;

                    // Update the list
                    MainActivity.setUpdateList(true);

                } else {
                    Log.i(TAG, "Updating " + name + ": " + distance_prog);
                    if ((distance_prog + proRateInc) >= distance) {
                        distance_prog = distance;
                    } else {
                        distance_prog += proRateInc;
                    }
                }
            }
        }
    }

    public void battle() {

        Log.i(TAG, "Battling");
        // Initialize with first(fastest) ally and enemy
        Battler ally = allyRanks.getNext();
        Battler enemy = enemyRanks.getNext();

        Log.i(TAG, "Allies: " + allyRanks.size() + " enemies: " + enemyRanks.size());
        Log.i(TAG, ally.getName());
        Log.i(TAG, enemy.getName());
        Log.i(TAG, ally.getName() + " vs " + enemy.getName());
        // Keep battling until one party is destroyed, or allies cannot hit 10 consecutive times
        while (allyRanks.size() > 0 && enemyRanks.size() > 0 && allyRanks.getZeroHitCount() < 10) {
            Log.i(TAG, "Allies: " + allyRanks.size() + " enemies: " + enemyRanks.size());
            // Check if either party has available attackers this round
            if (allyRanks.hasNext() && !enemyRanks.hasNext()) {
                Log.i(TAG, "Ally attack");
                enemyRanks.attack(ally, questLog);
                ally = allyRanks.getNext();
            } else if (!allyRanks.hasNext() && enemyRanks.hasNext()) {
                Log.i(TAG, "Enemy attack");
                allyRanks.attack(enemy, questLog);
                enemy = enemyRanks.getNext();
            } else {
                // Both parties have battlers left, find the fastest
                if ((ally.getSPD() > enemy.getSPD())) {
                    // Ally is currently fastest
                    Log.i(TAG, "Ally attack");
                    enemyRanks.attack(ally, questLog);
                    ally = allyRanks.getNext();
                } else if (enemyRanks.hasNext()) {
                    Log.i(TAG, "Enemy attack");
                    // Enemy is currently fastest, attack ally ranks
                    allyRanks.attack(enemy, questLog);
                    enemy = enemyRanks.getNext();
                }
            }

            // If neither party has battlers left this round, reset the round
            if (!allyRanks.hasNext() && !enemyRanks.hasNext()) {
                allyRanks.resetIndex();
                enemyRanks.resetIndex();
            }
        }

        // Quest is over
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDistance_prog() {
        return distance_prog;
    }

    public void setDistance_prog(float distance_prog) {
        this.distance_prog = distance_prog;
    }

    public float getDistance_prog_rate() {
        return distance_prog_rate;
    }

    public void setDistance_prog_rate(float distance_prog_rate) {
        this.distance_prog_rate = distance_prog_rate;
    }

    public boolean isQuesting() {
        return isQuesting;
    }

    public void setIsQuesting(boolean isQuesting) {
        this.isQuesting = isQuesting;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public BattleRank getAllyRanks() {
        return allyRanks;
    }

    public void setAllyRanks(BattleRank allyRanks) {
        this.allyRanks = allyRanks;
    }

    public float getProRateInc() {
        return proRateInc;
    }

    public void setProRateInc(float proRateInc) {
        this.proRateInc = proRateInc;
    }

    public List<String> getQuestLog() {
        return questLog;
    }

    public void setQuestLog(List<String> questLog) {
        this.questLog = questLog;
    }
}
