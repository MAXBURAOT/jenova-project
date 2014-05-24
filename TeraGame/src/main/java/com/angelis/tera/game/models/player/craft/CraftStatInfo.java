package com.angelis.tera.game.models.player.craft;

import com.angelis.tera.game.models.AbstractTeraModel;
import com.angelis.tera.game.models.player.craft.enums.CraftTypeEnum;

public class CraftStatInfo extends AbstractTeraModel {

    private CraftTypeEnum craftType;
    private int level;
    
    public CraftStatInfo(final Integer id) {
        super(id);
    }
    
    public CraftStatInfo(final CraftTypeEnum craftType, final int level) {
        super(null);
        this.craftType = craftType;
        this.level = level;
    }

    public CraftTypeEnum getCraftType() {
        return craftType;
    }

    public void setCraftType(final CraftTypeEnum craftType) {
        this.craftType = craftType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((craftType == null) ? 0 : craftType.hashCode());
        result = prime * result + level;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof CraftStatInfo)) {
            return false;
        }
        final CraftStatInfo other = (CraftStatInfo) obj;
        if (craftType != other.craftType) {
            return false;
        }
        if (level != other.level) {
            return false;
        }
        return true;
    }
}
