package com.game.test.gametest.Buildings;

import com.game.test.gametest.MainActivity;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Buildings {

    @SerializedName("type")
    protected MainActivity.RawResourceType type;

    @SerializedName("name")
    protected String name;

    @SerializedName("buildingList")
    List<Building> buildingList;

    @SerializedName("availableBuildings")
    List<Building> availableBuildings;

    // Buildings construction for start of game
    public Buildings() {
        buildingList = new ArrayList<>();
        availableBuildings = new ArrayList<>();
    }

    public void setBuildingList(List<Building> buildingList){
        this.buildingList = buildingList;
    }

    public void constructStartingBuildings() {

        setType(MainActivity.RawResourceType.BUILDING);
        setName("Buildings");

        // Setup default built buildings
        buildingList.add(new Building(Building.Type.HOUSE));
        buildingList.add(new Building(Building.Type.UNBUILT));
        buildingList.add(new Building(Building.Type.UNBUILT));
        buildingList.add(new Building(Building.Type.UNBUILT));

        // Setup default available buildings
        availableBuildings.add(new Building(Building.Type.HOUSE));
        availableBuildings.add(new Building(Building.Type.QUARRY));
        availableBuildings.add(new Building(Building.Type.SAWMILL));
        availableBuildings.add(new Building(Building.Type.WAREHOUSE));
        availableBuildings.add(new Building(Building.Type.WALL));
        availableBuildings.add(new Building(Building.Type.BARRACKS));
    }

    public List<Building> getBuildingList() {
        return buildingList;
    }

    public void addBuilding(Building building) {
        buildingList.add(building);
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
        int builtBuildings = 0;
        for (Building v: buildingList) {
            if (!v.getName().contains("unbuilt")){
                builtBuildings++;
            }
        }
        return builtBuildings;
    }

    public int getMaxQuantity() {
        return buildingList.size();
    }

    public int getWallLevel() {
        for (Building building : buildingList) {
            if (building.getName().equals("Wall")) {
                return building.getLevel();
            }
        }
        return 0;
    }

    public List<Building> getAvailableBuildings() {
        return availableBuildings;
    }

//    public static void setAvailableBuildings(List<Building> availableBuildings) {
//        this.availableBuildings = availableBuildings;
//    }

    public static List<Requirement> getRequirements(Building.Type type) {

        List<Requirement> requirements = new ArrayList<>();
        switch (type) {
            case UNBUILT:
                return requirements;
            case HOUSE: {
                Requirement woodReq = new Requirement(Requirement.Type.WOOD, "Wood", 750, true);
                Requirement stoneReq = new Requirement(Requirement.Type.STONE, "Stone", 250, true);
                Requirement timeReq = new Requirement(Requirement.Type.TIME, "Time", 10, true);
                requirements = new ArrayList<>();
                requirements.add(woodReq);
                requirements.add(stoneReq);
                requirements.add(timeReq);
                return requirements;
            }
            case SAWMILL: {
                Requirement woodReq = new Requirement(Requirement.Type.WOOD,"Wood", 100, true);
                Requirement stoneReq = new Requirement(Requirement.Type.STONE, "Stone", 50, true);
                Requirement timeReq = new Requirement(Requirement.Type.TIME, "Time", 10, true);
                requirements = new ArrayList<>();
                requirements.add(woodReq);
                requirements.add(stoneReq);
                requirements.add(timeReq);
                return requirements;
            }
            case QUARRY: {
                Requirement woodReq = new Requirement(Requirement.Type.WOOD,"Wood", 100, true);
                Requirement stoneReq = new Requirement(Requirement.Type.STONE,"Stone", 50, true);
                Requirement timeReq = new Requirement(Requirement.Type.TIME, "Time", 10, true);
                requirements = new ArrayList<>();
                requirements.add(woodReq);
                requirements.add(stoneReq);
                requirements.add(timeReq);
                return requirements;
            }
            case WAREHOUSE: {
                Requirement woodReq = new Requirement(Requirement.Type.WOOD,"Wood", 100, true);
                Requirement stoneReq = new Requirement(Requirement.Type.STONE,"Stone", 100, true);
                Requirement timeReq = new Requirement(Requirement.Type.TIME, "Time", 10, true);
                requirements = new ArrayList<>();
                requirements.add(woodReq);
                requirements.add(stoneReq);
                requirements.add(timeReq);
                return requirements;
            }
            case WALL: {
                Requirement woodReq = new Requirement(Requirement.Type.WOOD, "Wood", 100, true);
                Requirement stoneReq = new Requirement(Requirement.Type.STONE,"Stone", 200, true);
                Requirement timeReq = new Requirement(Requirement.Type.TIME, "Time", 10, true);
                Requirement onlyOne = new Requirement(Requirement.Type.ONLY_ONE, "Can only have one", 0, false);
                requirements = new ArrayList<>();
                requirements.add(woodReq);
                requirements.add(stoneReq);
                requirements.add(timeReq);
                requirements.add(onlyOne);
                return requirements;
            }
            case BARRACKS: {
                Requirement woodReq = new Requirement(Requirement.Type.WOOD, "Wood", 100, true);
                Requirement stoneReq = new Requirement(Requirement.Type.STONE,"Stone", 200, true);
                Requirement timeReq = new Requirement(Requirement.Type.TIME, "Time", 10, true);
                Requirement onlyOne = new Requirement(Requirement.Type.ONLY_ONE, "Can only have one", 0, false);
                requirements = new ArrayList<>();
                requirements.add(woodReq);
                requirements.add(stoneReq);
                requirements.add(timeReq);
                requirements.add(onlyOne);
                return requirements;
            }
        }
        return requirements;
    }

    public static List<Upgrade> getUpgrades(Building.Type type) {

        List<Upgrade> upgrades = new ArrayList<>();
        switch (type) {
            case UNBUILT:
                return upgrades;
            case HOUSE: {
                Upgrade upgrade1_1 = new Upgrade(1, Upgrade.Type.VILLAGER_MAX, "Extra villager", 1);
//                Upgrade upgrade2_1 = new Upgrade(2, Upgrade.Type.NONE, "-", 0);
                Upgrade upgrade3_1 = new Upgrade(3, Upgrade.Type.VILLAGER_MAX, "Extra villager", 1);
//                Upgrade upgrade4_1 = new Upgrade(4, Upgrade.Type.NONE, "-", 0);
//                Upgrade upgrade5_1 = new Upgrade(5, Upgrade.Type.NONE, "-", 0);
                Upgrade upgrade6_1 = new Upgrade(6, Upgrade.Type.VILLAGER_MAX, "Extra villager", 1);
//                Upgrade upgrade7_1 = new Upgrade(7, Upgrade.Type.NONE, "-", 0);
//                Upgrade upgrade8_1 = new Upgrade(8, Upgrade.Type.NONE, "-", 0);
                Upgrade upgrade9_1 = new Upgrade(9, Upgrade.Type.VILLAGER_MAX, "Extra villager", 1);
                Upgrade upgrade10_1 = new Upgrade(10, Upgrade.Type.VILLAGER_MAX, "Extra villager", 1);

                upgrades.add(upgrade1_1);
//                upgrades.add(upgrade2_1);
                upgrades.add(upgrade3_1);
//                upgrades.add(upgrade4_1);
//                upgrades.add(upgrade5_1);
                upgrades.add(upgrade6_1);
//                upgrades.add(upgrade7_1);
//                upgrades.add(upgrade8_1);
                upgrades.add(upgrade9_1);
                upgrades.add(upgrade10_1);

                return upgrades;
            }
            case SAWMILL: {
                Upgrade upgrade1_1 = new Upgrade(1, Upgrade.Type.WOOD_INC, "Gathering wood is more productive", 1.1f);
                Upgrade upgrade1_2 = new Upgrade(1, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 2000);

                Upgrade upgrade2_1 = new Upgrade(2, Upgrade.Type.WOOD_INC, "Gathering wood is more productive", 1.5f);
                Upgrade upgrade2_2 = new Upgrade(2, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 10000);

                Upgrade upgrade3_1 = new Upgrade(3, Upgrade.Type.WOOD_INC, "Gathering wood is more productive", 2f);
                Upgrade upgrade3_2 = new Upgrade(3, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 20000);

//                Upgrade upgrade4_1 = new Upgrade(4, Upgrade.Type.NONE, "-", 0);

                Upgrade upgrade5_1 = new Upgrade(5, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 50000);

//                Upgrade upgrade6_1 = new Upgrade(6, Upgrade.Type.NONE, "-", 0);

                Upgrade upgrade7_1 = new Upgrade(7, Upgrade.Type.WOOD_INC, "Gathering wood is more productive", 2.5f);

//                Upgrade upgrade8_1 = new Upgrade(8, Upgrade.Type.NONE, "-", 0);

                Upgrade upgrade9_1 = new Upgrade(9, Upgrade.Type.WOOD_INC, "Gathering wood is more productive", 3.5f);
                Upgrade upgrade9_2 = new Upgrade(9, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 500000);

                Upgrade upgrade10_1 = new Upgrade(10, Upgrade.Type.WOOD_INC, "Gathering wood is more productive", 5f);
                Upgrade upgrade10_2 = new Upgrade(10, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 1000000);

                upgrades.add(upgrade1_1);
                upgrades.add(upgrade1_2);
                upgrades.add(upgrade2_1);
                upgrades.add(upgrade2_2);
                upgrades.add(upgrade3_1);
                upgrades.add(upgrade3_2);
//                upgrades.add(upgrade4_1);
                upgrades.add(upgrade5_1);
//                upgrades.add(upgrade6_1);
                upgrades.add(upgrade7_1);
//                upgrades.add(upgrade8_1);
                upgrades.add(upgrade9_1);
                upgrades.add(upgrade9_2);
                upgrades.add(upgrade10_1);
                upgrades.add(upgrade10_2);

                return upgrades;
            }
            case QUARRY: {
                Upgrade upgrade1_1 = new Upgrade(1, Upgrade.Type.STONE_INC, "Gathering wood is more productive", 1.1f);
                Upgrade upgrade1_2 = new Upgrade(1, Upgrade.Type.STONE_MAX, "Extra wood capacity", 2000);

                Upgrade upgrade2_1 = new Upgrade(2, Upgrade.Type.STONE_INC, "Gathering wood is more productive", 1.5f);
                Upgrade upgrade2_2 = new Upgrade(2, Upgrade.Type.STONE_MAX, "Extra wood capacity", 10000);

                Upgrade upgrade3_1 = new Upgrade(3, Upgrade.Type.STONE_INC, "Gathering wood is more productive", 2f);
                Upgrade upgrade3_2 = new Upgrade(3, Upgrade.Type.STONE_MAX, "Extra wood capacity", 20000);

//                Upgrade upgrade4_1 = new Upgrade(4, Upgrade.Type.NONE, "-", 0);

                Upgrade upgrade5_1 = new Upgrade(5, Upgrade.Type.STONE_MAX, "Extra wood capacity", 50000);

//                Upgrade upgrade6_1 = new Upgrade(6, Upgrade.Type.NONE, "-", 0);

                Upgrade upgrade7_1 = new Upgrade(7, Upgrade.Type.STONE_INC, "Gathering wood is more productive", 2.5f);

//                Upgrade upgrade8_1 = new Upgrade(8, Upgrade.Type.NONE, "-", 0);

                Upgrade upgrade9_1 = new Upgrade(9, Upgrade.Type.STONE_INC, "Gathering wood is more productive", 3.5f);
                Upgrade upgrade9_2 = new Upgrade(9, Upgrade.Type.STONE_MAX, "Extra wood capacity", 500000);

                Upgrade upgrade10_1 = new Upgrade(10, Upgrade.Type.STONE_INC, "Gathering wood is more productive", 5f);
                Upgrade upgrade10_2 = new Upgrade(10, Upgrade.Type.STONE_MAX, "Extra wood capacity", 1000000);

                upgrades.add(upgrade1_1);
                upgrades.add(upgrade1_2);
                upgrades.add(upgrade2_1);
                upgrades.add(upgrade2_2);
                upgrades.add(upgrade3_1);
                upgrades.add(upgrade3_2);
//                upgrades.add(upgrade4_1);
                upgrades.add(upgrade5_1);
//                upgrades.add(upgrade6_1);
                upgrades.add(upgrade7_1);
//                upgrades.add(upgrade8_1);
                upgrades.add(upgrade9_1);
                upgrades.add(upgrade9_2);
                upgrades.add(upgrade10_1);
                upgrades.add(upgrade10_2);

                return upgrades;
            }
            case WAREHOUSE: {
                Upgrade upgrade1_1 = new Upgrade(1, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 1000);
                Upgrade upgrade1_2 = new Upgrade(1, Upgrade.Type.FOOD_MAX, "Extra food capacity", 1000);
                Upgrade upgrade1_3 = new Upgrade(1, Upgrade.Type.STONE_MAX, "Extra stone capacity", 1000);

                Upgrade upgrade2_1 = new Upgrade(2, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 1000);
                Upgrade upgrade2_2 = new Upgrade(2, Upgrade.Type.FOOD_MAX, "Extra food capacity", 1000);
                Upgrade upgrade2_3 = new Upgrade(2, Upgrade.Type.STONE_MAX, "Extra stone capacity", 1000);

                Upgrade upgrade3_1 = new Upgrade(3, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 5000);
                Upgrade upgrade3_2 = new Upgrade(3, Upgrade.Type.FOOD_MAX, "Extra food capacity", 5000);
                Upgrade upgrade3_3 = new Upgrade(3, Upgrade.Type.STONE_MAX, "Extra stone capacity", 5000);

                Upgrade upgrade4_1 = new Upgrade(4, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 10000);
                Upgrade upgrade4_2 = new Upgrade(4, Upgrade.Type.FOOD_MAX, "Extra food capacity", 10000);
                Upgrade upgrade4_3 = new Upgrade(4, Upgrade.Type.STONE_MAX, "Extra stone capacity", 10000);

                Upgrade upgrade5_1 = new Upgrade(5, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 10000);
                Upgrade upgrade5_2 = new Upgrade(5, Upgrade.Type.FOOD_MAX, "Extra food capacity", 10000);
                Upgrade upgrade5_3 = new Upgrade(5, Upgrade.Type.STONE_MAX, "Extra stone capacity", 10000);

                Upgrade upgrade6_1 = new Upgrade(6, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 50000);
                Upgrade upgrade6_2 = new Upgrade(6, Upgrade.Type.FOOD_MAX, "Extra food capacity", 50000);
                Upgrade upgrade6_3 = new Upgrade(6, Upgrade.Type.STONE_MAX, "Extra stone capacity", 50000);

                Upgrade upgrade7_1 = new Upgrade(7, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 50000);
                Upgrade upgrade7_2 = new Upgrade(7, Upgrade.Type.FOOD_MAX, "Extra food capacity", 50000);
                Upgrade upgrade7_3 = new Upgrade(7, Upgrade.Type.STONE_MAX, "Extra stone capacity", 50000);

                Upgrade upgrade8_1 = new Upgrade(8, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 100000);
                Upgrade upgrade8_2 = new Upgrade(8, Upgrade.Type.FOOD_MAX, "Extra food capacity", 100000);
                Upgrade upgrade8_3 = new Upgrade(8, Upgrade.Type.STONE_MAX, "Extra stone capacity", 100000);

                Upgrade upgrade9_1 = new Upgrade(9, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 100000);
                Upgrade upgrade9_2 = new Upgrade(9, Upgrade.Type.FOOD_MAX, "Extra food capacity", 100000);
                Upgrade upgrade9_3 = new Upgrade(9, Upgrade.Type.STONE_MAX, "Extra stone capacity", 100000);

                Upgrade upgrade10_1 = new Upgrade(10, Upgrade.Type.WOOD_MAX, "Extra wood capacity", 500000);
                Upgrade upgrade10_2 = new Upgrade(10, Upgrade.Type.FOOD_MAX, "Extra food capacity", 500000);
                Upgrade upgrade10_3 = new Upgrade(10, Upgrade.Type.STONE_MAX, "Extra stone capacity", 500000);

                upgrades.add(upgrade1_1);
                upgrades.add(upgrade1_2);
                upgrades.add(upgrade1_3);
                upgrades.add(upgrade2_1);
                upgrades.add(upgrade2_2);
                upgrades.add(upgrade2_3);
                upgrades.add(upgrade3_1);
                upgrades.add(upgrade3_2);
                upgrades.add(upgrade3_3);
                upgrades.add(upgrade4_1);
                upgrades.add(upgrade4_2);
                upgrades.add(upgrade4_3);
                upgrades.add(upgrade5_1);
                upgrades.add(upgrade5_2);
                upgrades.add(upgrade5_3);
                upgrades.add(upgrade6_1);
                upgrades.add(upgrade6_2);
                upgrades.add(upgrade6_3);
                upgrades.add(upgrade7_1);
                upgrades.add(upgrade7_2);
                upgrades.add(upgrade7_3);
                upgrades.add(upgrade8_1);
                upgrades.add(upgrade8_2);
                upgrades.add(upgrade8_3);
                upgrades.add(upgrade9_1);
                upgrades.add(upgrade9_2);
                upgrades.add(upgrade9_3);
                upgrades.add(upgrade10_1);
                upgrades.add(upgrade10_2);
                upgrades.add(upgrade10_3);

                return upgrades;
            }
            case WALL: {
                Upgrade upgrade1_1 = new Upgrade(1, Upgrade.Type.BUILDING_MAX, "Extra building space", 1);
                Upgrade upgrade2_1 = new Upgrade(2, Upgrade.Type.BUILDING_MAX, "Extra building space", 1);
                Upgrade upgrade3_1 = new Upgrade(3, Upgrade.Type.BUILDING_MAX, "Extra building space", 2);
                Upgrade upgrade4_1 = new Upgrade(4, Upgrade.Type.NONE, "Extra building space", 1);
                Upgrade upgrade5_1 = new Upgrade(5, Upgrade.Type.BUILDING_MAX, "Extra building space", 3);
                Upgrade upgrade6_1 = new Upgrade(6, Upgrade.Type.BUILDING_MAX, "Extra building space", 3);
                Upgrade upgrade7_1 = new Upgrade(7, Upgrade.Type.NONE, "Extra building space", 1);
                Upgrade upgrade8_1 = new Upgrade(8, Upgrade.Type.NONE, "Extra building space", 1);
                Upgrade upgrade9_1 = new Upgrade(9, Upgrade.Type.BUILDING_MAX, "Extra building space", 5);
                Upgrade upgrade10_1 = new Upgrade(10, Upgrade.Type.BUILDING_MAX, "Extra building space", 5);

                upgrades.add(upgrade1_1);
                upgrades.add(upgrade2_1);
                upgrades.add(upgrade3_1);
                upgrades.add(upgrade4_1);
                upgrades.add(upgrade5_1);
                upgrades.add(upgrade6_1);
                upgrades.add(upgrade7_1);
                upgrades.add(upgrade8_1);
                upgrades.add(upgrade9_1);
                upgrades.add(upgrade10_1);

                return upgrades;
            }
            case BARRACKS: {

            }
        }
        return upgrades;
    }
}
