package com.game.test.gametest.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.test.gametest.Buildings.Building;
import com.game.test.gametest.Buildings.Upgrade;
import com.game.test.gametest.R;

import java.util.List;

public class UpgradesAdapter  extends ArrayAdapter<Upgrade> {
    private String TAG = "/UpgradesAdapter";
    private final Context context;
    private final List<Upgrade> upgrades;
    private final Building building;

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    public UpgradesAdapter(Activity context, List<Upgrade> upgrades, Building building) {
        super(context, R.layout.village_details, upgrades);
        this.context = context;
        this.upgrades = upgrades;
        this.building = building;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(R.layout.upgrade_row, parent, false);
            // configure view holder
        }

        // Check if the upgrade details are below the building's next level
        if (upgrades.get(position).getLevel() <= (building.getLevel() + 1)) {
            // If so, then show the details
            TextView upgradeLevel = (TextView) rowView.findViewById(R.id.upgrade_level);
            upgradeLevel.setTextColor(Color.WHITE);
            upgradeLevel.setText(String.valueOf(upgrades.get(position).getLevel()));

            TextView upgradeName = (TextView) rowView.findViewById(R.id.upgrade_name);
            upgradeName.setTextColor(Color.WHITE);
            upgradeName.setText(upgrades.get(position).getName());
        } else {
            // If not, then show generic data
            TextView upgradeLevel = (TextView) rowView.findViewById(R.id.upgrade_level);
            upgradeLevel.setText(String.valueOf(upgrades.get(position).getLevel()));
            upgradeLevel.setTextColor(Color.GRAY);

            TextView upgradeName = (TextView) rowView.findViewById(R.id.upgrade_name);
            upgradeName.setText("<unknown>");
            upgradeName.setTextColor(Color.GRAY);
        }

        return rowView;
    }
}
