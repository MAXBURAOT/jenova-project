package com.angelis.tera.game.models.player;

import com.angelis.tera.common.model.AbstractModel;

public class PlayerRelation extends AbstractModel {
    
    private Player player;
    private String note;

    public PlayerRelation(final Player player) {
        super(null);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof PlayerRelation)) {
            return false;
        }
        
        final PlayerRelation relation = (PlayerRelation) o;
        return this.player.equals(relation.getPlayer());
    }
}
