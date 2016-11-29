package com.game.test.gametest.Buildings;

import com.google.gson.annotations.SerializedName;

public class Requirement {

    @SerializedName("type")
    private Requirement.Type type;

    @SerializedName("name")
    private String name;

    @SerializedName("value")
    private int value;

    @SerializedName("incremental")
    private boolean incremental;

    public enum Type {
        FOOD, WOOD, STONE, TIME, ONLY_ONE
    }

    public Requirement(Requirement.Type type, String name, int value, boolean incremental) {

        this.type = type;
        this.name = name;
        this.value = value;
        this.incremental = incremental;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isIncremental() {
        return incremental;
    }

    public void setIncremental(boolean incremental) {
        this.incremental = incremental;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
