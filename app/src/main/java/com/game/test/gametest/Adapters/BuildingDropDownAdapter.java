package com.game.test.gametest.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.game.test.gametest.Buildings.Building;
import com.game.test.gametest.R;
import com.game.test.gametest.Villagers.Villager;
import com.game.test.gametest.Villagers.VillagerJob;

import java.util.List;

/**
 * Created by bbeitman on 9/23/15.
 */
public class BuildingDropDownAdapter extends ArrayAdapter<Building> implements AdapterView.OnItemSelectedListener {

    static private String TAG = "/BuildingDropDownAdapter";
    private Context context;
    private List<Building> buildings;
    private Dialog dialog;

    public BuildingDropDownAdapter(Context context, int textViewResourceId, List<Building> buildings, Dialog dialog) {
        super(context, textViewResourceId, buildings);
        this.context = context;
        this.buildings = buildings;
        this.dialog = dialog;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);

        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.job_snippet_list_row, parent, false);
        }

        TextView rawRName = (TextView) view.findViewById(R.id.rawr_name);
        rawRName.setText(buildings.get(position).getName());
        view.setTag(buildings.get(position));

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Log.i(TAG, "ItemSelected");

//        ListView listview = (ListView) dialog.findViewById(R.id.newbuilding_requirements_list);
//        RequirementsAdapter adapter = new RequirementsAdapter(context , buildings.get(pos).getRequirements());
//        listview.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
