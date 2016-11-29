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

import java.util.List;

/**
 * Created by bbeitman on 10/22/15.
 */
public class LogLineAdapter extends ArrayAdapter<String>{

    static private String TAG = "/LogLineAdapter";
    private Context context;
    private List<String> strings;

    public LogLineAdapter(Context context, List<String> strings) {
        super(context, R.layout.quest_log , strings);
        this.context = context;
        this.strings = strings;}

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
            view = layoutInflater.inflate(R.layout.quest_log_snippet, parent, false);
        }

        TextView rawRName = (TextView) view.findViewById(R.id.log_line);
        rawRName.setText(strings.get(position));

        return view;
    }

}
