package com.game.test.gametest.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.test.gametest.Buildings.Requirement;
import com.game.test.gametest.R;
import com.game.test.gametest.Villagers.Villager;

import java.util.List;

public class QuestBattleListAdapter  extends ArrayAdapter<Villager> {

    private String TAG = "/QuestBattleListAdapter";
    private final Context context;
    private final List<Villager> battlers;

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    public QuestBattleListAdapter(Context context, List<Villager> battlers) {
        super(context, R.layout.village_details, battlers);
        this.context = context;
        this.battlers = battlers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(R.layout.battler_detail_row, parent, false);
            // configure view holder
        }

        TextView battlerName = (TextView) rowView.findViewById(R.id.battler_name);
        battlerName.setText(battlers.get(position).getName());

        TextView battlerLevel = (TextView) rowView.findViewById(R.id.battler_level);
//        battlerLevel.setText("Lv."+String.valueOf(battlers.get(position).getCurrentJob().getLevel()));

        TextView battlerJob = (TextView) rowView.findViewById(R.id.battler_job);
//        battlerJob.setText(battlers.get(position).getCurrentJob().getName());

        CheckBox selectedBattler = (CheckBox) rowView.findViewById(R.id.battler_checkbox);
        selectedBattler.setTag(battlers.get(position));

        return rowView;
    }
}
