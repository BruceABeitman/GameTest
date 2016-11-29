package com.game.test.gametest.Villagers;

import android.util.Log;

import com.game.test.gametest.MainActivity;
import com.google.gson.annotations.SerializedName;

import java.security.DomainCombiner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Villagers {

    private String TAG = "/VillageRS Obj";

    @SerializedName("type")
    protected MainActivity.RawResourceType type;

    @SerializedName("villagerList")
    private List<Villager> villagerList;

    @SerializedName("name")
    protected String name;

    @SerializedName("incRate")
    protected float incRate;

    public Villagers() {
        this.villagerList = new ArrayList<>();
    }

    public Villagers(List<Villager> villagerList, MainActivity.RawResourceType type, String name, float incRate) {
        this.villagerList = villagerList;
        this.type = type;
        this.name = name;
        this.incRate = incRate;
    }

    public void constructStartingVillagers() {

        this.type = MainActivity.RawResourceType.VILLAGER;
        this.name = "Villagers";

        Villager villager0 = new Villager(0, "villager0");
        Villager villager1 = new Villager(1, "villager1");

        villager0.startConsumingFood();
        villager1.startConsumingFood();

        villagerList = new ArrayList<>();
        villagerList.add(villager0);
        villagerList.add(villager1);
    }

    public void setVillagerList(List<Villager> villagerList) {
        this.villagerList = villagerList;
    }

    public List<Villager> getVillagerList() {
//        Log.i("Villagers", "villagerList size: " + villagerList.size());
        return villagerList;
    }

    public void addVillager(Villager villager) {
        villagerList.add(villager);
    }

    public int getQuantity() {
        int liveVillagers = 0;
        for (Villager v: villagerList) {
            if (!v.getName().contains("nobody")){
                liveVillagers++;
            }
        }
        return liveVillagers;
    }

    public int getMaxQuantity() {
        return villagerList.size();
    }

    public boolean haveIdleBuilder() {
        for (Villager v : villagerList) {
            if (v.getCurrentJob().getType().equals(VillagerJob.JobType.BUILDER) && v.isAvailable()) {
//                Log.i(TAG, "haveIdleBuilding returning true");
                return true;
            }
        }
        Log.i(TAG, "haveIdleBuilding returning false");
        return false;
    }

    public List<Villager> getIdleBuilders() {
        List<Villager> idleBuilders = new ArrayList<Villager>();
        for (Villager v : villagerList) {
            if (v.getCurrentJob().getType().equals(VillagerJob.JobType.BUILDER) && v.isAvailable()) {
                idleBuilders.add(v);
            }
        }
        return idleBuilders;
    }

    public void newBlankVillager() {
        Log.i(TAG, "New villager spot opened up!");
        Villager newVillager = new Villager(villagerList.size(), "nobody" + String.valueOf(villagerList.size()));
        villagerList.add(newVillager);
    }

    public void createVillager(Villager villager) {

        // change villager from nobody to somebody
        villager.setName(villager.getName().replace("nobody", "villager"));

        // consume resources for new villager
        villager.takeRequirements();

        // allow new villager to start consuming food
        villager.startConsumingFood();

        // update list
        MainActivity.setUpdateList(true);
    }

    public void starveVillager() {

        // get an unlucky, starved villager at random
        Random randomGenerator = new Random();
        int villagerPos = randomGenerator.nextInt((getMaxQuantity() - 1));
        Villager unluckyVillager = villagerList.get(villagerPos);

        // make sure villager isn't dead already
        if (unluckyVillager.getName().contains("nobody")) {
            for (Villager v : villagerList) {
                unluckyVillager = v;
                if (unluckyVillager.getName().contains("nobody")) {
                    break;
                }
            }
        }

        //TODO Check for all dead villagers
        unluckyVillager.killVillager();
    }

    public List<Villager> getBattlers() {
        List<Villager> battlers = new ArrayList<>();
        for (Villager v : villagerList) {
            if (v.isBattler()) {
                battlers.add(v);
            }
        }
        return battlers;
    }
}
