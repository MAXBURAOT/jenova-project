package com.angelis.tera.game.models.player.quest;

import java.util.Iterator;
import java.util.Set;

import javolution.util.FastList;
import javolution.util.FastSet;

import com.angelis.tera.game.models.quest.Quest;
import com.angelis.tera.game.models.quest.QuestData;

public class QuestList {
    public final Set<QuestData> questDatas;

    public QuestList() {
        this.questDatas = new FastSet<>();
    }
    
    public QuestList(final Set<QuestData> questDatas) {
        this.questDatas = questDatas;
    }
    
    public QuestData addQuest(final Quest quest) {
        final QuestData questData = new QuestData(quest);
        questData.setStep(1);
        questData.setCounters(new FastList<Integer>());
        this.questDatas.add(questData);
        
        return questData;
    }
    
    public void removeQuest(final Quest quest) {
        if (!this.hasQuest(quest)) {
            return;
        }
        
        final Iterator<QuestData> itr = this.questDatas.iterator();
        while (itr.hasNext()) {
            final QuestData questData = itr.next();
            if (questData.getQuest().equals(quest)) {
                itr.remove();
                break;
            }
        }
    }
    
    public boolean hasQuest(final Quest quest) {
        boolean hasQuest = false;
        for (final QuestData questData : this.questDatas) {
            if (questData.getQuest().equals(quest)) {
                hasQuest = true;
                break;
            }
        }
        return hasQuest;
    }
    
    public final Set<QuestData> getQuestDatas() {
       return this.questDatas;
    }

    public int getQuestStep(final Quest quest) {
        int step = 0;
        for (final QuestData questData : this.questDatas) {
            if (questData.getQuest().equals(quest)) {
                step = questData.getStep();
                break;
            }
        }
        return step;
    }
}
