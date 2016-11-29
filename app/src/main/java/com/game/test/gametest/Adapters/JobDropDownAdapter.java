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

public class JobDropDownAdapter extends ArrayAdapter<VillagerJob> implements AdapterView.OnItemSelectedListener {

    static private String TAG = "/JDDA";
    private Context context;
    private Villager villager;

    public JobDropDownAdapter(Context context, int textViewResourceId, Villager villager, List<VillagerJob> villagerJobs) {
        super(context, textViewResourceId, villagerJobs);
        this.context = context;
        this.villager = villager;
        // TODO Auto-generated constructor stub
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
        rawRName.setText(villager.getAvailableJobNames().get(position));
        view.setTag(villager);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

//        Log.i("JDDA", "Village Job was: " + villager.getCurrentJob().getType() + " changed to: " + ((VillagerJob) parent.getItemAtPosition(pos)).getType());
        Log.i(TAG, "Villager is working: " + villager.isWorking());

        // Check if we are actually changing the villager's job or just reloading the list
        // If we are changing the job then assign the new job to the villager
        if (!villager.getCurrentJob().getType().equals(((VillagerJob) parent.getItemAtPosition(pos)).getType())) {
            villager.setJob((VillagerJob) parent.getItemAtPosition(pos));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
