package com.game.test.gametest.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.game.test.gametest.R;
import com.game.test.gametest.Villagers.Villager;
import com.game.test.gametest.Villagers.VillagerJob;

import java.util.List;

/**
 * Created by bbeitman on 9/23/15.
 */
public class BuilderDropDownAdapter extends ArrayAdapter<Villager> implements AdapterView.OnItemSelectedListener {

    static private String TAG = "/BuilderDropDownAdapter";
    private Context context;
    private List<Villager> builders;

    public BuilderDropDownAdapter(Context context, int textViewResourceId, List<Villager> builders) {
        super(context, textViewResourceId, builders);
        this.context = context;
        this.builders = builders;}

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
        rawRName.setText(builders.get(position).getName());
        view.setTag(builders.get(position));

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Log.i(TAG, "ItemSelected");
        // Super hacky way to check if we are in the initial ItemSelects from the spinners
        // We need to count to the number of spinners we have (equal to the number of villagers)
        // After that, any ItemSelects are now the user activating them, so Update and restart count.
//        selectionCount++;
//        if (selectionCount > villagerCount) {
//            Log.i("JDDA", villager.getName() + " job set to " + parent.getItemAtPosition(pos));
//            selectionCount = 0;
////            Villager villager = (Villager) view.getTag();
//            villager.setJob((VillagerJob) parent.getItemAtPosition(pos));
//            MainActivity.setUpdateList(true);
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
