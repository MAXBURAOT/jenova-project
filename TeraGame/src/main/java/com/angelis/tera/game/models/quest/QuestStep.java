package com.angelis.tera.game.models.quest;

import java.util.List;

import com.angelis.tera.game.models.quest.enums.QuestStepTypeEnum;

public final class QuestStep {

    private final QuestStepTypeEnum questStepType;
    private final List<QuestStepValue> stepValues;
    
    public QuestStep(final QuestStepTypeEnum questStepType, final List<QuestStepValue> stepValues) {
        this.questStepType = questStepType;
        this.stepValues = stepValues;
    }

    public QuestStepTypeEnum getQuestStepType() {
        return questStepType;
    }

    public List<QuestStepValue> getStepValues() {
        return stepValues;
    }
    
    public final boolean containsObjectId(final int objectId) {
        for (final QuestStepValue questStepValue : stepValues) {
            if (questStepValue.getObjectId() == objectId) {
                return true;
            }
        }
        return false;
    }
    
    public int getRequiredObjectAmountByObjectId(final int objectId) {
        int amount = 0;
        for (final QuestStepValue questStepValue : stepValues) {
            if (questStepValue.getObjectId() == objectId) {
                amount = questStepValue.getAmount();
                break;
            }
        }
        return amount;
    }
}
