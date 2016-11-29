package com.game.test.gametest.Villagers;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BattleRank {

    private String TAG = "/BattleRank";

    @SerializedName("frontRank")
    private List<Battler> frontRank;

    @SerializedName("backRank")
    private List<Battler> backRank;

    private List<Battler> battleArray;
    private int battleIndex;
    private int zeroHitCount;


    public BattleRank(List<Battler> frontRank, List<Battler> backRank) {

        this.frontRank = frontRank;
        this.backRank = backRank;
        battleArray = new LinkedList<>();
        battleArray.addAll(frontRank);
        battleArray.addAll(backRank);
        battleIndex = 0;
        zeroHitCount = 0;

        Collections.sort(battleArray, new Comparator<Battler>() {
            @Override
            public int compare(Battler battler1, Battler battler2) {
                // ascending order (descending order would be: name2.compareTo(name1))
                return battler1.getSPD() - battler2.getSPD();
            }
        });
    }

    public void remove(Battler battler) {
        if (frontRank.contains(battler)) {
            frontRank.remove(frontRank.indexOf(battler));
            frontRank.remove(battler);
        } else if (backRank.contains(battler)) {
            backRank.remove(battler);
        }
    }

    public int size() {
        return (frontRank.size() + backRank.size());
    }

    // Gets the next fastest enemy
    public Battler getNext() {
        Log.i(TAG, "battleIndex: " + battleIndex);
        return battleArray.get(battleIndex++);
    }

    public boolean hasNext() {
        return (battleIndex < battleArray.size());
    }

    public void resetIndex() {
        battleIndex = 0;
    }

    public float getSlowestSpeed() { return getSlowest().getSPD(); }

    public Battler getFastest() {
        return battleArray.get(0);
    }

    public Battler getSlowest() {
        return battleArray.get(battleArray.size()-1);
    }

    public int getZeroHitCount() {
        return zeroHitCount;
    }

    public void setZeroHitCount(int zeroHitCount) {
        this.zeroHitCount = zeroHitCount;
    }

    public void returnedHome() {
        for (Battler battler : frontRank) {
            ((Villager) battler).setIsAvailable(true);
        }
        for (Battler battler : backRank) {
            ((Villager) battler).setIsAvailable(true);
        }
    }

    // Gets the front-most enemy, if multiple then picks one at random
    public Battler getFrontBattler() {
        Random randomGenerator = new Random();
        if (frontRank.size() > 0) {
            if (frontRank.size() == 1) {
                return frontRank.get(0);
            }
            return frontRank.get(randomGenerator.nextInt(frontRank.size()));
        } else if (backRank.size() > 0) {
            if (backRank.size() == 1) {
                return backRank.get(0);
            }
            return backRank.get(randomGenerator.nextInt(frontRank.size()));
        }
        return null;
    }

    public void attack(Battler battler, List<String> questLog) {
        // TODO remove these magic numbers and calculate them more eloquently
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        Battler defender = getFrontBattler();

        Log.i(TAG, "defender: " + defender.getName());
        Log.i(TAG, "battler: " + battler.getName());
        if (defender != null) {
            // Check if enemy will evade, or battler will miss
            Random randomGenerator = new Random();
            int missChance = randomGenerator.nextInt(999) + Math.round((defender.getEVA() - battler.getACC()));
            Log.i(TAG, "missChance: " + missChance);
            if (missChance > 975) {
                Log.i(TAG, "missed");
                questLog.add(battler.getName() + " missed!");
            } else {
                // Vary the battler's damage amount, and subtract enemy defense
                float trueDamage = battler.getATK() + (randomGenerator.nextFloat() * battler.getATK() * .1f);
                float defendedDamage = (defender.getDEF() + (randomGenerator.nextFloat() * defender.getDEF() * .1f));
                float finalDamage = trueDamage - defendedDamage;
                Log.i(TAG, "hit damage: " + trueDamage + " defended: " + defendedDamage + " finalDamage: " + finalDamage);
                if (finalDamage > 0) {
                    defender.takesDamage(finalDamage);
                    questLog.add(battler.getName() + " hits " + defender.getName() + " for " + df.format(finalDamage)
                            + "HP   " + defender.getName() + ": " + df.format(defender.getHP()) + " HP");
                    // Reset times we hit for 0 in a row
                    zeroHitCount = 0;
                } else {
                    questLog.add(battler.getName() + " hits " + defender.getName() + " for " + 0
                            + ". " + defender.getName() + ": " + df.format(defender.getHP()) + " HP");
                    // Track how many times we hit for 0 in a row
                    zeroHitCount++;
                }
                // If enemy died remove from ranks
                if (defender.getHP() <= 0) {
                    Log.i(TAG,  defender.getName() + " died");
                    questLog.add(defender.getName() + " has died!");
                    if (defender.getClass().equals(Villager.class)) {
                        ((Villager) defender).killVillager();
                    }
                    remove(defender);
                }
            }
        }
    }
}
