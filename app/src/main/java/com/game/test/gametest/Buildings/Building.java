package com.game.test.gametest.Buildings;

import android.util.Log;

import com.game.test.gametest.MainActivity;
import com.game.test.gametest.Villagers.Villager;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Building {

    private String TAG = "Building Object";

    @SerializedName("buildingType")
    private Building.Type buildingType;

    @SerializedName("name")
    private String name;

    @SerializedName("level")
    private int level;

    @SerializedName("progress")
    private float progress;

    @SerializedName("modifier")
    private float modifier;

    private float proRateInc;

    @SerializedName("isBuilding")
    private boolean isBuilding;

    @SerializedName("Builder")
    private int builderID;

    @SerializedName("requirements")
    private List<Requirement> requirements;

    @SerializedName("upgrades")
    private List<Upgrade> upgrades;

    public enum Type {
        UNBUILT, HOUSE, SAWMILL, QUARRY, WAREHOUSE, WALL, BARRACKS
    }

    public Building (Building.Type type, String name, int level, int progress, float modifier, boolean isBuilding, List<Requirement> requirements, List<Upgrade> upgrades) {
        this.buildingType = type;
        this.name = name;
        this.level = level;
        this.progress = progress;
        this.modifier = modifier;
        this.isBuilding = isBuilding;
        this.requirements = requirements;
        this.upgrades = upgrades;
    }

    public Building (Building.Type type) {
        setupNewBuilding(type, 1);
    }

    private void setupNewBuilding(Building.Type type, int level) {
        this.buildingType = type;
        switch (type) {
            case UNBUILT:
                this.name = "unbuilt" + String.valueOf(MainActivity.resourcesSet.getBuildings().getMaxQuantity() + 1);
                this.requirements = Buildings.getRequirements(type);
                this.upgrades = Buildings.getUpgrades(type);
                break;
            case HOUSE: {
                this.name = "House";
                this.level = level;
                this.requirements = Buildings.getRequirements(type);
                this.upgrades = Buildings.getUpgrades(type);
                break;
            }
            case SAWMILL: {
                this.name = "Sawmill";
                this.level = level;
                this.requirements = Buildings.getRequirements(type);
                this.upgrades = Buildings.getUpgrades(type);
                break;
            }
            case QUARRY: {
                this.name = "Quarry";
                this.level = level;
                this.requirements = Buildings.getRequirements(type);
                this.upgrades = Buildings.getUpgrades(type);
                break;
            }
            case WAREHOUSE: {
                this.name = "Warehouse";
                this.level = level;
                this.requirements = Buildings.getRequirements(type);
                this.upgrades = Buildings.getUpgrades(type);
                break;
            }
            case WALL: {
                this.name = "Wall";
                this.level = level;
                this.requirements = Buildings.getRequirements(type);
                this.upgrades = Buildings.getUpgrades(type);
                break;
            }
            case BARRACKS: {
                this.name = "Barracks";
                this.level = level;
                this.requirements = Buildings.getRequirements(type);
                this.upgrades = Buildings.getUpgrades(type);
            }
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getProgress() {
        int result = Math.round(100 * (progress/getTimeRequirement()));
        if (result > 100) {
            return 100;
        } else {
            return result;
        }
    }

    public int getTimeRequirement() {
        for (Requirement req : requirements) {
            if (req.getType().equals(Requirement.Type.TIME)) {
                return req.getValue();
            }
        }
        return 1;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public float getModifier() {
        return modifier;
    }

    public void setModifier(float modifier) {
        this.modifier = modifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public boolean isBuilding() {
        return this.isBuilding;
    }

    public void setIsBuilding(boolean isBuilding) {
        this.isBuilding = isBuilding;
    }

    public Villager getBuilder() {
        return MainActivity.resourcesSet.getVillagerList().get(builderID);
    }

    public void setBuilder(Villager builder) {
        this.builderID = MainActivity.resourcesSet.getVillagerList().indexOf(builder);
    }

    public void calculateModifier() {
        this.modifier = (1f/level) * .5f;
    }

    public void incrementProgress(float time) {

        // Increment building progress if building is getting built
        if (isBuilding) {

//            Log.i("Building Obj", "proRateInc= " + MainActivity.resourcesSet.getVillagerList().get(builderID).getModifier() + " * " + time);
            proRateInc = MainActivity.resourcesSet.getVillagerList().get(builderID).getModifier() * time;
            // If completed, perform completion for the building
            boolean completed = ((progress + proRateInc) >= getTimeRequirement());
            if (completed) {

                // Standard building level up overhead
                progress = 0;
                level += 1;
                MainActivity.resourcesSet.getVillagerList().get(builderID).setIsWorking(false);
                MainActivity.resourcesSet.getVillagerList().get(builderID).setAvailable(true);
                builderID = -1;
                isBuilding = false;
                calculateModifier();

                for (Requirement req : requirements) {
                    if (req.isIncremental()) {
                        req.setValue(req.getValue() * 2);
                    }
                }

                // Perform buildilng level up overhead unique to this building type
                upgradeBuilding();
                Log.i("Building Obj", getName() + " leveled up! Mod: " + modifier + " new level: " + getLevel());

                // Update the list
                MainActivity.setUpdateList(true);

            } else {
//                Log.i(TAG, "Updating " + name + ": " + progress);
                progress += proRateInc;
            }
        }
    }

    public void upgradeBuilding() {
        Log.i(TAG, "Upgrading: " + name + ", type: " + buildingType);
        switch (buildingType) {
            case HOUSE:
                MainActivity.resourcesSet.getVillagers().newBlankVillager();
                break;
            case QUARRY:
//                MainActivity.resourcesSet.getVillagers().newBlankVillager();
                break;
            case SAWMILL:
//                MainActivity.resourcesSet.getVillagers().newBlankVillager();
                break;
            case WAREHOUSE:
                MainActivity.resourcesSet.getVillagers().newBlankVillager();
                break;
            case WALL:
                MainActivity.resourcesSet.getVillagers().newBlankVillager();
                break;
        }
    }

    public boolean requirementsMet() {
        for (Requirement req : requirements) {
            switch (req.getType()) {
                case FOOD:
                    if (req.getValue() > MainActivity.resourcesSet.getFood().getQuantity()) {
                        Log.i(TAG, "Food required: " + req.getValue() + " current food: " + MainActivity.resourcesSet.getFood().getQuantity());
                        return false;
                    }
                    break;
                case WOOD:
                    if (req.getValue() > MainActivity.resourcesSet.getWood().getQuantity()) {
                        Log.i(TAG, "Wood required: " + req.getValue() + " current wood: " + MainActivity.resourcesSet.getWood().getQuantity());
                        return false;
                    }
                    break;
                case STONE:
                    if (req.getValue() > MainActivity.resourcesSet.getStone().getQuantity()) {
                        Log.i(TAG, "Stone required: " + req.getValue() + " current stone: " + MainActivity.resourcesSet.getStone().getQuantity());
                        return false;
                    }
                    break;
                case ONLY_ONE:
                    if (MainActivity.resourcesSet.getBuildings().getWallLevel() != 0) {
                        return false;
                    }
            }
        }
        return true;
    }

    public void consumeRequirements() {
        for (Requirement req : requirements) {
            switch (req.getType()) {
                case FOOD:
                    MainActivity.resourcesSet.getFood().setQuantity(MainActivity.resourcesSet.getFood().getQuantity() - req.getValue());
                    break;
                case WOOD:
                    MainActivity.resourcesSet.getWood().setQuantity(MainActivity.resourcesSet.getWood().getQuantity() - req.getValue());
                    break;
                case STONE:
                    MainActivity.resourcesSet.getStone().setQuantity(MainActivity.resourcesSet.getStone().getQuantity() - req.getValue());
                    break;
            }
        }
    }

    public void createBuilding(Building.Type buildingType) {

        Log.i(TAG, "Starting new building: " + name);
        // change unbuilt buildling into correct type
        this.buildingType = buildingType;
        setupNewBuilding(buildingType, 0);

        // update list
        MainActivity.setUpdateList(true);
    }

    public void takeRequirements() {
        for (Requirement req : requirements) {
            switch (req.getType()) {
                case FOOD:
                    MainActivity.resourcesSet.getFood().setQuantity(MainActivity.resourcesSet.getFood().getQuantity() - req.getValue());
                    break;
                case WOOD:
                    MainActivity.resourcesSet.getWood().setQuantity(MainActivity.resourcesSet.getWood().getQuantity() - req.getValue());
                    break;
                case STONE:
                    MainActivity.resourcesSet.getStone().setQuantity(MainActivity.resourcesSet.getStone().getQuantity() - req.getValue());
                    break;
            }
        }
    }

    public Type getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(Type buildingType) {
        this.buildingType = buildingType;
    }

    public List<Upgrade> getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(List<Upgrade> upgrades) {
        this.upgrades = upgrades;
    }

    public int getBuilderID() {
        return builderID;
    }

    public void setBuilderID(int builderID) {
        this.builderID = builderID;
    }

    public float getProRateInc() {
        return proRateInc;
    }

    public void setProRateInc(float proRateInc) {
        this.proRateInc = proRateInc;
    }
}
