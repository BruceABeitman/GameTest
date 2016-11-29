package com.game.test.gametest.Villagers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.test.gametest.R;
import com.game.test.gametest.Villagers.JobAction;
import com.game.test.gametest.Villagers.Villager;

import java.util.List;

/**
 * Created by bbeitman on 9/27/15.
 */
public class VillagerActionsAdapter extends ArrayAdapter<JobAction>  {
    private String TAG = "/VillagerActionsAdapter";
    private final Context context;
    private final List<JobAction> actions;

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    public VillagerActionsAdapter(Activity context, List<JobAction> actions) {
        super(context, R.layout.village_details, actions);
        this.context = context;
        this.actions = actions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(R.layout.villager_action_row, parent, false);
            // configure view holder
        }

        TextView statName = (TextView) rowView.findViewById(R.id.action);
//        statName.setText(actions.get(position).getName());
        statName.setTag(actions.get(position));

        TextView statVal = (TextView) rowView.findViewById(R.id.action_description);
//        statVal.setText(actions.get(position).getDescription());

        return rowView;
    }
}
