package com.game.test.gametest.Villagers;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VillagerJob {

    private String TAG = "/VillagerJobObject";

    @SerializedName("type")
    private JobType type;

    @SerializedName("name")
    private String name;

    @SerializedName("level")
    private int level;

    @SerializedName("exp")
    private float exp;

    @SerializedName("modifier")
    private float modifier;

    @SerializedName("actions")
    private List<JobAction> actions;

//    @Override
//    public String toString() {
//        return "{type:" + type +
//                ", name:" + name +
//                ", level:" + level +
//                ", exp:" + exp +
//                ", modifier:" + modifier + "}";
//    }

    public enum JobType {
        IDLE, HUNTER, STONEGATHERER, LUMBERJACK, BUILDER, SCOUT, SQUIRE
    }

    public VillagerJob(JobType type, String name, int level, float exp, float modifier, List<JobAction> actions) {
        this.type = type;
        this.name = name;
        this.level = level;
        this.exp = exp;
        this.modifier = modifier;
        this.actions = actions;
    }

    public VillagerJob(JobType type, String name, List<JobAction> actions) {
        this.type = type;
        this.name = name;
        this.actions = actions;
        this.level = 1;
        this.exp = 0;
        calculateModifier();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public void calculateModifier() {
        this.modifier = (1f/level) * .5f;
    }

    public void incExp() {
        exp += modifier;
    }

    public int getExp() {
        return Math.round(exp);
    }

    public float getTrueExp() {
        return exp;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public float getModifier() {
        return modifier;
    }

    public void setModifier(float modifier) {
        this.modifier = modifier;
    }

    public boolean hasActions() {
        switch (type) {
            case SCOUT:
                return true;
        }
        return false;
    }

    public List<JobAction> getActions() {
        return actions;
    }
}
