package com.angelis.tera.game.xml.entity.players;

import java.util.Set;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.common.xml.entity.AbstractXMLEntity;
import com.angelis.tera.game.models.player.enums.PlayerClassEnum;
import com.angelis.tera.game.xml.entity.BaseStatEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "player_basestat", namespace = "http://angelis.com/player_basestats")
public class PlayerBaseStatsEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = 2410250175955924178L;

    @XmlAttribute(name="targetClass")
    private PlayerClassEnum targetClass;

    @XmlElement(name = "basestat", namespace = "http://angelis.com/player_basestats")
    private Set<BaseStatEntity> baseStats;
    

    public PlayerClassEnum getTargetClass() {
        return targetClass;
    }

    public Set<BaseStatEntity> getBaseStats() {
        return baseStats;
    }

    @Override
    public void afterUnmarshalling(final Unmarshaller unmarshaller) {
    }

    @Override
    public void onLoad() {
    }
}
