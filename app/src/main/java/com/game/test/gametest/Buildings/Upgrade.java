package com.game.test.gametest.Buildings;

import com.google.gson.annotations.SerializedName;

public class Upgrade {

    @SerializedName("type")
    private Upgrade.Type type;

    @SerializedName("name")
    private String name;

    @SerializedName("value")
    private float value;

    @SerializedName("level")
    private int level;

    public enum Type {
        NONE, FOOD_INC, WOOD_INC, STONE_INC, FOOD_MAX, WOOD_MAX, STONE_MAX, VILLAGER_MAX, BUILDING_MAX
    }

    public Upgrade(int level, Type type, String name, float value) {

        this.level = level;
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
