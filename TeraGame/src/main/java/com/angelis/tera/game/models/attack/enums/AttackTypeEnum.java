package com.angelis.tera.game.models.attack.enums;

public enum AttackTypeEnum {
    NORMAL(1),
    CRITICAL(65537)
    ;
    
    public final int value;
    
    AttackTypeEnum(final int value) {
        this.value = value;
    }
}
