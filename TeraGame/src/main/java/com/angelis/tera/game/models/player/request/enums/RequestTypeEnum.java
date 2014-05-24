package com.angelis.tera.game.models.player.request.enums;

import org.apache.log4j.Logger;

public enum RequestTypeEnum {
    PARTY_INVITE(4),
    DUEL(12),
    PEGASUS(15)
    ;
    
    public final int value;
    
    RequestTypeEnum(final int value) {
        this.value = value;
    }

    public static RequestTypeEnum fromValue(final short value) {
        for (final RequestTypeEnum requestType : RequestTypeEnum.values()) {
            if (requestType.value == value) {
                return requestType;
            }
        }
        
        Logger.getLogger(RequestTypeEnum.class.getName()).error("Can't find RequestTypeEnum with value "+value);
        return null;
    }
}
