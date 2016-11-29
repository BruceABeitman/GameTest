package com.game.test.gametest.Items;

import com.game.test.gametest.MainActivity;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Items {

    @SerializedName("mainHandList")
    private ItemList mainHandList;
    @SerializedName("offHandList")
    private ItemList offHandList;
    @SerializedName("headList")
    private ItemList headList;
    @SerializedName("neckList")
    private ItemList neckList;
    @SerializedName("shoulderList")
    private ItemList shoulderList;
    @SerializedName("backList")
    private ItemList backList;
    @SerializedName("chestList")
    private ItemList chestList;
    @SerializedName("wristList")
    private ItemList wristList;
    @SerializedName("gloveList")
    private ItemList gloveList;
    @SerializedName("waistList")
    private ItemList waistList;
    @SerializedName("legList")
    private ItemList legList;
    @SerializedName("feetList")
    private ItemList feetList;
    @SerializedName("fingerList")
    private ItemList fingerList;

    public Items(ItemList mainHandList, ItemList offHandList, ItemList headList, ItemList neckList,
                 ItemList shoulderList, ItemList backList, ItemList chestList, ItemList wristList,
                 ItemList gloveList, ItemList waistList, ItemList legList, ItemList feetList, ItemList fingerList) {
        this.mainHandList = mainHandList;
        this.offHandList = offHandList;
        this.headList = headList;
        this.neckList = neckList;
        this.shoulderList = shoulderList;
        this.backList = backList;
        this.chestList = chestList;
        this.wristList = wristList;
        this.gloveList = gloveList;
        this.waistList = waistList;
        this.legList = legList;
        this.feetList = feetList;
        this.fingerList = fingerList;
    }

    public Items() {

        this.mainHandList = new ItemList();
        this.offHandList = new ItemList();
        this.headList = new ItemList();
        this.neckList = new ItemList();
        this.shoulderList = new ItemList();
        this.backList = new ItemList();
        this.chestList = new ItemList();
        this.wristList = new ItemList();
        this.gloveList = new ItemList();
        this.waistList = new ItemList();
        this.legList = new ItemList();
        this.feetList = new ItemList();
        this.fingerList = new ItemList();
    }

    public void itemEquipped(Item item) {
        // Only keep item in list if it's real
        if (!item.getName().equals("<none>")) {
            switch (item.getType()) {
                case MAIN_HAND:
                    mainHandList.getUnequipped().remove(item);
                    mainHandList.getEquipped().add(item);
                    break;
                case OFF_HAND:
                    offHandList.getUnequipped().remove(item);
                    offHandList.getEquipped().add(item);
                    break;
                case HEAD:
                    headList.getUnequipped().remove(item);
                    headList.getEquipped().add(item);
                    break;
                case NECK:
                    neckList.getUnequipped().remove(item);
                    neckList.getEquipped().add(item);
                    break;
                case SHOULDER:
                    shoulderList.getUnequipped().remove(item);
                    shoulderList.getEquipped().add(item);
                    break;
                case BACK:
                    backList.getUnequipped().remove(item);
                    backList.getEquipped().add(item);
                    break;
                case CHEST:
                    chestList.getUnequipped().remove(item);
                    chestList.getEquipped().add(item);
                    break;
                case WRIST:
                    wristList.getUnequipped().remove(item);
                    wristList.getEquipped().add(item);
                    break;
                case GLOVE:
                    gloveList.getUnequipped().remove(item);
                    gloveList.getEquipped().add(item);
                    break;
                case WAIST:
                    waistList.getUnequipped().remove(item);
                    waistList.getEquipped().add(item);
                    break;
                case LEG:
                    legList.getUnequipped().remove(item);
                    legList.getEquipped().add(item);
                    break;
                case FEET:
                    feetList.getUnequipped().remove(item);
                    feetList.getEquipped().add(item);
                    break;
                case FINGER1:
                    fingerList.getUnequipped().remove(item);
                    fingerList.getEquipped().add(item);
                    break;
                case FINGER2:
                    fingerList.getUnequipped().remove(item);
                    fingerList.getEquipped().add(item);
                    break;
                case FINGER:
                    fingerList.getUnequipped().remove(item);
                    fingerList.getEquipped().add(item);
                    break;
                default:
                    break;
            }
        }
    }

    public void itemUnequipped(Item item) {
        // Only unequip item if it's real
        if (!item.getName().equals("<none>")) {
            switch (item.getType()) {
                case MAIN_HAND:
                    mainHandList.getEquipped().remove(item);
                    mainHandList.getUnequipped().add(item);
                    break;
                case OFF_HAND:
                    offHandList.getEquipped().remove(item);
                    offHandList.getUnequipped().add(item);
                    break;
                case HEAD:
                    headList.getEquipped().remove(item);
                    headList.getUnequipped().add(item);
                    break;
                case NECK:
                    neckList.getEquipped().remove(item);
                    neckList.getUnequipped().add(item);
                    break;
                case SHOULDER:
                    shoulderList.getEquipped().remove(item);
                    shoulderList.getUnequipped().add(item);
                    break;
                case BACK:
                    backList.getEquipped().remove(item);
                    backList.getUnequipped().add(item);
                    break;
                case CHEST:
                    chestList.getEquipped().remove(item);
                    chestList.getUnequipped().add(item);
                    break;
                case WRIST:
                    wristList.getEquipped().remove(item);
                    wristList.getUnequipped().add(item);
                    break;
                case GLOVE:
                    gloveList.getEquipped().remove(item);
                    gloveList.getUnequipped().add(item);
                    break;
                case WAIST:
                    waistList.getEquipped().remove(item);
                    waistList.getUnequipped().add(item);
                    break;
                case LEG:
                    legList.getEquipped().remove(item);
                    legList.getUnequipped().add(item);
                    break;
                case FEET:
                    feetList.getEquipped().remove(item);
                    feetList.getUnequipped().add(item);
                    break;
                case FINGER1:
                    fingerList.getEquipped().remove(item);
                    fingerList.getUnequipped().add(item);
                    break;
                case FINGER2:
                    fingerList.getEquipped().remove(item);
                    fingerList.getUnequipped().add(item);
                    break;
                case FINGER:
                    fingerList.getEquipped().remove(item);
                    fingerList.getUnequipped().add(item);
                    break;
                default:
                    break;
            }
        }
    }

    public List<Item> getItems() {
        List<Item> allItems = new ArrayList<>();
        allItems.addAll(mainHandList.getAll());
        allItems.addAll(offHandList.getAll());
        allItems.addAll(headList.getAll());
        allItems.addAll(neckList.getAll());
        allItems.addAll(shoulderList.getAll());
        allItems.addAll(backList.getAll());
        allItems.addAll(chestList.getAll());
        allItems.addAll(wristList.getAll());
        allItems.addAll(gloveList.getAll());
        allItems.addAll(waistList.getAll());
        allItems.addAll(legList.getAll());
        allItems.addAll(feetList.getAll());
        allItems.addAll(fingerList.getAll());
        return allItems;
    }

    public void constructStartingItems() {
        mainHandList.addUnequipped(new Item(Item.simple.WOODCUTTERSAXE));
        mainHandList.addUnequipped(new Item(Item.simple.PICKAXE));
        mainHandList.addUnequipped(new Item(Item.simple.BOW));
    }

    public int getQuantity() {

        int totalSize = 0;

        totalSize += this.mainHandList.size();
        totalSize += this.offHandList.size();
        totalSize += this.headList.size();
        totalSize += this.neckList.size();
        totalSize += this.shoulderList.size();
        totalSize += this.backList.size();
        totalSize += this.chestList.size();
        totalSize += this.wristList.size();
        totalSize += this.gloveList.size();
        totalSize += this.waistList.size();
        totalSize += this.legList.size();
        totalSize += this.feetList.size();
        totalSize += this.fingerList.size();

        return totalSize;
    }

    public ItemList getMainHandList() {
        return mainHandList;
    }

    public void setMainHandList(ItemList mainHandList) {
        this.mainHandList = mainHandList;
    }

    public ItemList getOffHandList() {
        return offHandList;
    }

    public void setOffHandList(ItemList offHandList) {
        this.offHandList = offHandList;
    }

    public ItemList getHeadList() {
        return headList;
    }

    public void setHeadList(ItemList headList) {
        this.headList = headList;
    }

    public ItemList getNeckList() {
        return neckList;
    }

    public void setNeckList(ItemList neckList) {
        this.neckList = neckList;
    }

    public ItemList getShoulderList() {
        return shoulderList;
    }

    public void setShoulderList(ItemList shoulderList) {
        this.shoulderList = shoulderList;
    }

    public ItemList getBackList() {
        return backList;
    }

    public void setBackList(ItemList backList) {
        this.backList = backList;
    }

    public ItemList getChestList() {
        return chestList;
    }

    public void setChestList(ItemList chestList) {
        this.chestList = chestList;
    }

    public ItemList getWristList() {
        return wristList;
    }

    public void setWristList(ItemList wristList) {
        this.wristList = wristList;
    }

    public ItemList getGloveList() {
        return gloveList;
    }

    public void setGloveList(ItemList gloveList) {
        this.gloveList = gloveList;
    }

    public ItemList getWaistList() {
        return waistList;
    }

    public void setWaistList(ItemList waistList) {
        this.waistList = waistList;
    }

    public ItemList getLegList() {
        return legList;
    }

    public void setLegList(ItemList legList) {
        this.legList = legList;
    }

    public ItemList getFeetList() {
        return feetList;
    }

    public void setFeetList(ItemList feetList) {
        this.feetList = feetList;
    }

    public ItemList getFingerList() {
        return fingerList;
    }

    public void setFingerList(ItemList fingerList) {
        this.fingerList = fingerList;
    }
}
