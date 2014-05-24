package com.angelis.tera.game.models.player.craft;

import java.util.Set;

import javolution.util.FastSet;

import com.angelis.tera.game.models.player.craft.enums.CraftTypeEnum;

public class CraftStats {
    
    public static final int MAX_LEVEL = 410;
    
    public final Set<CraftStatInfo> craftStatInfos;
    
    public CraftStats(final Set<CraftStatInfo> craftStatInfos) {
        this.craftStatInfos = craftStatInfos;
    }
    
    public CraftStats() {
        this.craftStatInfos = new FastSet<>();
        this.craftStatInfos.add(new CraftStatInfo(CraftTypeEnum.WEAPON_SMITHING, 1));
        this.craftStatInfos.add(new CraftStatInfo(CraftTypeEnum.FOCUS_CRAFTING, 1));
        this.craftStatInfos.add(new CraftStatInfo(CraftTypeEnum.ARMOR_SMITHING, 1));
        this.craftStatInfos.add(new CraftStatInfo(CraftTypeEnum.LEATHER_WORKING, 1));
        this.craftStatInfos.add(new CraftStatInfo(CraftTypeEnum.TAILORING, 1));
        this.craftStatInfos.add(new CraftStatInfo(CraftTypeEnum.ALCHEMY, 1));
        this.craftStatInfos.add(new CraftStatInfo(CraftTypeEnum.RUNES, 1));
    }

    public Set<CraftStatInfo> getCraftStatInfos() {
        return this.craftStatInfos;
    }
    
    public Integer getCraftLevel(final CraftTypeEnum craftType) {
        return this.getGatherStatInfo(craftType).getLevel();
    }
    
    public void setCraftLevel(final CraftTypeEnum craftType, int level) {
        if (level < 1) {
            level = 1;
        }
        
        if (level > MAX_LEVEL) {
            level = MAX_LEVEL;
        }
        
        this.getGatherStatInfo(craftType).setLevel(level);
    }
    
    public void processCraft(final CraftTypeEnum craftType) {
        this.setCraftLevel(craftType, this.getCraftLevel(craftType)+1);
    }
    
    private CraftStatInfo getGatherStatInfo(final CraftTypeEnum craftType) {
        for (final CraftStatInfo craftStatInfo : craftStatInfos) {
            if (craftStatInfo.getCraftType() == craftType) {
                return craftStatInfo;
            }
        }
        
        return null;
    }
}
