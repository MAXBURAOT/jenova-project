package com.angelis.tera.game.models.template;

import com.angelis.tera.game.models.enums.CreatureTitleEnum;

public class TeraCreatureTemplate extends Template {

    private CreatureTitleEnum creatureTitle;
    private int huntingZoneId;
    private short creatureType;
    private int modelId;
    private boolean aggresive;

    public CreatureTitleEnum getCreatureTitle() {
        return creatureTitle;
    }

    public void setCreatureTitle(final CreatureTitleEnum creatureTitle) {
        this.creatureTitle = creatureTitle;
    }

    public int getHuntingZoneId() {
        return huntingZoneId;
    }

    public void setHuntingZoneId(final int huntingZoneId) {
        this.huntingZoneId = huntingZoneId;
    }

    public short getCreatureType() {
        return creatureType;
    }

    public void setCreatureType(final short creatureType) {
        this.creatureType = creatureType;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(final int modelId) {
        this.modelId = modelId;
    }

    public boolean isAggresive() {
        return aggresive;
    }

    public void setAggresive(final boolean aggresive) {
        this.aggresive = aggresive;
    }

    public final void copy(final TeraCreatureTemplate template) {
        this.creatureTitle = template.creatureTitle;
        this.huntingZoneId = template.huntingZoneId;
        this.creatureType = template.creatureType;
        this.modelId = template.modelId;
        this.aggresive = template.aggresive;
    }
}
