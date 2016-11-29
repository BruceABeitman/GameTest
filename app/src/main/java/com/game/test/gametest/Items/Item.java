package com.game.test.gametest.Items;

import com.game.test.gametest.Villagers.VillagerJob;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Item {

    private String TAG = "/Item Obj";

    @SerializedName("Type")
    private Item.type type;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("modifier")
    private float modifier;

    @SerializedName("jobType")
    private List<VillagerJob.JobType> jobTypes;

    @SerializedName("rarity")
    private float rarity;

    @SerializedName("itemBattleStats")
    private List<Integer> itemBattleStats;

    @SerializedName("itemCharacterStats")
    private List<Integer> itemCharacterStats;

    @SerializedName("twoHanded")
    private boolean twoHanded;

    public enum type {
        NOTHING, MAIN_HAND, OFF_HAND, HEAD, NECK, SHOULDER, BACK,
        CHEST, WRIST, GLOVE, WAIST, LEG, FEET, FINGER, FINGER1, FINGER2
    }

    public enum simple {
        NOTHING, WOODCUTTERSAXE, PICKAXE, BOW
    }

    public Item(Item.type type, String name, String description, float modifier, List<VillagerJob.JobType> jobTypes, float rarity, List<Integer> itemBattleStats, List<Integer> itemCharacterStats, boolean twoHanded) {

        this.type = type;
        this.name = name;
        this.description = description;
        this.modifier = modifier;
        this.jobTypes = jobTypes;
        this.rarity = rarity;
        this.itemBattleStats = itemBattleStats;
        this.itemCharacterStats = itemCharacterStats;
        this.twoHanded = twoHanded;
    }

    public Item(Item.type type) {
        Integer[] min;
        Integer[] max;
        Integer[] chance;
        this.type = type;
        this.name = "<none>";
        this.description = "";
        this.modifier = 0;
        this.rarity = 100;
        this.twoHanded = false;

        // TODO Make this an exponential algorithm where additional stat points towards max are less and less likely received
        //                     STR, STM, VIT, DEX, SPD, INT, CHA, SPI, WIL, PER, LCK
        min = new Integer[]    { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
        max = new Integer[]    { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
        chance = new Integer[] { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
        generateRandomCharacterStats(Arrays.asList(min), Arrays.asList(max), Arrays.asList(chance));

        // TODO Make this an exponential algorithm where additional stat points towards max are less and less likely received
        //                     STR, STM, VIT, DEX, SPD, INT, CHA, SPI, WIL, PER, LCK
        min = new Integer[]    { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
        max = new Integer[]    { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
        chance = new Integer[] { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
        generateRandomBattleStats(Arrays.asList(min), Arrays.asList(max), Arrays.asList(chance));
    }

    public Item(Item.simple stype) {

        Integer[] min;
        Integer[] max;
        Integer[] chance;
        switch(stype) {
            case NOTHING:
                this.type = type.NOTHING;
                this.name = "<none>";
                this.description = "";
                this.modifier = 0;
                this.rarity = 100;
                this.twoHanded = false;

                // TODO Make this an exponential algorithm where additional stat points towards max are less and less likely received
                //                     STR, STM, VIT, DEX, SPD, INT, CHA, SPI, WIL, PER, LCK
                min = new Integer[]    { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
                max = new Integer[]    { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
                chance = new Integer[] { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
                generateRandomCharacterStats(Arrays.asList(min), Arrays.asList(max), Arrays.asList(chance));

                // TODO Make this an exponential algorithm where additional stat points towards max are less and less likely received
                //                      HP,  MP, ATK, CCH, CDM, MAG, DEF, MDF, EVA, MEV, ACC
                min = new Integer[]    { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
                max = new Integer[]    { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
                chance = new Integer[] { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
                generateRandomBattleStats(Arrays.asList(min), Arrays.asList(max), Arrays.asList(chance));
                break;
            case WOODCUTTERSAXE:
                this.type = type.MAIN_HAND;
                this.name = "Woodcutter's axe";
                this.description = "A common axe for chopping tress";
                this.modifier = 2;
                this.rarity = 100;
                this.twoHanded = true;
                jobTypes = new ArrayList<>();
                jobTypes.add(VillagerJob.JobType.LUMBERJACK);

                // TODO Make this an exponential algorithm where additional stat points towards max are less and less likely received
                //                     STR, STM, VIT, DEX, SPD, INT, CHA, SPI, WIL, PER, LCK
                min = new Integer[]    { 0,   0,   0,   0,   -1,   0,   0,   0,   0,   0,   0};
                max = new Integer[]    { 0,   0,   0,   0,   -1,   0,   0,   0,   0,   0,   0};
                chance = new Integer[] { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
                generateRandomCharacterStats(Arrays.asList(min), Arrays.asList(max), Arrays.asList(chance));

                // TODO Make this an exponential algorithm where additional stat points towards max are less and less likely received
                //                      HP,  MP, ATK, CCH, CDM, MAG, DEF, MDF, EVA, MEV, ACC
                min = new Integer[]    { 0,   0,   10,   0,   0,   0,   3,   0,   0,   0,   0};
                max = new Integer[]    { 0,   0,   15,   0,   0,   0,   5,   0,   0,   0,   0};
                chance = new Integer[] { 0,   0,   10,   0,   0,   0,   10,   0,   0,   0,   0};
                generateRandomBattleStats(Arrays.asList(min), Arrays.asList(max), Arrays.asList(chance));
                break;
            case PICKAXE:
                this.type = type.MAIN_HAND;
                this.name = "Pick axe";
                this.description = "A common pick axe for splitting rocks";
                this.modifier = 2;
                this.rarity = 100;
                this.twoHanded = true;
                this.jobTypes = new ArrayList<>();
                jobTypes.add(VillagerJob.JobType.STONEGATHERER);

                // TODO Make this an exponential algorithm where additional stat points towards max are less and less likely received
                //                     STR, STM, VIT, DEX, SPD, INT, CHA, SPI, WIL, PER, LCK
                min = new Integer[]    { 0,   0,   0,   0,   -2,   0,   0,   0,   0,   0,   0};
                max = new Integer[]    { 0,   0,   0,   0,   -2,   0,   0,   0,   0,   0,   0};
                chance = new Integer[] { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
                generateRandomCharacterStats(Arrays.asList(min), Arrays.asList(max), Arrays.asList(chance));

                // TODO Make this an exponential algorithm where additional stat points towards max are less and less likely received
                //                      HP,  MP, ATK, CCH, CDM, MAG, DEF, MDF, EVA, MEV, ACC
                min = new Integer[]    { 0,   0,   4,   0,   0,   0,   2,   0,   0,   0,   0};
                max = new Integer[]    { 0,   0,   4,   0,   0,   0,   2,   0,   0,   0,   0};
                chance = new Integer[] { 0,   0,   5,   0,   0,   0,   5,   0,   0,   0,   0};
                generateRandomBattleStats(Arrays.asList(min), Arrays.asList(max), Arrays.asList(chance));
                break;
            case BOW:
                this.type = type.MAIN_HAND;
                this.name = "Bow";
                this.description = "A common bow";
                this.modifier = 2;
                this.rarity = 98;
                this.twoHanded = true;
                this.jobTypes = new ArrayList<>();
                jobTypes.add(VillagerJob.JobType.HUNTER);

                // TODO Make this an exponential algorithm where additional stat points towards max are less and less likely received
                //                     STR, STM, VIT, DEX, SPD, INT, CHA, SPI, WIL, PER, LCK
                min = new Integer[]    { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
                max = new Integer[]    { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
                chance = new Integer[] { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0};
                generateRandomCharacterStats(Arrays.asList(min), Arrays.asList(max), Arrays.asList(chance));

                // TODO Make this an exponential algorithm where additional stat points towards max are less and less likely received
                //                      HP,  MP, ATK, CCH, CDM, MAG, DEF, MDF, EVA, MEV, ACC
                min = new Integer[]    { 0,   0,   15,   0,   0,   0,   0,   0,   0,   0,   0};
                max = new Integer[]    { 0,   0,   15,   0,   0,   0,   0,   0,   0,   0,   0};
                chance = new Integer[] { 0,   0,   8,   0,   0,   0,   0,   0,   0,   0,   0};
                generateRandomBattleStats(Arrays.asList(min), Arrays.asList(max), Arrays.asList(chance));
                break;
        }
    }

    public void generateRandomBattleStats(List<Integer> min, List<Integer> max, List<Integer> chance) {
        itemBattleStats = new ArrayList<>();
        Random randomGenerator = new Random();
        int diff = 0;
        if ((max.get(0) - min.get(0)) > 0) {
            diff = max.get(0) - min.get(0);
            itemBattleStats.add(randomGenerator.nextInt(max.get(0) - min.get(0)) + min.get(0));
        } else {
            itemBattleStats.add(min.get(0));
        }
        if ((max.get(1) - min.get(1)) > 0) {
        itemBattleStats.add(randomGenerator.nextInt(max.get(1) - min.get(1)) + min.get(1));
        } else {
            itemBattleStats.add(min.get(1));
        }
        if ((max.get(2) - min.get(2)) > 0) {
        itemBattleStats.add(randomGenerator.nextInt(max.get(2) - min.get(2)) + min.get(2));
        } else {
            itemBattleStats.add(min.get(2));
        }
        if ((max.get(3) - min.get(3)) > 0) {
        itemBattleStats.add(randomGenerator.nextInt(max.get(3) - min.get(3)) + min.get(3));
        } else {
            itemBattleStats.add(min.get(3));
        }
        if ((max.get(4) - min.get(4)) > 0) {
        itemBattleStats.add(randomGenerator.nextInt(max.get(4) - min.get(4)) + min.get(4));
        } else {
            itemBattleStats.add(min.get(4));
        }
        if ((max.get(5) - min.get(5)) > 0) {
        itemBattleStats.add(randomGenerator.nextInt(max.get(5) - min.get(5)) + min.get(5));
        } else {
            itemBattleStats.add(min.get(5));
        }
        if ((max.get(6) - min.get(6)) > 0) {
        itemBattleStats.add(randomGenerator.nextInt(max.get(6) - min.get(6)) + min.get(6));
        } else {
            itemBattleStats.add(min.get(6));
        }
        if ((max.get(7) - min.get(7)) > 0) {
        itemBattleStats.add(randomGenerator.nextInt(max.get(7) - min.get(7)) + min.get(7));
        } else {
            itemBattleStats.add(min.get(7));
        }
        if ((max.get(8) - min.get(8)) > 0) {
        itemBattleStats.add(randomGenerator.nextInt(max.get(8) - min.get(8)) + min.get(8));
        } else {
            itemBattleStats.add(min.get(8));
        }
        if ((max.get(9) - min.get(9)) > 0) {
        itemBattleStats.add(randomGenerator.nextInt(max.get(9) - min.get(9)) + min.get(9));
        } else {
            itemBattleStats.add(min.get(9));
        }
        if ((max.get(10) - min.get(10)) > 0) {
        itemBattleStats.add(randomGenerator.nextInt(max.get(10) - min.get(10)) + min.get(10));
        } else {
            itemBattleStats.add(min.get(10));
        }
    }

    public void generateRandomCharacterStats(List<Integer> min, List<Integer> max, List<Integer> chance) {
        itemCharacterStats = new ArrayList<>();
        Random randomGenerator = new Random();
        int diff = 0;
        if ((max.get(0) - min.get(0)) > 0) {
            diff = max.get(0) - min.get(0);
            itemCharacterStats.add(randomGenerator.nextInt(max.get(0) - min.get(0)) + min.get(0));
        } else {
            itemCharacterStats.add(min.get(0));
        }
        if ((max.get(1) - min.get(1)) > 0) {
            itemCharacterStats.add(randomGenerator.nextInt(max.get(1) - min.get(1)) + min.get(1));
        } else {
            itemCharacterStats.add(min.get(1));
        }
        if ((max.get(2) - min.get(2)) > 0) {
            itemCharacterStats.add(randomGenerator.nextInt(max.get(2) - min.get(2)) + min.get(2));
        } else {
            itemCharacterStats.add(min.get(2));
        }
        if ((max.get(3) - min.get(3)) > 0) {
            itemCharacterStats.add(randomGenerator.nextInt(max.get(3) - min.get(3)) + min.get(3));
        } else {
            itemCharacterStats.add(min.get(3));
        }
        if ((max.get(4) - min.get(4)) > 0) {
            itemCharacterStats.add(randomGenerator.nextInt(max.get(4) - min.get(4)) + min.get(4));
        } else {
            itemCharacterStats.add(min.get(4));
        }
        if ((max.get(5) - min.get(5)) > 0) {
            itemCharacterStats.add(randomGenerator.nextInt(max.get(5) - min.get(5)) + min.get(5));
        } else {
            itemCharacterStats.add(min.get(5));
        }
        if ((max.get(6) - min.get(6)) > 0) {
            itemCharacterStats.add(randomGenerator.nextInt(max.get(6) - min.get(6)) + min.get(6));
        } else {
            itemCharacterStats.add(min.get(6));
        }
        if ((max.get(7) - min.get(7)) > 0) {
            itemCharacterStats.add(randomGenerator.nextInt(max.get(7) - min.get(7)) + min.get(7));
        } else {
            itemCharacterStats.add(min.get(7));
        }
        if ((max.get(8) - min.get(8)) > 0) {
            itemCharacterStats.add(randomGenerator.nextInt(max.get(8) - min.get(8)) + min.get(8));
        } else {
            itemCharacterStats.add(min.get(8));
        }
        if ((max.get(9) - min.get(9)) > 0) {
            itemCharacterStats.add(randomGenerator.nextInt(max.get(9) - min.get(9)) + min.get(9));
        } else {
            itemCharacterStats.add(min.get(9));
        }
        if ((max.get(10) - min.get(10)) > 0) {
            itemCharacterStats.add(randomGenerator.nextInt(max.get(10) - min.get(10)) + min.get(10));
        } else {
            itemCharacterStats.add(min.get(10));
        }
    }

    public Item.type getType() {
        return type;
    }

    public void setType(Item.type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getModifier() {
        return modifier;
    }

    public void setModifier(float modifier) {
        this.modifier = modifier;
    }

    public float getRarity() {
        return rarity;
    }

    public void setRarity(float rarity) {
        this.rarity = rarity;
    }

    public List<Integer> getItemBattleStats() {
        return itemBattleStats;
    }

    public void setItemBattleStats(List<Integer> itemBattleStats) {
        this.itemBattleStats = itemBattleStats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getItemCharacterStats() {
        return itemCharacterStats;
    }

    public void setItemCharacterStats(List<Integer> itemCharacterStats) {
        this.itemCharacterStats = itemCharacterStats;
    }

    public List<VillagerJob.JobType> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(List<VillagerJob.JobType> jobTypes) {
        this.jobTypes = jobTypes;
    }

    public boolean isTwoHanded() {
        return twoHanded;
    }

    public void setTwoHanded(boolean twoHanded) {
        this.twoHanded = twoHanded;
    }
}
