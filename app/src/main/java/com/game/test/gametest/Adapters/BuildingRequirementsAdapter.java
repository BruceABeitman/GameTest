package com.game.test.gametest.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.test.gametest.R;
import com.game.test.gametest.Villagers.Villager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by bbeitman on 9/23/15.
 */
public class BuildingRequirementsAdapter extends ArrayAdapter<String> {

    private String TAG = "/VillagerCharacterDetailsAdapter";
    private final Context context;
    private final List<String> requirementNames;
    private final List<Integer> requirementVals;

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    public BuildingRequirementsAdapter(Activity context, List<String> requirementNames, List<Integer> requirementVals) {
        super(context, R.layout.village_details, requirementNames);
        this.context = context;
        this.requirementNames = requirementNames;
        this.requirementVals = requirementVals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(R.layout.building_requirements, parent, false);
            // configure view holder
        }

        if (requirementNames.get(position).equals("Time")) {

            TextView statVal = (TextView) rowView.findViewById(R.id.stat_value);
            TextView statName = (TextView) rowView.findViewById(R.id.stat_name);

            printTime(requirementVals.get(position), statVal, statName);

        } else {
            TextView statVal = (TextView) rowView.findViewById(R.id.stat_value);
            statVal.setText(String.valueOf(requirementVals.get(position)));

            TextView statName = (TextView) rowView.findViewById(R.id.stat_name);
            statName.setText(requirementNames.get(position));
        }

        return rowView;
    }


    public void printTime(int secondsCount, TextView statVal, TextView statName){
        //Calculate the seconds to display:
        int seconds = secondsCount %60;
        secondsCount -= seconds;
        //Calculate the minutes:
        long minutesCount = secondsCount / 60;
        long minutes = minutesCount % 60;
        minutesCount -= minutes;
        //Calculate the hours:
        long hoursCount = minutesCount / 60;
        //Build the String

//        if (minutes == 0 && hoursCount == 0) {
//            statVal.setText(String.valueOf(seconds));
//            statName.setText("sec");
//        } else
        if (hoursCount == 0) {
            statVal.setText(minutes + ":" + seconds);
            statName.setText("min");
        } else {
            statVal.setText("" + hoursCount + ":" + minutes + ":" + seconds);
            statName.setText("hours");
        }
    }
}
