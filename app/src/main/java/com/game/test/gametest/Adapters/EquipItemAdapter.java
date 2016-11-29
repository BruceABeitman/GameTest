package com.game.test.gametest.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.game.test.gametest.Items.Item;
import com.game.test.gametest.R;
import com.game.test.gametest.Villagers.Villager;
import com.game.test.gametest.Villagers.VillagerJob;

import java.util.List;

/**
 * Created by bbeitman on 10/28/15.
 */
public class EquipItemAdapter extends ArrayAdapter<Item> implements AdapterView.OnItemSelectedListener {

    static private String TAG = "/EquipItemAdapter";
    private Context context;
    private Villager villager;
    private List<Item> items;

    public EquipItemAdapter(Context context, Villager villager, List<Item> items) {
        super(context, R.layout.quest_log , items);
        this.context = context;
        this.villager = villager;
        this.items = items;
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
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.equipment_snippet, parent, false);
        }

        TextView equipmentName = (TextView) view.findViewById(R.id.item_name);
        equipmentName.setText(items.get(position).getName());

        TextView equipmentDescription = (TextView) view.findViewById(R.id.item_description);
        equipmentDescription.setText(items.get(position).getDescription());

        view.setTag(items.get(position));

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

//        Log.i("JDDA", "Village Job was: " + villager.getCurrentJob().getType() + " changed to: " + ((VillagerJob) parent.getItemAtPosition(pos)).getType());

        Item newEquip = ((Item) parent.getItemAtPosition(pos));
        Log.i(TAG, "Equip new item: " + newEquip.getName() + " on " + villager.getName());

        // Check if we have initialized, and the new equip is different
        if ((newEquip.getType() != null) && villager.getEquipmentSet().getEquip(newEquip.getType()) != null) {
            Log.i(TAG, "New " + newEquip.getType() + " is not null");
            if (!(villager.getEquipmentSet().getEquip(newEquip.getType()).equals(newEquip))) {
                // Equip the item on the villager
                Log.i(TAG, "New " + newEquip.getType() + " is not currently equipped");
                villager.equip(newEquip);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    static private void setEquipment(Villager villager, Item item) {

        switch (item.getType()) {
            case MAIN_HAND:
                villager.getEquipmentSet().setMainHand(item);
                break;
            case OFF_HAND:
                villager.getEquipmentSet().setOffHand(item);
                break;
            case HEAD:
                villager.getEquipmentSet().setHead(item);
                break;
            case NECK:
                villager.getEquipmentSet().setNeck(item);
                break;
            case SHOULDER:
                villager.getEquipmentSet().setShoulder(item);
                break;
            case BACK:
                villager.getEquipmentSet().setBack(item);
                break;
            case CHEST:
                villager.getEquipmentSet().setChest(item);
                break;
            case WRIST:
                villager.getEquipmentSet().setWrist(item);
                break;
            case GLOVE:
                villager.getEquipmentSet().setGlove(item);
                break;
            case WAIST:
                villager.getEquipmentSet().setWaist(item);
                break;
            case LEG:
                villager.getEquipmentSet().setLeg(item);
                break;
            case FEET:
                villager.getEquipmentSet().setFeet(item);
                break;
            case FINGER:
                if (villager.getEquipmentSet().getFinger1() == null) {
                    villager.getEquipmentSet().setFinger1(item);
                } else {
                    villager.getEquipmentSet().setFinger2(item);
                }

                break;
        }
    }

}
