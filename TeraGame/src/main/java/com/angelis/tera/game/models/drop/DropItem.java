package com.angelis.tera.game.models.drop;

import com.angelis.tera.game.controllers.DropItemController;
import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.item.Item;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.visible.VisibleTeraObject;
import com.angelis.tera.game.models.visible.WorldPosition;

public class DropItem extends VisibleTeraObject {

    private final Creature creature;
    private final Player owner;
    private final Item item;
    private final WorldPosition worldPosition;
    private final int amount;
    private boolean isFree;
    
    public DropItem(final Integer id, final Creature creature, final Player owner, final Item item, final WorldPosition worldPosition, final int amount) {
        super(id, new DropItemController());
        this.creature = creature;
        this.owner = owner;
        this.item = item;
        this.worldPosition = worldPosition;
        this.amount = amount;
    }

    public Creature getCreature() {
        return creature;
    }

    public Player getOwner() {
        return owner;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public WorldPosition getWorldPosition() {
        return worldPosition;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(final boolean isFree) {
        this.isFree = isFree;
    }

    @Override
    public DropItemController getController() {
        return (DropItemController) this.controller;
    }
}
