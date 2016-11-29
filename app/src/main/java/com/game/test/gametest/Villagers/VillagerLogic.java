package com.game.test.gametest.Villagers;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.game.test.gametest.Adapters.EquipItemAdapter;
import com.game.test.gametest.MainActivity;
import com.game.test.gametest.R;


public class VillagerLogic {

    private static String TAG = "/VillagerLogic";

    private static Activity activity;

    public VillagerLogic(Activity activity) {
        this.activity = activity;
    }

    public static void getVillagerDetails(View view) {
        Log.i(TAG, "villagerDetails: " + ((Villager) view.getTag()).getName());

        final Villager villager = (Villager) view.getTag();
        // custom dialog
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.village_details);
        dialog.setTitle(villager.getName() + " details");

        final ListView charStatList = (ListView) dialog.findViewById(R.id.stat_list);
        VillagerCharacterDetailsAdapter charAdapter = new VillagerCharacterDetailsAdapter(activity, villager.getCharStatNames(), villager.getCharStats(), villager.getItemCharacterStats());
        charStatList.setAdapter(charAdapter);

        final ListView battleStatList = (ListView) dialog.findViewById(R.id.battle_stat_list);
        VillagerBattleDetailsAdapter battleAdapter = new VillagerBattleDetailsAdapter(activity, villager.getBattleStatNames(), villager.getBattleStats(), villager.getItemBattleStats());
        battleStatList.setAdapter(battleAdapter);

        final LinearLayout statLayout = (LinearLayout) dialog.findViewById(R.id.stat_layout);
        final LinearLayout equipLayout = (LinearLayout) dialog.findViewById(R.id.equip_layout);
        final LinearLayout actionLayout = (LinearLayout) dialog.findViewById(R.id.action_layout);

        ListView actionList = (ListView) dialog.findViewById(R.id.action_list);
        VillagerActionsAdapter villagerActionsAdapter = new VillagerActionsAdapter(activity, villager.getCurrentJob().getActions());
        actionList.setAdapter(villagerActionsAdapter);

        TextView showVillagerStats = (TextView) dialog.findViewById(R.id.showVillagerStats);
        TextView showVillagerEquip = (TextView) dialog.findViewById(R.id.showVillagerEquip);
        TextView showVillagerActions = (TextView) dialog.findViewById(R.id.showVillagerActions);

        // Change layout depending on button clicks
        showVillagerStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update stats
                VillagerCharacterDetailsAdapter charAdapter = new VillagerCharacterDetailsAdapter(activity, villager.getCharStatNames(), villager.getCharStats(), villager.getItemCharacterStats());
                charStatList.setAdapter(charAdapter);

                VillagerBattleDetailsAdapter battleAdapter = new VillagerBattleDetailsAdapter(activity, villager.getBattleStatNames(), villager.getBattleStats(), villager.getItemBattleStats());
                battleStatList.setAdapter(battleAdapter);

                statLayout.setVisibility(View.VISIBLE);
                equipLayout.setVisibility(View.GONE);
                actionLayout.setVisibility(View.GONE);
            }
        });
        showVillagerEquip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statLayout.setVisibility(View.GONE);
                equipLayout.setVisibility(View.VISIBLE);
                actionLayout.setVisibility(View.GONE);
            }
        });
        showVillagerActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statLayout.setVisibility(View.GONE);
                equipLayout.setVisibility(View.GONE);
                actionLayout.setVisibility(View.VISIBLE);
            }
        });

        fillEquipSpinners(villager, dialog);
        dialog.show();
    }

    public static void createNewVillager(View view) {

        final Villager villager = (Villager) view.getTag();
        Log.i(TAG, "createNewVillager: " + villager.getName());

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.create_villager);
        dialog.setTitle("Create extra villagers");

//        ListView listview = (ListView) dialog.findViewById(R.id.newvillager_requirements_list);
//        RequirementsAdapter adapter = new RequirementsAdapter(activity, villager.getRequirements());
//        listview.setAdapter(adapter);

        Button confirmButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        confirmButton.setEnabled(villager.requirementsMet());
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.resourcesSet.getVillagers().createVillager(villager);
                dialog.dismiss();
            }
        });
        Button cancelButton = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void villagerAction(View view) {
//        JobAction villagerAction = (JobAction) view.getTag();
//        Log.i(TAG, "villager: " + villagerAction.getVillager().getName() + " start performing action " + villagerAction.getName());
//        villagerAction.startAction();
    }

    private static void fillEquipSpinners(Villager villager, Dialog dialog) {
        Spinner mainHandSpinner = (Spinner) dialog.findViewById(R.id.equip_right_hand);
        EquipItemAdapter equipMainHandAdapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getMainHandList().getUnequippedWithBlank(villager.getEquipmentSet().getMainHand()));
        equipMainHandAdapter.setDropDownViewResource(R.layout.villagerjobdll);
        mainHandSpinner.setAdapter(equipMainHandAdapter);
        mainHandSpinner.setOnItemSelectedListener(equipMainHandAdapter);

        Spinner offHandSpinner = (Spinner) dialog.findViewById(R.id.equip_left_hand);
        EquipItemAdapter equipOffHandAdapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getOffHandList().getUnequippedWithBlank(villager.getEquipmentSet().getOffHand()));
        equipOffHandAdapter.setDropDownViewResource(R.layout.villagerjobdll);
        offHandSpinner.setAdapter(equipOffHandAdapter);
        offHandSpinner.setOnItemSelectedListener(equipOffHandAdapter);

        Spinner headSpinner = (Spinner) dialog.findViewById(R.id.equip_head);
        EquipItemAdapter equipHeadAdapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getHeadList().getUnequippedWithBlank(villager.getEquipmentSet().getHead()));
        equipHeadAdapter.setDropDownViewResource(R.layout.villagerjobdll);
        headSpinner.setAdapter(equipHeadAdapter);
        headSpinner.setOnItemSelectedListener(equipHeadAdapter);

        Spinner neckSpinner = (Spinner) dialog.findViewById(R.id.equip_neck);
        EquipItemAdapter equipNeckAdapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getNeckList().getUnequippedWithBlank(villager.getEquipmentSet().getNeck()));
        equipNeckAdapter.setDropDownViewResource(R.layout.villagerjobdll);
        neckSpinner.setAdapter(equipNeckAdapter);
        neckSpinner.setOnItemSelectedListener(equipNeckAdapter);

        Spinner shoulderSpinner = (Spinner) dialog.findViewById(R.id.equip_shoulders);
        EquipItemAdapter equipShouldersAdapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getShoulderList().getUnequippedWithBlank(villager.getEquipmentSet().getShoulder()));
        equipShouldersAdapter.setDropDownViewResource(R.layout.villagerjobdll);
        shoulderSpinner.setAdapter(equipShouldersAdapter);
        shoulderSpinner.setOnItemSelectedListener(equipShouldersAdapter);

        Spinner backSpinner = (Spinner) dialog.findViewById(R.id.equip_back);
        EquipItemAdapter equipBackAdapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getBackList().getUnequippedWithBlank(villager.getEquipmentSet().getBack()));
        equipBackAdapter.setDropDownViewResource(R.layout.villagerjobdll);
        backSpinner.setAdapter(equipBackAdapter);
        backSpinner.setOnItemSelectedListener(equipBackAdapter);

        Spinner chestSpinner = (Spinner) dialog.findViewById(R.id.equip_chest);
        EquipItemAdapter equipChestAdapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getChestList().getUnequippedWithBlank(villager.getEquipmentSet().getChest()));
        equipChestAdapter.setDropDownViewResource(R.layout.villagerjobdll);
        chestSpinner.setAdapter(equipChestAdapter);
        chestSpinner.setOnItemSelectedListener(equipChestAdapter);

        Spinner wristSpinner = (Spinner) dialog.findViewById(R.id.equip_wrist);
        EquipItemAdapter equipWristAdapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getWristList().getUnequippedWithBlank(villager.getEquipmentSet().getWrist()));
        equipWristAdapter.setDropDownViewResource(R.layout.villagerjobdll);
        wristSpinner.setAdapter(equipWristAdapter);
        wristSpinner.setOnItemSelectedListener(equipWristAdapter);

        Spinner gloveSpinner = (Spinner) dialog.findViewById(R.id.equip_glove);
        EquipItemAdapter equipGloveAdapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getGloveList().getUnequippedWithBlank(villager.getEquipmentSet().getGlove()));
        equipGloveAdapter.setDropDownViewResource(R.layout.villagerjobdll);
        gloveSpinner.setAdapter(equipGloveAdapter);
        gloveSpinner.setOnItemSelectedListener(equipGloveAdapter);

        Spinner waistSpinner = (Spinner) dialog.findViewById(R.id.equip_waist);
        EquipItemAdapter equipWaistAdapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getWaistList().getUnequippedWithBlank(villager.getEquipmentSet().getWaist()));
        equipWaistAdapter.setDropDownViewResource(R.layout.villagerjobdll);
        waistSpinner.setAdapter(equipWaistAdapter);
        waistSpinner.setOnItemSelectedListener(equipWaistAdapter);

        Spinner legSpinner = (Spinner) dialog.findViewById(R.id.equip_legging);
        EquipItemAdapter equipLeggingAdapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getLegList().getUnequippedWithBlank(villager.getEquipmentSet().getLeg()));
        equipLeggingAdapter.setDropDownViewResource(R.layout.villagerjobdll);
        legSpinner.setAdapter(equipLeggingAdapter);
        legSpinner.setOnItemSelectedListener(equipLeggingAdapter);

        Spinner feetSpinner = (Spinner) dialog.findViewById(R.id.equip_feet);
        EquipItemAdapter equipFeetAdapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getFeetList().getUnequippedWithBlank(villager.getEquipmentSet().getFeet()));
        equipFeetAdapter.setDropDownViewResource(R.layout.villagerjobdll);
        feetSpinner.setAdapter(equipFeetAdapter);
        feetSpinner.setOnItemSelectedListener(equipFeetAdapter);

        Spinner finger1Spinner = (Spinner) dialog.findViewById(R.id.equip_finger1);
        EquipItemAdapter equipFinger1Adapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getFingerList().getUnequippedWithBlank(villager.getEquipmentSet().getFinger1()));
        equipFinger1Adapter.setDropDownViewResource(R.layout.villagerjobdll);
        finger1Spinner.setAdapter(equipFinger1Adapter);
        finger1Spinner.setOnItemSelectedListener(equipFinger1Adapter);

        Spinner finger2Spinner = (Spinner) dialog.findViewById(R.id.equip_finger2);
        EquipItemAdapter equipFinger2Adapter = new EquipItemAdapter(activity, villager, MainActivity.resourcesSet.getItems().getFingerList().getUnequippedWithBlank(villager.getEquipmentSet().getFinger2()));
        equipFinger2Adapter.setDropDownViewResource(R.layout.villagerjobdll);
        finger2Spinner.setAdapter(equipFinger2Adapter);
        finger2Spinner.setOnItemSelectedListener(equipFinger2Adapter);
    }
}
