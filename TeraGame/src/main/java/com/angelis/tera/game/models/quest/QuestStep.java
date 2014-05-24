package com.angelis.tera.game.models.quest;

import com.angelis.tera.game.models.quest.enums.QuestStepTypeEnum;

public final class QuestStep {
    private final QuestStepTypeEnum questStepType;
    private final int value;
    
    public QuestStep(final QuestStepTypeEnum questStepType, final int value) {
        this.questStepType = questStepType;
        this.value = value;
    }

    public QuestStepTypeEnum getQuestStepType() {
        return questStepType;
    }

    public int getValue() {
        return value;
    }
}
