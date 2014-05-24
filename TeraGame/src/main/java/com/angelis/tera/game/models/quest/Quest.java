package com.angelis.tera.game.models.quest;

import java.util.List;

import com.angelis.tera.game.models.AbstractTeraModel;
import com.angelis.tera.game.models.quest.enums.QuestIconTypeEnum;

public class Quest extends AbstractTeraModel {

    private QuestIconTypeEnum questType;
    private int startNpcFullId;
    private int requiredLevel;
    private int needQuest;
    private int rewardExp;
    private int rewardMoney;
    private List<QuestStep> steps;
    
    public Quest(final Integer id) {
        super(id);
    }

    public QuestIconTypeEnum getQuestType() {
        return questType;
    }

    public void setQuestType(final QuestIconTypeEnum questType) {
        this.questType = questType;
    }

    public int getStartNpcFullId() {
        return startNpcFullId;
    }

    public void setStartNpcFullId(final int startNpcFullId) {
        this.startNpcFullId = startNpcFullId;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(final int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public int getNeedQuest() {
        return needQuest;
    }

    public void setNeedQuest(final int needQuest) {
        this.needQuest = needQuest;
    }

    public int getRewardExp() {
        return rewardExp;
    }

    public void setRewardExp(final int rewardExp) {
        this.rewardExp = rewardExp;
    }

    public int getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(final int rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public List<QuestStep> getSteps() {
        return steps;
    }

    public void setSteps(final List<QuestStep> steps) {
        this.steps = steps;
    }

    public final int getQuestFullId() {
        return this.getId()*1000+1;
    }
}
