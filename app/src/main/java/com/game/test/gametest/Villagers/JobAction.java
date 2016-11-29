package com.game.test.gametest.Villagers;

import android.util.Log;

import com.game.test.gametest.MainActivity;
import com.game.test.gametest.Quests.Quest;
import com.google.gson.annotations.SerializedName;

public class JobAction {

    private static String TAG = "/JobAction";

    @SerializedName("type")
    private JobAction.Type type;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("villagerID")
    private int villagerID;

    @SerializedName("progress")
    private float progress;

    public enum Type {
        SCOUT
    }

    public JobAction(JobAction.Type type, String name, String description, int villagerID, float progress) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.villagerID = villagerID;
        this.progress = progress;
    }

    public JobAction(int villagerID, JobAction.Type type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.villagerID = villagerID;

        progress = 0;
    }

    public void startAction() {

        Villager villager = MainActivity.resourcesSet.getVillagerList().get(villagerID);
        switch (type) {
            case SCOUT:
                villager.setAvailable(false);
                villager.setIsWorking(true);
                villager.setHasActiveAction(true);
                villager.setCurrentAction(this);
                break;
        }
    }

    public void finishAction() {

        Villager villager = MainActivity.resourcesSet.getVillagerList().get(villagerID);
        villager.setAvailable(true);
        villager.setIsWorking(false);
        villager.setHasActiveAction(false);
        villager.setCurrentAction(null);

        switch (type) {
            case SCOUT:
                Log.i(TAG, "Chance to discover a quest!");
                // Random chance to discover a quest
//                Quest quest = new Quest("Jungle Ruins", "An ancient temple from a long lost civilization", 1, 50);
//                MainActivity.resourcesSet.getQuests().addQuest(quest);
                MainActivity.setUpdateList(true);
                break;
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    public int getVillagerID() {
        return villagerID;
    }

    public void setVillagerID(int villagerID) {
        this.villagerID = villagerID;
    }

    public int getProgress() {
//        Log.i(TAG, "getProgress: " + String.valueOf(Math.round(progress)));
        return Math.round(progress);
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public Villager getVillager() {
        return MainActivity.resourcesSet.getVillagerList().get(villagerID);
    }
}
