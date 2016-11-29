package com.game.test.gametest;

import android.content.Context;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.game.test.gametest.Buildings.Buildings;
import com.game.test.gametest.Items.Items;
import com.game.test.gametest.Quests.Quest;
import com.game.test.gametest.Quests.Quests;
import com.game.test.gametest.RawResources.Food;
import com.game.test.gametest.RawResources.ResourceSet;
import com.game.test.gametest.RawResources.Stone;
import com.game.test.gametest.RawResources.Wood;
import com.game.test.gametest.Villagers.BattleRank;
import com.game.test.gametest.Villagers.Battler;
import com.game.test.gametest.Villagers.Enemy;
import com.game.test.gametest.Villagers.Villager;
import com.game.test.gametest.Villagers.Villagers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bbeitman on 11/28/16.
 */

public class QuestTest extends ActivityUnitTestCase<MainActivity> {

    BattleRank enemyRanks;
    BattleRank allyRanks;
    Quest quest;
    ResourceSet resourcesSet;

//    @Mock
//    Context mockContext;

    public QuestTest() {
        super(MainActivity.class);
        enemyRanks = new BattleRank(Enemy.generateEnemyList(Enemy.Type.GRUNT, 2), Enemy.generateEnemyList(Enemy.Type.GRUNT, 0));
        quest = new Quest("Jungle Ruins", "An ancient temple from a long lost civilization", 1, 3, enemyRanks);

        resourcesSet = new ResourceSet();
        resourcesSet.add(new Food());
        resourcesSet.add(new Wood());
        resourcesSet.add(new Stone());
        resourcesSet.add(new Villagers());
        resourcesSet.add(new Buildings());
        resourcesSet.add(new Quests());
        resourcesSet.add(new Items());
    }

    @SmallTest
    public void testBattleWeakVillager() {

        MainActivity.resourcesSet = resourcesSet;

        Villager testVillager = new Villager(0, "testVillager");
        List<Battler> frontArray = new ArrayList<>();
        // Strength Stamina Defense Dexterity Speed Intelligence Charisma Spirit Willpower Perception Luck
        // Weakest possible villager
        testVillager.setCharStats(Arrays.asList(new Integer[]{1,1,1,1,1,1,1,1,1,1,0}));
        frontArray.add(testVillager);
        allyRanks = new BattleRank(frontArray, new ArrayList<Battler>());
        quest.setAllyRanks(allyRanks);
        quest.battle();
    }

    @SmallTest
    public void testBattleStrongVillager() {

        Villager testVillager = new Villager(0, "testVillager");
        List<Battler> frontArray = new ArrayList<>();
        // Strength Stamina Defense Dexterity Speed Intelligence Charisma Spirit Willpower Perception Luck
        // Strongest possible villager
        testVillager.setCharStats(Arrays.asList(new Integer[]{9,9,9,9,9,9,9,9,9,9,9}));
        frontArray.add(testVillager);
        allyRanks = new BattleRank(frontArray, new ArrayList<Battler>());
        quest.setAllyRanks(allyRanks);
        quest.battle();
    }

    @SmallTest
    public void testBattleMixVillager() {

        Villager testVillager = new Villager(0, "testVillager");
        List<Battler> frontArray = new ArrayList<>();
        // Strength Stamina Defense Dexterity Speed Intelligence Charisma Spirit Willpower Perception Luck
        // Weakest strength, highest defense possible villager
        testVillager.setCharStats(Arrays.asList(new Integer[]{1,9,9,9,9,9,9,9,9,9,9}));
        frontArray.add(testVillager);
        allyRanks = new BattleRank(frontArray, new ArrayList<Battler>());
        quest.setAllyRanks(allyRanks);
        quest.battle();
    }


}
