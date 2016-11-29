package com.game.test.gametest.Items;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bbeitman on 10/28/15.
 */
public class ItemList {

    @SerializedName("equipped")
    List<Item> equipped;

    @SerializedName("unequipped")
    List<Item> unequipped;

    static private Item blank = new Item(Item.simple.NOTHING);

    public ItemList(List<Item> equipped, List<Item> unequipped) {
        this.equipped = equipped;
        this.unequipped = unequipped;
    }

    public ItemList() {
        this.equipped = new ArrayList<>();
        this.unequipped = new ArrayList<>();
    }

    public List<Item> getEquipped() {
        return equipped;
    }

    public void setEquipped(List<Item> equipped) {
        this.equipped = equipped;
    }

    public void addEquipped(Item item) {
        equipped.add(item);
    }

    public List<Item> getUnequipped() {
        return unequipped;
    }

    /*
    Returns a list of items including:
        1. a "blank" item for deselecting
        2. the item the villager currently has equipped

    If the villager has no currently equipped item, then only the
    "blank" item is added

     */
    public List<Item> getUnequippedWithBlank(Item villagerEquipped) {
        List<Item> unequippedWithBlank = new ArrayList<>();
        // If villager has an item equipped then get that item first
        if (!villagerEquipped.getName().equals("<none>")) {
            if (!villagerEquipped.getType().equals(Item.type.NOTHING)) {
                unequippedWithBlank.add(villagerEquipped);
            }
        }
        // Always enter a blank item (of correct type) to allow de-equipping
        unequippedWithBlank.add(new Item(villagerEquipped.getType()));
        // Finally enter all unequipped items
        unequippedWithBlank.addAll(unequipped);
        return unequippedWithBlank;
    }

    public void setUnequipped(List<Item> unequipped) {
        this.unequipped = unequipped;
    }

    public void addUnequipped(Item item) {
        unequipped.add(item);
    }

    public List<Item> getAll() {
        List<Item> all = new ArrayList<>();
        all.addAll(equipped);
        all.addAll(unequipped);
        return all;
    }

    public int size() {
        int size = 0;
        size += equipped.size();
        size += unequipped.size();
        return size;
    }
}
