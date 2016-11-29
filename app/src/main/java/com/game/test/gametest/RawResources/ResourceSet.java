package com.game.test.gametest.RawResources;

import android.util.Log;

import com.game.test.gametest.Buildings.Building;
import com.game.test.gametest.Buildings.Buildings;
import com.game.test.gametest.Items.Item;
import com.game.test.gametest.Items.Items;
import com.game.test.gametest.MainActivity;
import com.game.test.gametest.Quests.Quest;
import com.game.test.gametest.Quests.Quests;
import com.game.test.gametest.Villagers.Villager;
import com.game.test.gametest.Villagers.VillagerJob;
import com.game.test.gametest.Villagers.Villagers;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceSet {

    private String TAG = "/ResourceSet";

    @SerializedName("resourcesList")
    private static List<Object> resourcesList = new ArrayList<>();

    @SerializedName("systemTime")
    private static long systemTime;

//    @Override
//    public String toString() {
//
//        return "{resourcesList: " + resourcesList +
//                "systemTime:" + systemTime + "}";
//    }

    public static Object getResource(MainActivity.RawResourceType rawResourceType) {
        switch (rawResourceType) {
            case FOOD:
                return resourcesList.get(0);
            case WOOD:
                return resourcesList.get(1);
            case STONE:
                return resourcesList.get(2);
            case VILLAGER:
                return resourcesList.get(3);
            case BUILDING:
                return resourcesList.get(4);
            case QUEST:
                return resourcesList.get(5);
            case ITEM:
                return resourcesList.get(6);
        }
        return null;
    }

    public Food getFood() {
        return (Food) resourcesList.get(0);
    }

    public Wood getWood() {
        return (Wood) resourcesList.get(1);
    }

    public Stone getStone() {
        return (Stone) resourcesList.get(2);
    }

    public void incrementMaterialResources(float time) {
        getFood().incrementResource(time);
        getWood().incrementResource(time);
        getStone().incrementResource(time);

        if (getFood().getQuantity() < 0) {
            getVillagers().starveVillager();
        }
    }

    public static List<Object> getResourcesList() {
        return resourcesList;
    }

    public static List<Object> getMaterialResourcesList() {
        List<Object> Materials = new ArrayList<>();
        Materials.add(resourcesList.get(0));
        Materials.add(resourcesList.get(1));
        Materials.add(resourcesList.get(2));
        return Materials;
    }

    public static List<Object> getNonMaterialResourcesList() {
        List<Object> Materials = new ArrayList<>();
        Materials.add(resourcesList.get(3));
        Materials.add(resourcesList.get(4));
        Materials.add(resourcesList.get(5));
        Materials.add(resourcesList.get(6));
        return Materials;
    }

    public static List<Object> getNonMaterialChildrenResourcesList() {
        List<Object> nonMaterialChildren = new ArrayList<>();
        nonMaterialChildren.add(getVillagerList());
        nonMaterialChildren.add(getBuildingList());
        nonMaterialChildren.add(getQuestList());
        nonMaterialChildren.add(getItemList());
        Log.i("RESOURCESET", "nonMaterialChildren size: " + nonMaterialChildren.size());
        return nonMaterialChildren;
    }

    public void add(Object rawR) {
        resourcesList.add(rawR);
        Log.i(TAG, "list size: " + resourcesList.size());
    }

    public int size() {
        return resourcesList.size();
    }

    public static List<Villager> getVillagerList() {
//        Villagers villagers = (Villagers) getResource(MainActivity.RawResourceType.VILLAGER);
        return ((Villagers) getResource(MainActivity.RawResourceType.VILLAGER)).getVillagerList();
    }

    public static Villagers getVillagers() {
        return (Villagers) getResource(MainActivity.RawResourceType.VILLAGER);
    }

    public static List<Building> getBuildingList() {
        Buildings buildings = (Buildings) getResource(MainActivity.RawResourceType.BUILDING);
        return buildings.getBuildingList();
    }

    public static Buildings getBuildings() {
        return (Buildings) getResource(MainActivity.RawResourceType.BUILDING);
    }

    public static List<Quest> getQuestList() {
        return ((Quests) getResource(MainActivity.RawResourceType.QUEST)).getQuests();
    }

    public static Quests getQuests() {
        return (Quests) getResource(MainActivity.RawResourceType.QUEST);
    }

    public static List<Item> getItemList() {
        return ((Items) getResource(MainActivity.RawResourceType.ITEM)).getItems();
    }

    public static Items getItems() {
        return (Items) getResource(MainActivity.RawResourceType.ITEM);
    }

    public void changeModifier(VillagerJob.JobType jobType, float modifier) {

        switch (jobType) {
            case HUNTER:
                ((Food) getResource(MainActivity.RawResourceType.FOOD)).setVillagerModifier((((Food) getResource(MainActivity.RawResourceType.FOOD)).getVillagerModifier() + modifier));
                ((Food) getResource(MainActivity.RawResourceType.FOOD)).calculateIncRate();
                break;
            case LUMBERJACK:
                ((Wood) getResource(MainActivity.RawResourceType.WOOD)).setVillagerModifier((((Wood) getResource(MainActivity.RawResourceType.WOOD)).getVillagerModifier() + modifier));
                ((Wood) getResource(MainActivity.RawResourceType.WOOD)).calculateIncRate();
                break;
            case STONEGATHERER:
                ((Stone) getResource(MainActivity.RawResourceType.STONE)).setVillagerModifier((((Stone) getResource(MainActivity.RawResourceType.STONE)).getVillagerModifier() + modifier));
                ((Stone) getResource(MainActivity.RawResourceType.STONE)).calculateIncRate();
                break;
        }
        if (jobType.equals(VillagerJob.JobType.IDLE)) {

        } else if (jobType.equals(VillagerJob.JobType.HUNTER)) {
            Food food = (Food) getResource(MainActivity.RawResourceType.FOOD);
//            Log.i(TAG, "IncRate: " + rawR.getIncRate());
            food.setIncRate((food.getIncRate()));
            Log.i(TAG, food.getName() + " IncRate: " + food.getIncRate());
        } else if (jobType.equals(VillagerJob.JobType.LUMBERJACK)) {
            Wood wood = (Wood) getResource(MainActivity.RawResourceType.WOOD);
//            Log.i(TAG, "IncRate: " + rawR.getIncRate());
            wood.setIncRate((wood.getIncRate()));
            Log.i(TAG, wood.getName() + " IncRate: " + wood.getIncRate());
        } else if (jobType.equals(VillagerJob.JobType.STONEGATHERER)) {
            Stone stone = (Stone) getResource(MainActivity.RawResourceType.STONE);
//            Log.i(TAG, "stone: " + rawR.getIncRate());
            stone.setIncRate((stone.getIncRate()));
            Log.i(TAG, stone.getName() + " IncRate: " + stone.getIncRate());
        } else if (jobType.equals(VillagerJob.JobType.BUILDER)) {

        }
    }

    public static void setResourcesList(List<Object> resourcesList) {
        ResourceSet.resourcesList = resourcesList;
    }
//    public static boolean isMaterialResource(Object obj) {
//        if (rawR.getType().equals(MainActivity.RawResourceType.FOOD) ||
//            rawR.getType().equals(MainActivity.RawResourceType.STONE) ||
//            rawR.getType().equals(MainActivity.RawResourceType.WOOD)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public static long getSystemTime() {
        return systemTime;
    }

    public static void setSystemTime(long systemTime) {
        ResourceSet.systemTime = systemTime;
    }
}
