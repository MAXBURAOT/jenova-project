package com.angelis.tera.game.models.enums;

import org.apache.log4j.Logger;

import com.angelis.tera.game.models.action.Action;
import com.angelis.tera.game.models.campfire.CampFire;
import com.angelis.tera.game.models.creature.Monster;
import com.angelis.tera.game.models.creature.Npc;
import com.angelis.tera.game.models.dialog.Dialog;
import com.angelis.tera.game.models.drop.DropItem;
import com.angelis.tera.game.models.gather.Gather;
import com.angelis.tera.game.models.item.Item;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.request.PartyInviteRequest;
import com.angelis.tera.game.models.player.request.Request;

public enum ObjectFamilyEnum {
    //Abstract
    ATTACK("0", Action.class),
    REQUEST("0", Request.class, PartyInviteRequest.class),

    //Visible
    PLAYER("00800001", Player.class),
    CREATURE("00800C00", Npc.class, Monster.class),
    GATHER("00800400", Gather.class),
    DIALOG("00800C00", Dialog.class),
    DROP_ITEM("00800900", DropItem.class),
    CAMPFIRE("00801000", CampFire.class),
    INVENTORY_ITEM("0", Item.class),
    
    SYSTEM("0", Void.class), // TODO
    PROJECTILE("0", Void.class), // TODO
    GUILD("0", Void.class); // TODO
    
    public final Class<?>[] associatedClass;
    public final String value;
    
    ObjectFamilyEnum(final String value, final Class<?>... associatedClass) {
        this.value = value;
        this.associatedClass = associatedClass;
    }
    
    public static final ObjectFamilyEnum fromClass(final Class<?> clazz) {
        for (final ObjectFamilyEnum objectFamily : ObjectFamilyEnum.values()) {
            for (final Class<?> associatedClass : objectFamily.associatedClass) {
                if (clazz == associatedClass) {
                    return objectFamily;
                }
            }
        }
        
        Logger.getLogger(ObjectFamilyEnum.class.getName()).error("Can't find ObjectFamilyEnum with class "+clazz.getName());
        return null;
    }

    public static final ObjectFamilyEnum fromValue(final String value) {
        for (final ObjectFamilyEnum objectFamily : ObjectFamilyEnum.values()) {
            if (value.equals(objectFamily.value)) {
                return objectFamily;
            }
        }
        
        Logger.getLogger(ObjectFamilyEnum.class.getName()).error("Can't find ObjectFamilyEnum with value "+value);
        return null;
    }
}
