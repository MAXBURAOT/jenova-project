package com.angelis.tera.game.models.quest.enums;

public enum QuestNpcIconEnum {

    NONE(0),
    REWARD_MISSION(1),
    PROCESS_MISSION(2),
    PROCESS_REPETABLE_MISSION(3),
    AVAILABLE_MISSION(4),
    AVAILABLE_REPETABLE_MISSION(5),
    REWARD_ZONE(6),
    PROCESS_ZONE(7),
    PROCESS_REPETABLE_ZONE(8),
    AVAILABLE_ZONE(9),
    AVAILABLE_REPETABLE_ZONE(10),
    REWARD_GUILD(11),
    PROCESS_GUILD(12),
    PROCESS_REPETABLE_GUILD(13),
    AVAILABLE_GUILD(14),
    AVAILABLE_REPETABLE_GUILD(15),
    AVAILABLE_ON_NEXT_LEVEL(16) // not sure
    ;
    
    public final int value;
    
    QuestNpcIconEnum(final int value) {
        this.value = value;
    }
}
