package com.game.test.gametest.Adapters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.game.test.gametest.Adapters.JobDropDownAdapter;
import com.game.test.gametest.Buildings.Building;
import com.game.test.gametest.Buildings.Buildings;
import com.game.test.gametest.Items.Item;
import com.game.test.gametest.Items.Items;
import com.game.test.gametest.MainActivity;
import com.game.test.gametest.Quests.Quest;
import com.game.test.gametest.Quests.Quests;
import com.game.test.gametest.R;
import com.game.test.gametest.Villagers.Villager;
import com.game.test.gametest.Villagers.Villagers;

public class MyExpandableAdapter extends BaseExpandableListAdapter implements AdapterView.OnItemSelectedListener {

    private String TAG = "/MyExpandableAdapter";
    private static final int VILLAGERS = 0;
    private static final int BUILDINGS = 1;
    private static final int QUESTS = 2;
    private static final int ITEMS = 3;

    private Activity activity;
    private List<Object> childitems;
    private LayoutInflater inflater;
    private List<Object> parentItems;
    private ArrayList<Object> child;
    private Context context;

    private static Set<View> villagerViews = new HashSet<>();
    private static Set<View> buildingViews = new HashSet<>();
    private static Set<View> questViews = new HashSet<>();
    private static Set<View> itemViews = new HashSet<>();

    private Spinner jobDDL;

    public MyExpandableAdapter(Context context, List<Object> parents, List<Object> children) {
        this.context = context;
        this.parentItems = parents;
        this.childitems = children;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup parent) {

        child = (ArrayList<Object>) childitems.get(groupPosition);

        if (groupPosition == VILLAGERS) {
//            Log.i(TAG, "groupPos: " + groupPosition);
//            Log.i(TAG, "childPosition: " + childPosition);

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.village_snippet_list_row, parent, false);

            Villager villager = (Villager) child.get(childPosition);
            view.setTag(villager);

            LinearLayout villagerRow = (LinearLayout) view.findViewById(R.id.villager_row);
            LinearLayout villagerAdd = (LinearLayout) view.findViewById(R.id.add_villager);

            // Check if we have a blank villager
            if (villager.getName().contains("nobody")) {
                villagerRow.setVisibility(LinearLayout.GONE);
                villagerAdd.setVisibility(LinearLayout.VISIBLE);
                villagerAdd.setTag(villager);
            } else {

                villagerAdd.setVisibility(LinearLayout.GONE);
                villagerRow.setVisibility(LinearLayout.VISIBLE);

                villagerViews.add(view);

                TextView rawRName = (TextView) view.findViewById(R.id.rawr_name);
                rawRName.setText(villager.getName());

                MainActivity.updateVillagerTextViews();
            }

            // Fill the job drop down
            jobDDL = (Spinner) view.findViewById(R.id.rawr_setJob);
            JobDropDownAdapter dataAdapter = new JobDropDownAdapter(this.context, android.R.layout.simple_spinner_item, villager, villager.getAvailableJobs());
            dataAdapter.setDropDownViewResource(R.layout.villagerjobdll);
            jobDDL.setAdapter(dataAdapter);
            jobDDL.setOnItemSelectedListener(dataAdapter);

            // Setup villager details for selecting
            LinearLayout villagerDetails = (LinearLayout) view.findViewById(R.id.name_layout);
            villagerDetails.setTag(villager);
            jobDDL.setTag(villager);
            jobDDL.setSelection(Villager.getJobDropDownPosition(villager));

        } else if (groupPosition == BUILDINGS) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.building_snippet_list_row, parent, false);

            LinearLayout buildingRow = (LinearLayout) view.findViewById(R.id.building_row);
            LinearLayout buildingAdd = (LinearLayout) view.findViewById(R.id.add_building);
            LinearLayout buildingDetails = (LinearLayout) view.findViewById(R.id.name_layout);

            Building building = (Building) child.get(childPosition);
            buildingDetails.setTag(building);
            view.setTag(building);
            buildingViews.add(view);

            // If building is unbuilt, display new building row
            if (building.getName().contains("unbuilt")) {
                buildingRow.setVisibility(LinearLayout.GONE);
                buildingAdd.setVisibility(LinearLayout.VISIBLE);
                buildingAdd.setTag(building);
            } else {
                // If building is built, display building details
                buildingRow.setVisibility(LinearLayout.VISIBLE);
                buildingAdd.setVisibility(LinearLayout.GONE);
                buildingRow.setTag(building);

                MainActivity.updateBuildingTextViews();
            }

        } else if (groupPosition == QUESTS) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.quest_snippet_list_row, parent, false);
            LinearLayout questNameLayout = (LinearLayout) view.findViewById(R.id.quest_name_layout);

            Quest quest = (Quest) child.get(childPosition);
            view.setTag(quest);
            questNameLayout.setTag(quest);

            questViews.add(view);
            MainActivity.updateQuestTextViews();

        } else if (groupPosition == ITEMS) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_snippet_list_row, parent, false);

            Item item = (Item) child.get(childPosition);
            view.setTag(item);

            itemViews.add(view);
            MainActivity.updateItemTextViews();
        }
        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Log.i(TAG, "ItemSelected");
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }

        switch (groupPosition) {
            case (VILLAGERS):
                ((CheckedTextView) convertView).setText("Villagers " + String.valueOf(((Villagers) parentItems.get(VILLAGERS)).getQuantity()) + "/" + String.valueOf(((Villagers) parentItems.get(0)).getMaxQuantity()));
                break;
            case BUILDINGS:
                ((CheckedTextView) convertView).setText("Buildings " + String.valueOf(((Buildings) parentItems.get(BUILDINGS)).getQuantity()) + "/" + String.valueOf(((Buildings) parentItems.get(1)).getMaxQuantity()));
                break;
            case QUESTS:
                ((CheckedTextView) convertView).setText("Quests (" + String.valueOf(((Quests) parentItems.get(QUESTS)).getQuantity()) + ")");
                break;
            case ITEMS:
                ((CheckedTextView) convertView).setText("Items (" + String.valueOf(((Items) parentItems.get(ITEMS)).getQuantity()) + ")");
                break;
        }

        ((CheckedTextView) convertView).setChecked(isExpanded);

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        Log.i(TAG, "groupPosition: " + groupPosition);
//        Log.i(TAG, "childitems: " + childitems.size());
        return ((ArrayList<String>) childitems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public Set<View> getVillagerViews() {
        return villagerViews;
    }

    public Set<View> getBuildingViews() {
        return buildingViews;
    }

    public Set<View> getQuestViews() {
        return questViews;
    }

    public Set<View> getItemViews() { return itemViews; }
}
