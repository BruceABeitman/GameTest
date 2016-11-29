package com.game.test.gametest;

import java.util.Iterator;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.game.test.gametest.Adapters.MyExpandableAdapter;
import com.game.test.gametest.Items.Item;
import com.game.test.gametest.Items.Items;

import com.game.test.gametest.Buildings.Building;
import com.game.test.gametest.Buildings.BuildingLogic;
import com.game.test.gametest.Buildings.Buildings;
import com.game.test.gametest.Quests.Quest;
import com.game.test.gametest.Quests.QuestLogic;
import com.game.test.gametest.Quests.Quests;
import com.game.test.gametest.RawResources.Food;
import com.game.test.gametest.RawResources.ResourceSet;
import com.game.test.gametest.RawResources.Stone;
import com.game.test.gametest.Villagers.BattleRank;
import com.game.test.gametest.Villagers.Enemy;
import com.game.test.gametest.Villagers.Villager;
import com.game.test.gametest.Villagers.VillagerJob;
import com.game.test.gametest.Villagers.VillagerLogic;
import com.game.test.gametest.Villagers.Villagers;
import com.game.test.gametest.RawResources.Wood;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends ExpandableListActivity {

    private static String TAG = "/MainActivity";
    public static ResourceSet resourcesSet;

    /** This application's preferences label */
    private static final String PREFS_NAME = "com.our.package.UserPrefs";
    /** This application's preferences */
    private static SharedPreferences settings;
    /** This application's settings editor*/
    private static SharedPreferences.Editor editor;

    private static String KEY_SYSTIME = "system_time";
    private static String KEY_FOOD = "food";
    private static String KEY_WOOD = "wood";
    private static String KEY_STONE = "stone";
    private static String KEY_VILLAGERS = "villagers";
    private static String KEY_BUILDINGS = "buildings";
    private static String KEY_QUESTS = "quests";
    private static String KEY_ITEMS = "items";
    private static int TIMER_FREQ = 1000;
    private static long timeDiff = 0;
    private static MyExpandableAdapter adapter = null;
    private static boolean updateList = false;

    private ExpandableListView expandableList = null;
    private static int absent_gathering_rate = 100;
    private static QuestLogic questLogic;
    private static BuildingLogic buildingLogic;
    private static VillagerLogic villagerLogic;

    public enum RawResourceType {
        VILLAGER, FOOD, STONE, WOOD, BUILDING, QUEST, ITEM
    }

    private TextView rawRName_food;
    private TextView rawRQuantity_food;
    private TextView rawRMaxQuantity_food;
    private TextView rawRName_wood;
    private TextView rawRQuantity_wood;
    private TextView rawRMaxQuantity_wood;
    private TextView rawRName_stone;
    private TextView rawRQuantity_stone;
    private TextView rawRMaxQuantity_stone;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            new LoadResources().execute();
            timerHandler.postDelayed(this, TIMER_FREQ);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create static objects with additional logic
        questLogic = new QuestLogic(MainActivity.this);
        buildingLogic = new BuildingLogic(MainActivity.this);
        villagerLogic = new VillagerLogic(MainActivity.this);

        if(settings == null){
            settings = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        editor = settings.edit();

        rawRName_food = (TextView) findViewById(R.id.rawr_name_food);
        rawRQuantity_food = (TextView) findViewById(R.id.rawr_quantity_food);
        rawRMaxQuantity_food = (TextView) findViewById(R.id.rawr_maxquantity_food);

        rawRName_wood = (TextView) findViewById(R.id.rawr_name_wood);
        rawRQuantity_wood = (TextView) findViewById(R.id.rawr_quantity_wood);
        rawRMaxQuantity_wood = (TextView) findViewById(R.id.rawr_maxquantity_wood);

        rawRName_stone = (TextView) findViewById(R.id.rawr_name_stone);
        rawRQuantity_stone = (TextView) findViewById(R.id.rawr_quantity_stone);
        rawRMaxQuantity_stone = (TextView) findViewById(R.id.rawr_maxquantity_stone);

        String food_json = settings.getString(KEY_FOOD, "default");
        String wood_json = settings.getString(KEY_WOOD, " default ");
        String stone_json = settings.getString(KEY_STONE, " default ");
        String villagers_json = settings.getString(KEY_VILLAGERS, " default ");
        String buildings_json = settings.getString(KEY_BUILDINGS, " default ");
        String quests_json = settings.getString(KEY_QUESTS, " default ");
        String items_json = settings.getString(KEY_ITEMS, " default ");

        Log.i(TAG, "food_json: " + food_json);
        Log.i(TAG, "wood_json: " + wood_json);
        Log.i(TAG, "stone_json: " + stone_json);
        Log.i(TAG, "villagers_json: " + villagers_json);
        Log.i(TAG, "buildings_json: " + buildings_json);
        Log.i(TAG, "quests_json: " + quests_json);
        Log.i(TAG, "items_json: " + items_json);

        // Check for first run, fill resources with initial amounts for a new game
        if (food_json.equals("default")) {
            resourcesSet = new ResourceSet();
            resourcesSet.add(new Food());
            resourcesSet.add(new Wood());
            resourcesSet.add(new Stone());
            resourcesSet.add(new Villagers());
            resourcesSet.getVillagers().constructStartingVillagers();
            resourcesSet.add(new Buildings());
            resourcesSet.getBuildings().constructStartingBuildings();
            resourcesSet.add(new Quests());
            resourcesSet.add(new Items());
            resourcesSet.getItems().constructStartingItems();

            // Create dummy quest for test
            BattleRank enemyRanks = new BattleRank(Enemy.generateEnemyList(Enemy.Type.GRUNT, 2), Enemy.generateEnemyList(Enemy.Type.GRUNT, 0));
            Quest quest = new Quest("Jungle Ruins", "An ancient temple from a long lost civilization", 1, 3, enemyRanks);
            resourcesSet.getQuests().addQuest(quest);
        } else {

            // If we are returning from paused game, get difference in time since pause
            Long system_time = settings.getLong(KEY_SYSTIME, 0);
            // Apply absent gathering rate, which diminishes incrementing when game is not active
            timeDiff = ((System.currentTimeMillis() - system_time) * absent_gathering_rate)/100;

            // Grab persisted data
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            Food food = gson.fromJson(food_json, Food.class);
            Wood wood = gson.fromJson(wood_json, Wood.class);
            Stone stone = gson.fromJson(stone_json, Stone.class);
            Villagers villagers = gson.fromJson(villagers_json, Villagers.class);
            Buildings buildings = gson.fromJson(buildings_json, Buildings.class);
            Quests quests = gson.fromJson(quests_json, Quests.class);
            Items items = gson.fromJson(items_json, Items.class);

            resourcesSet = new ResourceSet();
            resourcesSet.add(food);
            resourcesSet.add(wood);
            resourcesSet.add(stone);
            resourcesSet.add(villagers);
            resourcesSet.add(buildings);
            resourcesSet.add(quests);
            resourcesSet.add(items);
            Log.i(TAG, "time diff: " + timeDiff);
        }

        // Setup the main expandable list
        expandableList = getExpandableListView();
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        // Fill the expandable list details with non-material resources
        adapter = new MyExpandableAdapter(this, resourcesSet.getNonMaterialResourcesList(), resourcesSet.getNonMaterialChildrenResourcesList());
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);

        // Start the handler to update game data
        timerRunnable.run();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class LoadResources extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params)
        {
            // If timeDiff is greater than a second, we are returning after pausing the app
            if (timeDiff > 1000) {
                // Increment raw resources
                resourcesSet.incrementMaterialResources(timeDiff/1000);
                // Increment villager experience
                for (Iterator<Villager> it = resourcesSet.getVillagerList().iterator(); it.hasNext(); ) {
                    it.next().incrementExp(timeDiff/1000);
                }
                // Increment building progress
                for (Iterator<Building> it = resourcesSet.getBuildingList().iterator(); it.hasNext(); ) {
                    it.next().incrementProgress(timeDiff/1000);
                }

                // Increment quest progress
                for (Iterator<Quest> it = resourcesSet.getQuestList().iterator(); it.hasNext(); ) {
                    it.next().incrementProgress(timeDiff/1000);
                }

                // Once we apply the timeDiff once, we need to remove it to go back to standard increments
                timeDiff = 0;
            } else {
                // Increment raw resources
                resourcesSet.incrementMaterialResources(1f);
                // Increment villager experience
                for (Iterator<Villager> it = resourcesSet.getVillagerList().iterator(); it.hasNext(); ) {
                    it.next().incrementExp(1f);
                }
                // Increment building progress
                for (Iterator<Building> it = resourcesSet.getBuildingList().iterator(); it.hasNext(); ) {
                    it.next().incrementProgress(1f);
                }

                // Increment quest progress
                for (Iterator<Quest> it = resourcesSet.getQuestList().iterator(); it.hasNext(); ) {
                    it.next().incrementProgress(1f);
                }
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {

            // Check if we need to refresh entire expandable list, we want to minimize doing this as it interrupts user interaction
            if (updateList) {
                adapter.notifyDataSetChanged();
                updateList = false;
            } else {
                // If we are not refreshing entire list, we update all textviews with new data
                updateVillagerTextViews();
                updateBuildingTextViews();
                updateQuestTextViews();
            }

            // Always update material resources as they are not in the expandable list
            rawRName_food.setText(resourcesSet.getFood().getName() + " (" + resourcesSet.getFood().getApproxIncRate() + ")");
            rawRQuantity_food.setText(String.valueOf(resourcesSet.getFood().getQuantity()));
            rawRMaxQuantity_food.setText(String.valueOf(resourcesSet.getFood().getMaxQuantity()));

            rawRName_wood.setText(resourcesSet.getWood().getName() + " (" + resourcesSet.getWood().getApproxIncRate() + ")");
            rawRQuantity_wood.setText(String.valueOf(resourcesSet.getWood().getQuantity()));
            rawRMaxQuantity_wood.setText(String.valueOf(resourcesSet.getWood().getMaxQuantity()));

            rawRName_stone.setText(resourcesSet.getStone().getName() + " (" + resourcesSet.getStone().getApproxIncRate() + ")");
            rawRQuantity_stone.setText(String.valueOf(resourcesSet.getStone().getQuantity()));
            rawRMaxQuantity_stone.setText(String.valueOf(resourcesSet.getStone().getMaxQuantity()));

            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Stop the asynchronous calculations
        timerHandler.removeCallbacksAndMessages(timerRunnable);

        // Store all resources
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String food_json = gson.toJson(resourcesSet.getFood());
        String wood_json = gson.toJson(resourcesSet.getWood());
        String stone_json = gson.toJson(resourcesSet.getStone());
        String villagers_json = gson.toJson(resourcesSet.getVillagers());
        String buildings_json = gson.toJson(resourcesSet.getBuildings());
        String quests_json = gson.toJson(resourcesSet.getQuests());
        String items_json = gson.toJson(resourcesSet.getItems());
        editor.putString(KEY_FOOD, food_json);
        editor.putString(KEY_WOOD, wood_json);
        editor.putString(KEY_STONE, stone_json);
        editor.putString(KEY_VILLAGERS, villagers_json);
        editor.putString(KEY_BUILDINGS, buildings_json);
        editor.putString(KEY_QUESTS, quests_json);
        editor.putString(KEY_ITEMS, items_json);

        // Store the current time to calculate resources when returning
        editor.putLong(KEY_SYSTIME, System.currentTimeMillis());

        editor.commit();
    }

    public static void setUpdateList(boolean update) {
        updateList = update;
    }

    public void villagerDetails(View view) {
        villagerLogic.getVillagerDetails(view);
    }

    public void createNewVillager(View view) {
        villagerLogic.createNewVillager(view);
    }

    public void villagerAction(View view) {
        villagerLogic.villagerAction(view);
    }

    public void buildingUpgradeCancel(View view) {
        buildingLogic.buildingUpgrade(view);
    }

    public void buildingDetails(View view) {
        buildingLogic.getBuildingDetails(view);
    }

    public void buildNewBuilding(View view) {
        buildingLogic.buildNewBuilding(view);
    }

    public void questDetails(View view) {
        questLogic.getQuestDetails(view);
    }

    public void battlerSelected(View view) {
        questLogic.battlerSelected(view);
    }

    public void questLog(View view) { questLogic.questLog(view);}

    public void showVillagerStats(View view) {
        Log.i(TAG, "showVillagerStats");
    }

    public void showVillagerEquip(View view) {
        Log.i(TAG, "showVillagerStats");
    }

    public void showVillagerActions(View view) {
        Log.i(TAG, "showVillagerStats");
    }

    static public void  updateVillagerTextViews() {
        for (View view : adapter.getVillagerViews()) {

            Villager villager = (Villager) view.getTag();

            Spinner jobDDL = (Spinner) view.findViewById(R.id.rawr_setJob);

            // Check if villager is idle, set Exp and Level accordingly
            if (villager.getCurrentJob().getType().equals(VillagerJob.JobType.IDLE)) {
                TextView villagerExp = (TextView) view.findViewById(R.id.villager_exp);
                villagerExp.setText("");

                TextView villagerLevel = (TextView) view.findViewById(R.id.rawr_joblvl);
                villagerLevel.setText("--");
            } else {
                // Update villager Experience
                TextView villagerExp = (TextView) view.findViewById(R.id.villager_exp);
                villagerExp.setText(villager.getCurrentJob().getExp() + "%");

                // Update villager Level
                TextView villagerLevel = (TextView) view.findViewById(R.id.rawr_joblvl);
                villagerLevel.setText("Lv. " + String.valueOf(villager.getCurrentJob().getLevel()));

                // Fill the villager action experience text, if villager is not working say idle
                TextView villagerAction = (TextView) view.findViewById(R.id.rawr_job_action_exp);
                if (villager.isWorking()) {
                    if (villager.isHasActiveAction()) {
                        villagerAction.setText(String.valueOf(villager.getCurrentAction().getProgress())+"%");
                    } else {
                        // If villager is working, but isn't doing an action, clear the text
                        villagerAction.setText("");
                    }
                } else {
                    villagerAction.setText("Idle");
                }

                // Update the spinner as enabled if the villager is currently available

                jobDDL.setEnabled(villager.isAvailable());
                if (villager.isAvailable()) {
                    jobDDL.setAlpha(1f);
                } else {
                    jobDDL.setAlpha(.5f);
                }
            }
        }
    }

    static public void  updateBuildingTextViews() {

        for (View view : adapter.getBuildingViews()) {

            Building building = (Building) view.getTag();
            TextView buildingProgress = (TextView) view.findViewById(R.id.building_progress);

            TextView rawRName = (TextView) view.findViewById(R.id.rawr_name);
            TextView rawRBuildingLvl = (TextView) view.findViewById(R.id.rawr_buildinglvl);

//            Log.i(TAG, "building progress: " + building.getProgress());
            if (building.getProgress() != 0) {
                buildingProgress.setText(String.valueOf(building.getProgress()) + "%");
            } else {
                buildingProgress.setText("");
            }
            TextView buildingLevel = (TextView) view.findViewById(R.id.rawr_buildinglvl);
            buildingLevel.setText("Lv. " + String.valueOf(building.getLevel()));

            Button btnUpgradeCancel = (Button) view.findViewById(R.id.bld_upgrade);
            btnUpgradeCancel.setTag(building);

            if (!building.isBuilding()) {
                btnUpgradeCancel.setText("Upgrade");
            } else {
                btnUpgradeCancel.setText("Cancel");
            }

            rawRName.setText(building.getName());
            if (building.getProgress() != 0) {
                buildingProgress.setText(String.valueOf(building.getProgress()) + "%");
            }
            rawRBuildingLvl.setText("Lv. " + String.valueOf(building.getLevel()));
        }
    }

    static public void updateQuestTextViews() {

        for (View view : adapter.getQuestViews()) {

            Quest quest = (Quest) view.getTag();

            TextView questName = (TextView) view.findViewById(R.id.quest_name);
//            TextView questLevel = (TextView) view.findViewById(R.id.quest_vl);
            TextView questDescription = (TextView) view.findViewById(R.id.quest_description);
            TextView questDistanceCompletion = (TextView) view.findViewById(R.id.distance_completion);

            questName.setText(quest.getName());
//            questLevel.setText(String.valueOf(quest.getLevel()));
            questDescription.setText(quest.getDescription());
            if (quest.isQuesting() && quest.getDistance_prog() >= 0) {
                questDistanceCompletion.setText(String.valueOf(Math.round((100 * quest.getDistance_prog()) / quest.getDistance())) + "%");
            }
            else {
                questDistanceCompletion.setText("");
            }
        }
    }

    static public void updateItemTextViews() {
        for (View view : adapter.getItemViews()) {

            Item item = (Item) view.getTag();

            TextView questName = (TextView) view.findViewById(R.id.item_name);
            TextView questDescription = (TextView) view.findViewById(R.id.item_description);

            questName.setText(item.getName());
            questDescription.setText(item.getDescription());
        }
    }
}
