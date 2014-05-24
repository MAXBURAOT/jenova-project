package com.angelis.tera.game.models.quest;

import java.util.List;

public class QuestData {
    private final Quest quest;
    private int step;
    private List<Integer> counters;

    public QuestData(final Quest quest) {
        this.quest = quest;
    }

    public Quest getQuest() {
        return quest;
    }

    public int getStep() {
        return step;
    }

    public void setStep(final int step) {
        this.step = step;
    }
    
    public List<Integer> getCounters() {
        return counters;
    }

    public void setCounters(final List<Integer> counters) {
        this.counters = counters;
    }
}
