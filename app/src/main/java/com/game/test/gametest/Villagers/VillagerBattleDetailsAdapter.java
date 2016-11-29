package com.game.test.gametest.Villagers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.test.gametest.R;
import com.game.test.gametest.Villagers.Villager;

import java.util.HashMap;
import java.util.List;

public class VillagerBattleDetailsAdapter extends ArrayAdapter<Float> {

    private String TAG = "/VillagerCharacterDetailsAdapter";
    private final Context context;
    private final List<Float> charStats;
    private final List<String> statNames;
    private final List<Integer> itemStats;

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    public VillagerBattleDetailsAdapter(Activity context, List<String> statNames, List<Float> charStats, List<Integer> itemStats) {
        super(context, R.layout.village_details, charStats);
        this.context = context;
        this.charStats = charStats;
        this.statNames = statNames;
        this.itemStats = itemStats;
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

        Log.i(TAG, "itemBattleStat: " + itemStats.get(position));

        TextView statName = (TextView) rowView.findViewById(R.id.stat_name);
        statName.setText(statNames.get(position));

        TextView statVal = (TextView) rowView.findViewById(R.id.stat_value);
        if (charStats.get(position) + itemStats.get(position) < 1) {
            statVal.setText(String.valueOf(1));
        } else {
            statVal.setText(String.valueOf(charStats.get(position) + itemStats.get(position)));
        }

        if (itemStats.get(position) > 0) {
            statName.setTextColor(Color.parseColor("#47D1FF"));
            statVal.setTextColor(Color.parseColor("#47D1FF"));
        } else if (itemStats.get(position) < 0) {
            statName.setTextColor(Color.parseColor("#FF2A2A"));
            statVal.setTextColor(Color.parseColor("#FF2A2A"));
        } else {
            statName.setTextColor(Color.parseColor("#FFFFFF"));
            statVal.setTextColor(Color.parseColor("#FFFFFF"));
        }

        return rowView;
    }
}
