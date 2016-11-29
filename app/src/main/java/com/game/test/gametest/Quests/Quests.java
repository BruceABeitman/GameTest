package com.game.test.gametest.Quests;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Quests {

    @SerializedName("quests")
    List<Quest> quests;

    public Quests() {
        quests = new ArrayList<>();
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
    }

    public void removeQuest(Quest quest) {
        quests.remove(quest);
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public int getQuantity() {
        return quests.size();
    }
}
