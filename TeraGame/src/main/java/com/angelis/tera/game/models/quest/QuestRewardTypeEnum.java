package com.angelis.tera.game.models.quest;

public enum QuestRewardTypeEnum {
    SELECTABLE(1),
    UNSPECIFIED(2),
    ALL(3)
    ;
    
    public final int value;
    
    QuestRewardTypeEnum(final int value) {
        this.value = value;
    }
}
