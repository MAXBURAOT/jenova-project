package com.angelis.tera.game.xml.entity.creatures;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.game.models.creature.enums.CreatureRankEnum;
import com.angelis.tera.game.models.enums.CreatureTitleEnum;
import com.angelis.tera.game.xml.entity.BaseStatEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="creature_template", namespace="http://angelis.com/creature_templates")
public class CreatureTemplateEntity {
    
    @XmlAttribute(name = "npc_full_id")
    private int npcFullId;

    @XmlAttribute(name = "rank")
    private CreatureRankEnum creatureRank;
    
    @XmlAttribute(name = "exp")
    private int exp;
    
    @XmlAttribute(name = "hunting_zone_id")
    private int huntingZoneId;
    
    @XmlAttribute(name="model_id")
    private int modelId;
    
    @XmlAttribute(name = "speed")
    private int speed;
    
    @XmlAttribute(name = "offensive")
    private int offensive;
    
    @XmlAttribute(name = "title")
    private CreatureTitleEnum creatureTitle;
    
    @XmlElement(name = "basestat", namespace = "http://angelis.com/creature_templates")
    private BaseStatEntity baseStat;

    public int getNpcFullId() {
        return npcFullId;
    }

    public CreatureRankEnum getTemplateRank() {
        return creatureRank;
    }

    public int getExp() {
        return exp;
    }

    public int getHuntingZoneId() {
        return huntingZoneId;
    }

    public int getModelId() {
        return modelId;
    }

    public int getSpeed() {
        return speed;
    }

    public int getOffensive() {
        return offensive;
    }

    public CreatureTitleEnum getCreatureTitle() {
        return creatureTitle;
    }

    public BaseStatEntity getBaseStat() {
        return baseStat;
    }
}
