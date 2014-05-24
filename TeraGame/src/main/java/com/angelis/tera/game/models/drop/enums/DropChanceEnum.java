package com.angelis.tera.game.models.drop.enums;

// TODO stats must not be good !!!!!!!!!!!!!!
public enum DropChanceEnum {
    MEDIUM(50),
    LOW(75),
    VERY_LOW(80),
    RARE(90)
    ;
    
    public final int value;
    
    DropChanceEnum(final int value) {
        this.value = value;
    }
}
