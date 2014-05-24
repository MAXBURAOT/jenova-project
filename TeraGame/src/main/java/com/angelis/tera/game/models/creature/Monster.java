package com.angelis.tera.game.models.creature;



public class Monster extends TeraCreature {

    public Monster(final Integer id) {
        super(id);
    }

    public Monster(final Monster monster) {
        super(monster);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Monster)) {
            return false;
        }
        
        return true;
    }
}
