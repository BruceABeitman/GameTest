package com.game.test.gametest.Villagers;

import android.util.Log;

import com.game.test.gametest.Items.Item;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bbeitman on 10/27/15.
 */
public class EquipmentSet {

    private String TAG = "/EquipmentSet";
    @SerializedName("mainHand")
    private Item mainHand;
    @SerializedName("offHand")
    private Item offHand;
    @SerializedName("head")
    private Item head;
    @SerializedName("neck")
    private Item neck;
    @SerializedName("shoulder")
    private Item shoulder;
    @SerializedName("back")
    private Item back;
    @SerializedName("chest")
    private Item chest;
    @SerializedName("wrist")
    private Item wrist;
    @SerializedName("glove")
    private Item glove;
    @SerializedName("waist")
    private Item waist;
    @SerializedName("leg")
    private Item leg;
    @SerializedName("feet")
    private Item feet;
    @SerializedName("finger1")
    private Item finger1;
    @SerializedName("finger2")
    private Item finger2;

    static private Item none = new Item(Item.simple.NOTHING);

    public EquipmentSet(Item mainHand, Item offHand, Item head, Item neck, Item shoulder, Item back, Item chest, Item wrist, Item glove, Item waist, Item leg, Item feet, Item finger1, Item finger2) {
        this.mainHand = mainHand;
        this.offHand = offHand;
        this.head = head;
        this.neck = neck;
        this.shoulder = shoulder;
        this.back = back;
        this.chest = chest;
        this.wrist = wrist;
        this.glove = glove;
        this.waist = waist;
        this.leg = leg;
        this.feet = feet;
        this.finger1 = finger1;
        this.finger2 = finger2;
    }

    public EquipmentSet () {
        this.mainHand = new Item(Item.type.MAIN_HAND);
        this.offHand =  new Item(Item.type.OFF_HAND);
        this.head =     new Item(Item.type.HEAD);
        this.neck =     new Item(Item.type.NECK);
        this.shoulder = new Item(Item.type.SHOULDER);
        this.back =     new Item(Item.type.BACK);
        this.chest =    new Item(Item.type.CHEST);
        this.wrist =    new Item(Item.type.WRIST);
        this.glove =    new Item(Item.type.GLOVE);
        this.waist =    new Item(Item.type.WAIST);
        this.leg =      new Item(Item.type.LEG);
        this.feet =     new Item(Item.type.FEET);
        this.finger1 =  new Item(Item.type.FINGER1);
        this.finger2 =  new Item(Item.type.FINGER2);
    }

    public void equip(Villager villager, Item item) {
        villager.addItemStats(item);
        switch (item.getType()) {
            case MAIN_HAND:
                setMainHand(item);
                break;
            case OFF_HAND:
                setOffHand(item);
                break;
            case HEAD:
                setHead(item);
                break;
            case NECK:
                setNeck(item);
                break;
            case SHOULDER:
                setShoulder(item);
                break;
            case BACK:
                setBack(item);
                break;
            case CHEST:
                setChest(item);
                break;
            case WRIST:
                setWrist(item);
                break;
            case GLOVE:
                setGlove(item);
                break;
            case WAIST:
                setWaist(item);
                break;
            case LEG:
                setLeg(item);
                break;
            case FEET:
                setFeet(item);
                break;
            case FINGER:
                if (getFinger1() == null) {
                    setFinger1(item);
                } else {
                    setFinger2(item);
                }
                break;
        }
        Log.i(TAG, "Set villager's equipment: " + item.getName());
    }

    public void unequip(Villager villager, Item.type itemType) {
        switch (itemType) {
            case MAIN_HAND:
                villager.subtractItemStats(getMainHand());
                setMainHand(new Item(Item.simple.NOTHING));
                break;
            case OFF_HAND:
                villager.subtractItemStats(getOffHand());
                setOffHand(new Item(Item.simple.NOTHING));
                break;
            case HEAD:
                villager.subtractItemStats(getHead());
                setHead(new Item(Item.simple.NOTHING));
                break;
            case NECK:
                villager.subtractItemStats(getNeck());
                setNeck(new Item(Item.simple.NOTHING));
                break;
            case SHOULDER:
                villager.subtractItemStats(getShoulder());
                setShoulder(new Item(Item.simple.NOTHING));
                break;
            case BACK:
                villager.subtractItemStats(getBack());
                setBack(new Item(Item.simple.NOTHING));
                break;
            case CHEST:
                villager.subtractItemStats(getChest());
                setChest(new Item(Item.simple.NOTHING));
                break;
            case WRIST:
                villager.subtractItemStats(getWrist());
                setWrist(new Item(Item.simple.NOTHING));
                break;
            case GLOVE:
                villager.subtractItemStats(getGlove());
                setGlove(new Item(Item.simple.NOTHING));
                break;
            case WAIST:
                villager.subtractItemStats(getWaist());
                setWaist(new Item(Item.simple.NOTHING));
                break;
            case LEG:
                villager.subtractItemStats(getLeg());
                setLeg(new Item(Item.simple.NOTHING));
                break;
            case FEET:
                villager.subtractItemStats(getFeet());
                setFeet(new Item(Item.simple.NOTHING));
                break;
            case FINGER1:
                villager.subtractItemStats(getFinger1());
                setFinger1(new Item(Item.simple.NOTHING));
                break;
            case FINGER2:
                villager.subtractItemStats(getFinger2());
                setFinger2(new Item(Item.simple.NOTHING));
                break;
            case FINGER:
                if (getFinger1() == null) {
                    villager.subtractItemStats(getFinger2());
                    setFinger2(new Item(Item.simple.NOTHING));
                } else {
                    villager.subtractItemStats(getFinger1());
                    setFinger1(new Item(Item.simple.NOTHING));
                }
                break;
        }
    }

    public Item getEquip(Item.type itemType) {
        Item ret = null;
        switch (itemType) {
            case NOTHING:
                ret = none;
                break;
            case MAIN_HAND:
                ret = getMainHand();
                break;
            case OFF_HAND:
                ret = getOffHand();
                break;
            case HEAD:
                ret = getHead();
                break;
            case NECK:
                ret = getNeck();
                break;
            case SHOULDER:
                ret = getShoulder();
                break;
            case BACK:
                ret = getBack();
                break;
            case CHEST:
                ret = getChest();
                break;
            case WRIST:
                ret = getWrist();
                break;
            case GLOVE:
                ret = getGlove();
                break;
            case WAIST:
                ret = getWaist();
                break;
            case LEG:
                ret = getLeg();
                break;
            case FEET:
                ret = getFeet();
                break;
            case FINGER1:
                ret = getFinger1();
                break;
            case FINGER2:
                ret = getFinger2();
                break;
            case FINGER:
                if (getFinger1() == null) {
                    ret = getFinger1();
                } else {
                    ret = getFinger2();
                }
                break;
        }
        return ret;
    }

    public Item getMainHand() {
        return mainHand;
    }

    public void setMainHand(Item mainHand) {
        this.mainHand = mainHand;
    }

    public Item getOffHand() {
        return offHand;
    }

    public void setOffHand(Item offHand) {
        this.offHand = offHand;
    }

    public Item getHead() {
        return head;
    }

    public void setHead(Item head) {
        this.head = head;
    }

    public Item getNeck() {
        return neck;
    }

    public void setNeck(Item neck) {
        this.neck = neck;
    }

    public Item getShoulder() {
        return shoulder;
    }

    public void setShoulder(Item shoulder) {
        this.shoulder = shoulder;
    }

    public Item getBack() {
        return back;
    }

    public void setBack(Item back) {
        this.back = back;
    }

    public Item getChest() {
        return chest;
    }

    public void setChest(Item chest) {
        this.chest = chest;
    }

    public Item getWrist() {
        return wrist;
    }

    public void setWrist(Item wrist) {
        this.wrist = wrist;
    }

    public Item getGlove() {
        return glove;
    }

    public void setGlove(Item glove) {
        this.glove = glove;
    }

    public Item getWaist() {
        return waist;
    }

    public void setWaist(Item waist) {
        this.waist = waist;
    }

    public Item getLeg() {
        return leg;
    }

    public void setLeg(Item leg) {
        this.leg = leg;
    }

    public Item getFeet() {
        return feet;
    }

    public void setFeet(Item feet) {
        this.feet = feet;
    }

    public Item getFinger1() {
        return finger1;
    }

    public void setFinger1(Item finger1) {
        this.finger1 = finger1;
    }

    public Item getFinger2() {
        return finger2;
    }

    public void setFinger2(Item finger2) {
        this.finger2 = finger2;
    }
}
