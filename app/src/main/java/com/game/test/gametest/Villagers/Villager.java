package com.game.test.gametest.Villagers;

import android.util.Log;

import com.game.test.gametest.Items.Item;
import com.game.test.gametest.MainActivity;
import com.game.test.gametest.Buildings.Requirement;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Villager implements Battler {

    static private String TAG = "Villager Obj";

    @SerializedName("type")
    private MainActivity.RawResourceType type = MainActivity.RawResourceType.VILLAGER;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("currentJob")
    private VillagerJob currentJob;

    @SerializedName("currentAction")
    private JobAction currentAction;

    @SerializedName("jobSet")
    private List<VillagerJob> jobSet;

    @SerializedName("modifier")
    private float modifier;

    @SerializedName("toolModifier")
    private float toolModifier;

    @SerializedName("foodConsumption")
    private float foodConsumption;

    @SerializedName("isAvailable")
    private boolean isAvailable = true;

    @SerializedName("isWorking")
    private boolean isWorking = false;

    @SerializedName("hasActiveAction")
    private boolean hasActiveAction = false;

    @SerializedName("requirements")
    private List<Requirement> requirements;

    @SerializedName("charStats")
    private List<Integer> charStats;

    @SerializedName("itemBattleStats")
    private List<Integer> itemBattleStats;

    @SerializedName("itemCharacterStats")
    private List<Integer> itemCharacterStats;

    @SerializedName("battleStats")
    private List<Float> battleStats;

    @SerializedName("equipmentSet")
    private EquipmentSet equipmentSet;

    private static int STR = 0;
    private static int STM = 1;
    private static int VIT = 2;
    private static int DEX = 3;
    private static int SPD = 4;
    private static int INT = 5;
    private static int CHA = 6;
    private static int SPI = 7;
    private static int WIL = 8;
    private static int PER = 9;
    private static int LCK = 10;

    private static int HPI = 0;
    private static int MPI = 1;
    private static int ATTACKI = 2;
    private static int CRIT_CHANCEI = 3;
    private static int CRIT_DMGI = 4;
    private static int MAGICI = 5;
    private static int DEFENSEI = 6;
    private static int MDEFENSEI = 7;
    private static int EVASIONI = 8;
    private static int MEVASIONI = 9;
    private static int ACCURACYI = 10;

    private float proRateInc;

    public Villager(MainActivity.RawResourceType type, int id, String name, VillagerJob currentJob, JobAction currentAction, List<VillagerJob> jobSet, float modifier, float foodConsumption,
                    boolean isAvailable, boolean isWorking, boolean hasActiveAction, List<Requirement> requirements, List<Integer> charStats, List<Integer> itemBattleStats, List<Float> battleStats,
                    List<Integer> itemCharacterStats, EquipmentSet equipmentSet) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.currentJob = currentJob;
        this.currentAction = currentAction;
        this.jobSet = jobSet;
        this.modifier = modifier;
        this.foodConsumption = foodConsumption;
        this.isAvailable = isAvailable;
        this.isWorking = isWorking;
        this.hasActiveAction = hasActiveAction;
        this.requirements = requirements;
        this.charStats = charStats;
        this.itemCharacterStats = itemCharacterStats;
        this.itemBattleStats = itemBattleStats;
        this.battleStats = battleStats;
        this.equipmentSet = equipmentSet;
    }

    public Villager(int id, String name) {

        // Set requirements to create a
        requirements = new ArrayList<>();

        Requirement foodReq = new Requirement(Requirement.Type.FOOD, "Food", 100, false);
        requirements.add(foodReq);

        // set name, job, food consumption/second, and initialize equipment
        this.id = id;
        this.name = name;
        this.foodConsumption = -.2f;
        this.equipmentSet = new EquipmentSet();

        // create default jobs
        setupDefaultJobs();
        this.currentJob = jobSet.get(0);

        // generate random stats
        generateRandomStats();

        // calculate battle stats
        calculateBattleStats();

        initializeItemStats();
    }

    private void initializeItemStats() {
        itemBattleStats = new ArrayList<>();
        itemBattleStats.add(0);
        itemBattleStats.add(0);
        itemBattleStats.add(0);
        itemBattleStats.add(0);
        itemBattleStats.add(0);
        itemBattleStats.add(0);
        itemBattleStats.add(0);
        itemBattleStats.add(0);
        itemBattleStats.add(0);
        itemBattleStats.add(0);
        itemBattleStats.add(0);

        itemCharacterStats = new ArrayList<>();
        itemCharacterStats.add(0);
        itemCharacterStats.add(0);
        itemCharacterStats.add(0);
        itemCharacterStats.add(0);
        itemCharacterStats.add(0);
        itemCharacterStats.add(0);
        itemCharacterStats.add(0);
        itemCharacterStats.add(0);
        itemCharacterStats.add(0);
        itemCharacterStats.add(0);
        itemCharacterStats.add(0);
    }

    private void calculateBattleStats() {

        battleStats = new ArrayList<>();

        float STRENGTH = charStats.get(STR);
        float STAMINA = charStats.get(STM);
        float VITALITY = charStats.get(VIT);
        float DEXTERITY = charStats.get(DEX);
        float SPEED = charStats.get(SPD);
        float INTELLIGENCE = charStats.get(INT);
        float CHARACTER = charStats.get(CHA);
        float SPIRIT = charStats.get(SPI);
        float WILLPOWER = charStats.get(WIL);
        float PERCEPTION = charStats.get(PER);
        float LUCK = charStats.get(LCK);

        // HP
        battleStats.add((STAMINA * 2f) + STRENGTH + VITALITY);
        // MP
        battleStats.add(VITALITY * (SPIRIT * 10f));
        // Attack
        battleStats.add(STRENGTH);
        // Critical chance
        battleStats.add(DEXTERITY + LUCK);
        // Critical damage
        battleStats.add(SPEED);
        // Magic
        battleStats.add(INTELLIGENCE);
        // Defense
        battleStats.add(VITALITY);
        // Magic Defense
        battleStats.add(VITALITY + (SPIRIT*2));
        // Evasion
        battleStats.add(DEXTERITY + LUCK);
        // Accuracy
        battleStats.add(DEXTERITY + SPEED);
        // Luck
        battleStats.add(LUCK);
    }

    public boolean isBattler() {
        switch (currentJob.getType()) {
            case SQUIRE:
                return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void startConsumingFood() {
        // Place modifier on food for a single person
        MainActivity.resourcesSet.getFood().deltaIncRate(foodConsumption);
    }

    public void stopConsumingFood() {
        // Place modifier on food for a single person
        MainActivity.resourcesSet.getFood().deltaIncRate(-1 * foodConsumption);
    }

    public void setupDefaultJobs() {

        JobAction noActions = new JobAction(id, null, null, null);
        List<JobAction> noActionList = new ArrayList<>();
        noActionList.add(noActions);
        VillagerJob idle = new VillagerJob(VillagerJob.JobType.IDLE, "Idle", noActionList);
        VillagerJob hunter = new VillagerJob(VillagerJob.JobType.HUNTER, "Hunter", noActionList);
        VillagerJob lumberjack = new VillagerJob(VillagerJob.JobType.LUMBERJACK, "Lumberjack", noActionList);
        VillagerJob stoneGatherer = new VillagerJob(VillagerJob.JobType.STONEGATHERER, "StoneGatherer", noActionList);
        VillagerJob builder = new VillagerJob(VillagerJob.JobType.BUILDER, "Builder", noActionList);
        // For testing purposes
        JobAction scoutActions = new JobAction(id, JobAction.Type.SCOUT, "Scout", "Scout the surrounding area for areas of interest.");
        List<JobAction> scoutActionList = new ArrayList<>();
        scoutActionList.add(scoutActions);
        VillagerJob scout = new VillagerJob(VillagerJob.JobType.SCOUT, "Scout", scoutActionList);
        VillagerJob squire = new VillagerJob(VillagerJob.JobType.SQUIRE, "Squire", noActionList);

        jobSet = new ArrayList<>();
        jobSet.add(idle);
        jobSet.add(hunter);
        jobSet.add(lumberjack);
        jobSet.add(stoneGatherer);
        jobSet.add(builder);
        jobSet.add(scout);
        jobSet.add(squire);
    }

    public List<Integer> getCharStats() {
        return charStats;
    }

    public void setCharStats(List<Integer> newStats) {
        this.charStats = newStats;
        calculateBattleStats();
    }

    public List<Float> getBattleStats() {
        return battleStats;
    }

    public List<Integer> getItemBattleStats() {
        return itemBattleStats;
    }

    public List<Integer> getItemCharacterStats() {
        return itemCharacterStats;
    }


    public static List<String> getBattleStatNames() {
        List<String> stats = new ArrayList<>();
        stats.add("HP");
        stats.add("MP");
        stats.add("Attack");
        stats.add("Crit. %");
        stats.add("Crit. Dmg");
        stats.add("Magic");
        stats.add("Defense");
        stats.add("Mag. Def.");
        stats.add("Evasion");
        stats.add("Mag. Ev.");
        stats.add("Accuracy");
        return stats;
    }

    public static List<String> getCharStatNames() {
        List<String> stats = new ArrayList<>();
        stats.add("Strength");
        stats.add("Stamina");
        stats.add("Defense");
        stats.add("Dexterity");
        stats.add("Speed");
        stats.add("Intelligence");
        stats.add("Charisma");
        stats.add("Spirit");
        stats.add("Willpower");
        stats.add("Perception");
        stats.add("Luck");
        return stats;
    }

    public void takesDamage(float damage) {
        battleStats.set(HPI, (getHP() - damage));
    }

    private void removeModifier() {
        calculateModifier();
        Log.i(TAG, "removeModifier: " + modifier);
        MainActivity.resourcesSet.changeModifier(currentJob.getType(), (modifier * -1));
    }

    private void addModifier() {
        calculateModifier();
        Log.i(TAG, "addModifier: " + modifier);
        MainActivity.resourcesSet.changeModifier(currentJob.getType(), modifier);
    }

    public void killVillager() {
        // change villager from somebody to nobody, recalculate stats
        setName("nobody" + String.valueOf(MainActivity.resourcesSet.getVillagerList().indexOf(this)));
        generateRandomStats();
        setupDefaultJobs();

        // stop dead villagers food consumption
        stopConsumingFood();

        // generate food from unlucky villager
        MainActivity.resourcesSet.getFood().setQuantity(MainActivity.resourcesSet.getFood().getQuantity() + 250);

        // refresh list
        MainActivity.setUpdateList(true);
    }

    // Take a time value to calculate increment dependent on how much time has passed
    // This is higher when the user first comes back to the app after closing it
    public void incrementExp(float time) {

        proRateInc = currentJob.getModifier() * time;
        // Increment both villager experience, and any job actions
        if (currentJob != null) {

            // Check if villager is currently working
            if (isWorking) {

                // If leveled up, perform level up for job
                int levels = Math.round((currentJob.getExp() + proRateInc) / 100);
                if (levels > 0) {

                    currentJob.setExp(0);
                    // remove current resource modification with current level
                    removeModifier();


                    // increase current job's level, and increase respective stats
                    // Check for multiple level ups
                    currentJob.setLevel(currentJob.getLevel() + levels);
                    incStats(levels);

                    // recalculate villager's modifier for leveling
                    currentJob.calculateModifier();
                    // recalculate villager's resource modifier with updated level
                    addModifier();

                    // Set exp to be remainder of exp not used to level up
                    currentJob.setExp((currentJob.getTrueExp() + proRateInc) % 100);

                    Log.i("Villager Obj", getName() + " leveled up! Mod: " + modifier + " new level: " + currentJob.getLevel());
                    MainActivity.setUpdateList(true);
                } else {
                    currentJob.setExp(currentJob.getTrueExp() + proRateInc);
                }
            }

            // Check if villager is currently performing an action
            if (hasActiveAction) {
                // Check if new progress will complete the action
                boolean completedAction = (currentAction.getProgress() + proRateInc) >= 100;
                if (completedAction) {
                    currentAction.setProgress(0f);
                    currentAction.finishAction();
                } else {
                    currentAction.setProgress(currentAction.getProgress() + proRateInc);
                }
            }
        }
    }

    private void incStats(int levels) {

        int STRENGTH = charStats.get(STR);
        int STAMINA = charStats.get(STM);
        int VITALITY = charStats.get(VIT);
        int DEXTERITY = charStats.get(DEX);
        int SPEED = charStats.get(SPD);
        int INTELLIGENCE = charStats.get(INT);
        int CHARACTER = charStats.get(CHA);
        int SPIRIT = charStats.get(SPI);
        int WILLPOWER = charStats.get(WIL);
        int PERCEPTION = charStats.get(PER);
        int LUCK = charStats.get(LCK);

        switch(currentJob.getType()) {
            case HUNTER:
                DEXTERITY += levels;
                PERCEPTION += levels;
                modifier = currentJob.getLevel() * DEXTERITY * PERCEPTION * .08f * (LUCK * .2f);
                break;
            case LUMBERJACK:
                STRENGTH += levels;
                STAMINA += levels;
                modifier = currentJob.getLevel() * STRENGTH * STAMINA * .08f;
                break;
            case STONEGATHERER:
                STRENGTH += (2*levels);
                STAMINA += levels;
                modifier = currentJob.getLevel() * (1.5f*STRENGTH) * (.5f*STAMINA) * .08f;
                break;
            case BUILDER:
                STRENGTH += levels;
                DEXTERITY += levels;
                STAMINA += levels;
                INTELLIGENCE += levels;
                modifier = currentJob.getLevel() * STRENGTH * DEXTERITY * STAMINA * INTELLIGENCE * .03f;
                break;
        }

        // recalculate battle statistics
        calculateBattleStats();
    }

    private void calculateModifier() {
        int STRENGTH = charStats.get(STR);
        int STAMINA = charStats.get(STM);
        int VITALITY = charStats.get(VIT);
        int DEXTERITY = charStats.get(DEX);
        int SPEED = charStats.get(SPD);
        int INTELLIGENCE = charStats.get(INT);
        int CHARACTER = charStats.get(CHA);
        int SPIRIT = charStats.get(SPI);
        int WILLPOWER = charStats.get(WIL);
        int PERCEPTION = charStats.get(PER);
        int LUCK = charStats.get(LCK);

        switch(currentJob.getType()) {
            case HUNTER:
                modifier = (currentJob.getLevel()*.15f) * (DEXTERITY*.25f) * (PERCEPTION*.25f) * 1.4f * ((LUCK*.3f + .5f)) + .3f;
                break;
            case SCOUT:
                modifier = (currentJob.getLevel()*.15f) * (DEXTERITY*.15f) * (PERCEPTION*.35f) * 1.4f * ((LUCK*.6f + .5f)) + .3f;
                break;
            case LUMBERJACK:
                modifier = (currentJob.getLevel()*.3f) * (STRENGTH*.25f) * (STAMINA*.25f) * 1.4f;
                break;
            case STONEGATHERER:
                modifier = (currentJob.getLevel()*.3f) * (STRENGTH*.75f) * (.15f*STAMINA) * 1.4f;
                break;
            case BUILDER:
                modifier = (currentJob.getLevel()*.3f) * (STRENGTH*.25f) * (DEXTERITY*.25f) * (STAMINA*.25f) * (INTELLIGENCE*.25f) * .75f;
                break;
        }
        toolModifier = 0;
        // If current job is a valid job for boosting modifier, then add equipped main-hand modifier
        if (equipmentSet.getMainHand().getJobTypes() != null) {
            if (equipmentSet.getMainHand().getJobTypes().contains(currentJob.getType())) {
                toolModifier += equipmentSet.getMainHand().getModifier();
            }
        }
        modifier += toolModifier;
    }

    public boolean hasActions() {
        return getCurrentJob().hasActions();
    }

    public void generateRandomStats() {
        charStats = new ArrayList<>();
        Random randomGenerator = new Random();
        charStats.add(randomGenerator.nextInt(9) + 1);
        charStats.add(randomGenerator.nextInt(9) + 1);
        charStats.add(randomGenerator.nextInt(9) + 1);
        charStats.add(randomGenerator.nextInt(9) + 1);
        charStats.add(randomGenerator.nextInt(9) + 1);
        charStats.add(randomGenerator.nextInt(9) + 1);
        charStats.add(randomGenerator.nextInt(9) + 1);
        charStats.add(randomGenerator.nextInt(9) + 1);
        charStats.add(randomGenerator.nextInt(9) + 1);
        charStats.add(randomGenerator.nextInt(9) + 1);
        charStats.add(randomGenerator.nextInt(10));
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
            }
        }
        return true;
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

    public List<VillagerJob> getJobSet() {
        return jobSet;
    }

    public void setJobSet(List<VillagerJob> jobSet) {
        this.jobSet = jobSet;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public float getModifier() {
        return modifier;
    }

    public void setModifier(float modifier) {
        this.modifier = modifier;
    }

    public float getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(float foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MainActivity.RawResourceType getType() {
        return type;
    }

    public void setType(MainActivity.RawResourceType type) {
        type = type;
    }

    public VillagerJob getJob() {
        return currentJob;
    }

    public void setJob(VillagerJob job) {

        removeModifier();
        this.currentJob = job;
        addModifier();

        Log.i("Villager Obj", "Village Job was: " + getCurrentJob().getType() + " changed to: " + (job.getType()) + " modifier is: " + modifier);

        // Setup jobs with automatic work to show villager is working
        switch (job.getType()) {
            case HUNTER:
                isWorking = true;
                break;
            case SCOUT:
                isWorking = false;
                break;
            case LUMBERJACK:
                isWorking = true;
                break;
            case STONEGATHERER:
                isWorking = true;
                break;
            case BUILDER:
                isWorking = false;
                break;
            case SQUIRE:
                isWorking = false;
                break;
        }
    }

    public static int getJobDropDownPosition(Villager villager) {
        switch (villager.getJob().getType()) {
            case IDLE:
                return 0;
            case HUNTER:
                return 1;
            case LUMBERJACK:
                return 2;
            case STONEGATHERER:
                return 3;
            case BUILDER:
                return 4;
            case SCOUT:
                return 5;
            case SQUIRE:
                return 6;
        }
        return 0;
    }

    public void addJob(VillagerJob villagerJob) {
        jobSet.add(villagerJob);
    }

    public List<VillagerJob> getAvailableJobs() {
        return jobSet;
    }

    public List<String> getAvailableJobNames() {
        List<String> jobNames = new ArrayList<>();
        for (VillagerJob vj : jobSet){
            jobNames.add(vj.getName());
        }
        return jobNames;
    }

    public VillagerJob getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(VillagerJob current_job) {
        this.currentJob = current_job;
    }

    public JobAction getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(JobAction currentAction) {
        this.currentAction = currentAction;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public boolean isHasActiveAction() {
        return hasActiveAction;
    }

    public void setHasActiveAction(boolean hasActiveAction) {
        this.hasActiveAction = hasActiveAction;
    }

    public EquipmentSet getEquipmentSet() {
        return equipmentSet;
    }

    public void setEquipmentSet(EquipmentSet equipmentSet) {
        this.equipmentSet = equipmentSet;
    }

    public void addItemStats(Item item) {
        Log.i(TAG, "Updating stats with: " + item.getItemBattleStats());
        int index = 0;
        for (int stat : itemBattleStats) {
            itemBattleStats.set(index, (stat += item.getItemBattleStats().get(index)));
            index++;
        }
        index = 0;
        for (int stat : itemCharacterStats) {
            itemCharacterStats.set(index, (stat += item.getItemCharacterStats().get(index)));
            index++;
        }
    }

    public void subtractItemStats(Item item) {
        int index = 0;
        for (int stat : itemBattleStats) {
            itemBattleStats.set(index, stat -= item.getItemBattleStats().get(index));
            index++;
        }
        index = 0;
        for (int stat : itemCharacterStats) {
            itemCharacterStats.set(index, (stat -= item.getItemCharacterStats().get(index)));
            index++;
        }
    }

    /**
     * Handle equiping a new item on the villager
     * @param item
     */
    public void equip(Item item) {

        // Set the old item as unequipped
        MainActivity.resourcesSet.getItems().itemUnequipped(getEquipmentSet().getEquip(item.getType()));
        // Set the new item as now equipped
        MainActivity.resourcesSet.getItems().itemEquipped(item);

        removeModifier();
        // Unequip old item and equip new item
        getEquipmentSet().unequip(this, item.getType());
        getEquipmentSet().equip(this, item);

        // Update modifier in case new/old item gives modifier boost
        addModifier();
    }

    public float getToolModifier() {
        return toolModifier;
    }

    public void setToolModifier(float toolModifier) {
        this.toolModifier = toolModifier;
    }

    public int getSTR() {
        return charStats.get(STR);
    }
    public int getSTM() {
        return charStats.get(STM);
    }
    public int getVIT() {
        return charStats.get(VIT);
    }
    public int getDEX() {
        return charStats.get(DEX);
    }
    public int getSPD() {
        return charStats.get(SPD);
    }
    public int getINT() {
        return charStats.get(INT);
    }
    public int getCHA() {
        return charStats.get(CHA);
    }
    public int getSPI() {
        return charStats.get(SPI);
    }
    public int getWIL() {
        return charStats.get(WIL);
    }
    public int getPER() {
        return charStats.get(PER);
    }
    public int getLCK() {
        return charStats.get(LCK);
    }

    public float getHP() {
        return battleStats.get(HPI);
    }
    public float getMP() {
        return battleStats.get(MPI);
    }
    public float getATK() {
        return battleStats.get(ATTACKI);
    }
    public float getCC() {
        return battleStats.get(CRIT_CHANCEI);
    }
    public float getCD() {
        return battleStats.get(CRIT_DMGI);
    }
    public float getMAG() {
        return battleStats.get(MAGICI);
    }
    public float getDEF() {
        return battleStats.get(DEFENSEI);
    }
    public float getMDEF() {
        return battleStats.get(MDEFENSEI);
    }
    public float getEVA() {
        return battleStats.get(EVASIONI);
    }
    public float getMEVA() {
        return battleStats.get(MEVASIONI);
    }
    public float getACC() {
        return battleStats.get(ACCURACYI);
    }
}
