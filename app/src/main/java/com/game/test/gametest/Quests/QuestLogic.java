package com.game.test.gametest.Quests;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.game.test.gametest.Adapters.LogLineAdapter;
import com.game.test.gametest.MainActivity;
import com.game.test.gametest.Adapters.QuestBattleListAdapter;
import com.game.test.gametest.R;
import com.game.test.gametest.Villagers.BattleRank;
import com.game.test.gametest.Villagers.Battler;
import com.game.test.gametest.Villagers.Villager;

import java.util.ArrayList;
import java.util.List;

public class QuestLogic {

    private static String TAG = "/QuestLogic";

    private static Activity activity;
    private static List<Battler> selectedBattlers;
    private static Button embarkQuest;

    public QuestLogic(Activity activity) {
        this.activity = activity;
        selectedBattlers = new ArrayList<>();
    }

    public void getQuestDetails(View view) {
        final Quest quest = (Quest) view.getTag();
        Log.i(TAG, "questDetails: " + quest.getName());

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.quest_details);
        dialog.setTitle(quest.getName());
        TextView questDetails = (TextView) dialog.findViewById(R.id.quest_details);
        TextView questDistance = (TextView) dialog.findViewById(R.id.quest_distance);
        TextView questLog = (TextView) dialog.findViewById(R.id.quest_battleLog);
        questDetails.setText(quest.getDescription());
        questDistance.setText("Distance: " + String.valueOf(quest.getDistance()));

        if (!quest.getQuestLog().isEmpty()) {
            questLog.setVisibility(View.VISIBLE);
            questLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Quest log on click");
                    final Dialog questLog = new Dialog(activity);
                    questLog.setContentView(R.layout.quest_log);

                    // Print the battle log lis
                    final ListView questLogList = (ListView) questLog.findViewById(R.id.quest_log);
                    LogLineAdapter questLogAdapter = new LogLineAdapter(activity, quest.getQuestLog());
                    questLogList.setAdapter(questLogAdapter);
                    questLog.show();
                }
            });
        } else {
            questLog.setVisibility(View.GONE);
        }

        // Populate the available villagers with battle jobs for questing
        final ListView battleList = (ListView) dialog.findViewById(R.id.battle_list);
        QuestBattleListAdapter questBattleList = new QuestBattleListAdapter(activity, MainActivity.resourcesSet.getVillagers().getBattlers());
        battleList.setAdapter(questBattleList);

        // Perform quest embarking
        embarkQuest = (Button) dialog.findViewById(R.id.embarkQuest);
        // Check if we are able to embark
        ableToEmbark();
        embarkQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog confirm = new Dialog(activity);
                confirm.setContentView(R.layout.building_upgrade);
                // Check if user really wants to cancel
                LinearLayout buildingUpgrade = (LinearLayout) confirm.findViewById(R.id.building_upgrade);
                LinearLayout buildingNoBuilders = (LinearLayout) confirm.findViewById(R.id.no_builders);

                buildingUpgrade.setVisibility(LinearLayout.GONE);
                buildingNoBuilders.setVisibility(LinearLayout.VISIBLE);

                confirm.setTitle("Embark on quest?");

                Button confirmButton = (Button) confirm.findViewById(R.id.dialogButtonNoBuildersOk);
                Button cancelButton = (Button) confirm.findViewById(R.id.dialogButtonNoBuildersCancel);
                cancelButton.setVisibility(View.VISIBLE);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Destroy building
                        Log.i(TAG, "No. of selected battlers: " + selectedBattlers.size());
                        // ToDo embark on quest
                        for (Battler battler : selectedBattlers) {
                            ((Villager) battler).setIsAvailable(false);
                        }
                        // ToDo add interface to allow user to put in front OR back row
                        BattleRank allyRanks = new BattleRank(selectedBattlers, new ArrayList<Battler>());
                        quest.startQuesting(allyRanks);
                        confirm.dismiss();
                        dialog.dismiss();
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirm.dismiss();
                    }
                });
                confirm.show();
            }
        });

        // Cancel Quest dialog
        Button dialogButton = (Button) dialog.findViewById(R.id.cancelQuest);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedBattlers.clear();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void battlerSelected(View view) {

        Villager battler = (Villager) view.getTag();
        Log.i(TAG, "Selected battler: " + battler.getName());

        if (selectedBattlers.contains(battler)) {
            selectedBattlers.remove(battler);
        } else {
            selectedBattlers.add(battler);
        }

        ableToEmbark();
    }

    // Checks if the quest is able to embark or not
    private static void ableToEmbark() {
        if (selectedBattlers.size() < 1) {
            embarkQuest.setEnabled(false);
        } else {
            embarkQuest.setEnabled(true);
        }
    }

    public void questLog(View view) {
        final Quest quest = (Quest) view.getTag();

        final Dialog questLog = new Dialog(activity);
        questLog.setContentView(R.layout.quest_log);

        // Print the battle log lis
        final ListView questLogList = (ListView) questLog.findViewById(R.id.quest_log);
        LogLineAdapter questLogAdapter = new LogLineAdapter(activity, quest.getQuestLog());
        questLogList.setAdapter(questLogAdapter);

    }
}
