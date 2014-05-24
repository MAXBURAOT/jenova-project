package com.angelis.tera.game.models.pegasus;

import com.angelis.tera.game.models.AbstractTeraModel;

public class PegasusFlyPoint extends AbstractTeraModel {

    private int cost;
    private int fromNameId;
    private int toNameId;
    private int level;

    public PegasusFlyPoint(final Integer id) {
        super(id);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(final int cost) {
        this.cost = cost;
    }

    public int getFromNameId() {
        return fromNameId;
    }

    public void setFromNameId(final int fromNameId) {
        this.fromNameId = fromNameId;
    }

    public int getToNameId() {
        return toNameId;
    }

    public void setToNameId(final int toNameId) {
        this.toNameId = toNameId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }
}
