package com.game.test.gametest.Buildings;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.game.test.gametest.Adapters.BuilderDropDownAdapter;
import com.game.test.gametest.Adapters.BuildingDropDownAdapter;
import com.game.test.gametest.MainActivity;
import com.game.test.gametest.R;
import com.game.test.gametest.Adapters.RequirementsAdapter;
import com.game.test.gametest.Adapters.UpgradesAdapter;
import com.game.test.gametest.Villagers.Villager;

public class BuildingLogic extends Activity {

    private static String TAG = "BuildingLogic";

    private static Activity activity;
    private static Spinner builderDDL;
    private static Spinner buildingDDL;

    public BuildingLogic(Activity activity) {
        this.activity = activity;
    }

    public void buildNewBuilding(View view) {
        final Building building = (Building) view.getTag();
        Log.i(TAG, "buildNewBuilding: " + building.getName());

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.create_building);

        LinearLayout buildingCreate = (LinearLayout) dialog.findViewById(R.id.builder_available);
        LinearLayout buildingNoBuilders = (LinearLayout) dialog.findViewById(R.id.no_builder_available);

        if (!MainActivity.resourcesSet.getVillagers().haveIdleBuilder()) {

            // If user has NO available builders, then show warning
            buildingCreate.setVisibility(LinearLayout.GONE);
            buildingNoBuilders.setVisibility(LinearLayout.VISIBLE);

            dialog.setTitle("No available builders");
            Button confirmButton = (Button) dialog.findViewById(R.id.dialogButtonNoBuilders);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } else {

            // If user has available builders, then show additional requirements for building
            buildingCreate.setVisibility(LinearLayout.VISIBLE);
            buildingNoBuilders.setVisibility(LinearLayout.GONE);

            dialog.setTitle("Build new building");

            // Fill the ddl for selecting the type of building
            buildingDDL = (Spinner) dialog.findViewById(R.id.choose_building);
            BuildingDropDownAdapter dataAdapter = new BuildingDropDownAdapter(activity, android.R.layout.simple_spinner_item, MainActivity.resourcesSet.getBuildings().getAvailableBuildings(), dialog);
            dataAdapter.setDropDownViewResource(R.layout.villagerjobdll);
            Log.i(TAG, "available buildings: " + MainActivity.resourcesSet.getBuildings().getAvailableBuildings());
            buildingDDL.setAdapter(dataAdapter);

            // Set listener to repopulate the requirements list when user selects building
            buildingDDL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long i)
                {
                    Building newSelectedBuilding = MainActivity.resourcesSet.getBuildings().getAvailableBuildings().get(position);
                    ListView listview = (ListView) dialog.findViewById(R.id.newbuilding_requirements_list);
                    RequirementsAdapter adapter = new RequirementsAdapter(activity, newSelectedBuilding.getRequirements());
                    listview.setAdapter(adapter);

                    Button confirmButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    confirmButton.setEnabled(newSelectedBuilding.requirementsMet());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });

            // Fill the requirements list with the requirements for the selected building
            Building selectedBuilding = (Building) buildingDDL.getSelectedItem();
            ListView listview = (ListView) dialog.findViewById(R.id.newbuilding_requirements_list);
            RequirementsAdapter adapter = new RequirementsAdapter(activity, selectedBuilding.getRequirements());
            listview.setAdapter(adapter);

            // Fill the builder ddl with available builders
            builderDDL = (Spinner) dialog.findViewById(R.id.rawr_setBuilder);
            BuilderDropDownAdapter builderDropDownAdapter = new BuilderDropDownAdapter(activity, android.R.layout.simple_spinner_item, MainActivity.resourcesSet.getVillagers().getIdleBuilders());
            dataAdapter.setDropDownViewResource(R.layout.villagerjobdll);
            builderDDL.setAdapter(builderDropDownAdapter);

            Button confirmButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            confirmButton.setEnabled(selectedBuilding.requirementsMet());
            confirmButton.setTag(building);
            Log.i(TAG, "Building pos: " + MainActivity.resourcesSet.getBuildingList().indexOf(building));
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    MainActivity.resourcesSet.getBuildings().createBuilding();
                    // Get the selected building type, NOTE this is from available buildings, not what we want to update
                    Building selectedBuildingType = (Building) buildingDDL.getSelectedItem();
                    Villager builder = (Villager) builderDDL.getSelectedItem();

                    building.createBuilding(selectedBuildingType.getBuildingType());
                    building.consumeRequirements();
                    builder.setIsWorking(true);
                    builder.setAvailable(false);
                    building.setIsBuilding(true);
                    building.setBuilder(builder);

//                    building.setTimeRequirement(building.getRequirementVals().get((building.getRequirementNames().size() - 1)));
//                    btnUpgradeCancel.setText("Cancel");
//                    MainActivity.resourcesSet.getBuildingList().get(1).createBuilding(selectedBuildingType.getBuildingType());
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
        }

        dialog.show();
    }

    public static void getBuildingDetails(View view) {

        Building building = (Building) view.getTag();
        // custom dialog
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.building_details);
        dialog.setTitle(building.getName() + " details");

        ListView actionList = (ListView) dialog.findViewById(R.id.upgrade_list);
        UpgradesAdapter upgradesAdapter = new UpgradesAdapter(activity, building.getUpgrades(), building);
        actionList.setAdapter(upgradesAdapter);

        Button destroyBuilding = (Button) dialog.findViewById(R.id.destroyBuilding);
        destroyBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog confirm = new Dialog(activity);
                confirm.setContentView(R.layout.building_upgrade);
                // Check if user really wants to cancel
                LinearLayout buildingUpgrade = (LinearLayout) confirm.findViewById(R.id.building_upgrade);
                LinearLayout buildingNoBuilders = (LinearLayout) confirm.findViewById(R.id.no_builders);

                buildingUpgrade.setVisibility(LinearLayout.GONE);
                buildingNoBuilders.setVisibility(LinearLayout.VISIBLE);

                confirm.setTitle("Destroy building?");

                Button confirmButton = (Button) confirm.findViewById(R.id.dialogButtonNoBuildersOk);
                Button cancelButton = (Button) confirm.findViewById(R.id.dialogButtonNoBuildersCancel);
                cancelButton.setVisibility(View.VISIBLE);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Destroy building
                        // ToDo destroy building
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

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    
    public static void buildingUpgrade(View view) {
        Log.i(TAG, "buildingUpgradeCancel: " + ((Building) view.getTag()).getName());

        final Building building = (Building) view.getTag();
        // custom dialog
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.building_upgrade);

        LinearLayout buildingUpgrade = (LinearLayout) dialog.findViewById(R.id.building_upgrade);
        LinearLayout buildingNoBuilders = (LinearLayout) dialog.findViewById(R.id.no_builders);

        final Button btnUpgradeCancel = (Button) view.findViewById(R.id.bld_upgrade);

        // Check if this is an upgrade or a cancel
        if (btnUpgradeCancel.getText().equals("Upgrade")) {

            // Start dialogue to allow user to upgrade building if possible
            if (MainActivity.resourcesSet.getVillagers().haveIdleBuilder()) {

                // If user has available builders, then show additional requirements for building
                buildingUpgrade.setVisibility(LinearLayout.VISIBLE);
                buildingNoBuilders.setVisibility(LinearLayout.GONE);

                dialog.setTitle("Upgrade " + building.getName() + " to level " + String.valueOf((building.getLevel() + 1)) + "?");

                ListView listview = (ListView) dialog.findViewById(R.id.building_requirements_list);
                RequirementsAdapter adapter = new RequirementsAdapter(activity, building.getRequirements());
                listview.setAdapter(adapter);

                builderDDL = (Spinner) dialog.findViewById(R.id.rawr_setBuilder);
                BuilderDropDownAdapter dataAdapter = new BuilderDropDownAdapter(activity, android.R.layout.simple_spinner_item, MainActivity.resourcesSet.getVillagers().getIdleBuilders());
                dataAdapter.setDropDownViewResource(R.layout.villagerjobdll);
                builderDDL.setAdapter(dataAdapter);

                final Button okButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

                // Check if all requirements are met
                okButton.setEnabled(building.requirementsMet());

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Perform upgrade
                        final Villager builder = MainActivity.resourcesSet.getVillagers().getIdleBuilders().get(builderDDL.getSelectedItemPosition());
//                  Log.i(TAG, "Setting to build " +  builder.getName());

                        building.consumeRequirements();
                        builder.setIsWorking(true);
                        builder.setAvailable(false);
                        building.setIsBuilding(true);
                        building.setBuilder(builder);
//                    building.setTimeRequirement(building.getRequirementVals().get((building.getRequirementNames().size() - 1)));
                        btnUpgradeCancel.setText("Cancel");
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
            } else {

                // If user doesn't have available builders, then inform user of no builders
                buildingUpgrade.setVisibility(LinearLayout.GONE);
                buildingNoBuilders.setVisibility(LinearLayout.VISIBLE);

                dialog.setTitle("No available builders");
                Button okButton = (Button) dialog.findViewById(R.id.dialogButtonNoBuildersOk);
                Button cancelButton = (Button) dialog.findViewById(R.id.dialogButtonNoBuildersCancel);
                cancelButton.setVisibility(View.GONE);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            dialog.show();
        } else {

            // Check if user really wants to cancel
            buildingUpgrade.setVisibility(LinearLayout.GONE);
            buildingNoBuilders.setVisibility(LinearLayout.VISIBLE);

            dialog.setTitle("Cancel upgrade?");

            Button confirmButton = (Button) dialog.findViewById(R.id.dialogButtonNoBuildersOk);
            Button cancelButton = (Button) dialog.findViewById(R.id.dialogButtonNoBuildersCancel);
            cancelButton.setVisibility(View.VISIBLE);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Cancel building upgrade
                    Villager builder = building.getBuilder();
                    builder.setIsWorking(false);
                    builder.setAvailable(true);
                    building.setIsBuilding(false);
                    building.setBuilder(null);
                    building.setProgress(0f);
                    btnUpgradeCancel.setText("Upgrade");
                    dialog.dismiss();
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
