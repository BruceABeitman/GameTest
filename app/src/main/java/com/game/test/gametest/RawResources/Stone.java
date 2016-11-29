package com.game.test.gametest.RawResources;

import android.support.annotation.RawRes;

import com.game.test.gametest.MainActivity;
import com.google.gson.annotations.SerializedName;

public class Stone {

    static private String TAG = "/Stone";

    @SerializedName("type")
    protected MainActivity.RawResourceType type;

    @SerializedName("name")
    protected String name;

    @SerializedName("quantity")
    protected float quantity;

    @SerializedName("maxQuantity")
    protected int maxQuantity;

    @SerializedName("incRate")
    protected float incRate;

    @SerializedName("villagerModifier")
    protected float villagerModifier;

    @SerializedName("buildingModifier")
    protected float buildingModifier = 1;

    private float proRatedInc;

    public Stone(MainActivity.RawResourceType type, String name, float quantity, int maxQuantity, float incRate, float villagerModifier, float buildingModifier) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.maxQuantity = maxQuantity;
        this.incRate = incRate;
        this.villagerModifier = villagerModifier;
        this.buildingModifier = buildingModifier;
    }

    public Stone() {

        this.type = MainActivity.RawResourceType.STONE;
        this.name = "Stone";
        this.quantity = 250;
        this.maxQuantity = 2000;
        this.incRate = 0f;

//        setType(RawResourceType.STONE);
//        setName("Stone");
//        setQuantity(250);
//        setMaxQuantity(2000);
//        setIncRate(0f);
    }

    public void incrementResource(float time) {

        // Get proRated incrementer in case returning from paused game
        proRatedInc = incRate * time;

        // If we are increasing our quantity, check for maxQuantity ceiling
        if (proRatedInc > 0) {
            // If increasing, check if new quantity value will be over maxQuantity
            if ((quantity + proRatedInc) > maxQuantity) {
                quantity = maxQuantity;
            }
            else {
                quantity += proRatedInc;
            }
        } else {
            // If we are decreasing, check if new quantity will be below 0
            if ((quantity + proRatedInc) < 0) {
                quantity = 0;
            } else {
                quantity += proRatedInc;
            }
        }
    }

    public MainActivity.RawResourceType getType() {
        return type;
    }

    public void setType(MainActivity.RawResourceType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return Math.round(quantity);
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public float getIncRate() {
        return incRate;
    }

    public void setIncRate(float incRate) {
        this.incRate = incRate;
    }

    public String getApproxIncRate() {
        if ((incRate < .00001) && (incRate > -.00001)){
            return "0";
        } else if (incRate > 0) {
            return "+" + String.format("%.1f", incRate);
        } else {
            return String.format("%.1f", incRate);
        }
    }

    public float getVillagerModifier() {
        return villagerModifier;
    }

    public void setVillagerModifier(float villagerModifier) {
        this.villagerModifier = villagerModifier;
    }

    public float getBuildingModifier() {
        return buildingModifier;
    }

    public void setBuildingModifier(float buildingModifier) {
        this.buildingModifier = buildingModifier;
    }

    public float getProRatedInc() {
        return proRatedInc;
    }

    public void setProRatedInc(float proRatedInc) {
        this.proRatedInc = proRatedInc;
    }

    public void calculateIncRate() {
        this.incRate = villagerModifier * buildingModifier;
    }
}
