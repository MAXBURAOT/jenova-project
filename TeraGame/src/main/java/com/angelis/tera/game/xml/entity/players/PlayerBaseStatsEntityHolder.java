package com.angelis.tera.game.xml.entity.players;

import java.util.Set;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.xml.entity.AbstractXMLEntity;
import com.angelis.tera.game.models.player.enums.PlayerClassEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "player_basestats", namespace = "http://angelis.com/player_basestats")
public class PlayerBaseStatsEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = 2941004152168232255L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(PlayerBaseStatsEntityHolder.class.getName());

    @XmlElement(name = "player_basestat", namespace = "http://angelis.com/player_basestats")
    private Set<PlayerBaseStatsEntity> playerBaseStats;

    public Set<PlayerBaseStatsEntity> getPlayerBaseStats() {
        if (playerBaseStats == null) {
            playerBaseStats = new FastSet<>(0);
        }
        return playerBaseStats;
    }

    public PlayerBaseStatsEntity getPlayerBaseStatsByTargetClass(final PlayerClassEnum classEnum) {
        for (final PlayerBaseStatsEntity baseStats : this.getPlayerBaseStats()) {
            if (baseStats.getTargetClass() == classEnum) {
                return baseStats;
            }
        }
        return null;
    }

    @Override
    public void afterUnmarshalling(final Unmarshaller unmarshaller) {
    }

    @Override
    public void onLoad() {
        log.info("Loaded " + getPlayerBaseStats().size() + " base stats !");
    }
}
