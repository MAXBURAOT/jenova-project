package com.angelis.tera.game.models.creature;

public class Npc extends TeraCreature {

    public Npc(final Integer id) {
        super(id);
    }

    public Npc(final Npc npc) {
        super(npc);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Npc)) {
            return false;
        }
        
        return true;
    }
}
