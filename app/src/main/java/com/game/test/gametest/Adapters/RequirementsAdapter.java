package com.game.test.gametest.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.test.gametest.Buildings.Requirement;
import com.game.test.gametest.R;

import java.util.List;

public class RequirementsAdapter extends ArrayAdapter<Requirement> {

    private String TAG = "/RequirementsAdapter";
    private final Context context;
    private final List<Requirement> requirements;

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    public RequirementsAdapter(Activity context, List<Requirement> requirements) {
        super(context, R.layout.village_details, requirements);
        this.context = context;
        this.requirements = requirements;
    }

    public RequirementsAdapter(Context context, List<Requirement> requirements) {
        super(context, R.layout.village_details, requirements);
        this.context = context;
        this.requirements = requirements;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(R.layout.villager_detail_row, parent, false);
            // configure view holder
        }

        TextView statName = (TextView) rowView.findViewById(R.id.stat_name);
        statName.setText(requirements.get(position).getName());

        TextView statVal = (TextView) rowView.findViewById(R.id.stat_value);
        if (requirements.get(position).getValue() != 0) {
            statVal.setText(String.valueOf(requirements.get(position).getValue()));
        }

        return rowView;
    }
}
