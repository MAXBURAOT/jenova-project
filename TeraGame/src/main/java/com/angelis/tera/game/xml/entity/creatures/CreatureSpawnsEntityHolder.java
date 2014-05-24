package com.angelis.tera.game.xml.entity.creatures;

import java.util.Set;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.xml.entity.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="creature_spawns", namespace="http://angelis.com/creature_spawns")
public class CreatureSpawnsEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = -7067997321438234684L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(CreatureSpawnsEntityHolder.class.getName());
    
    @XmlElement(name="creature_spawn", namespace = "http://angelis.com/creature_spawns")
    private Set<CreatureSpawnEntity> creatures;

    public Set<CreatureSpawnEntity> getCreatures() {
        if (this.creatures == null) {
            this.creatures = new FastSet<CreatureSpawnEntity>(0);
        }
        return this.creatures;
    }

    @Override
    public void afterUnmarshalling(final Unmarshaller unmarshaller) {
    }
    
    @Override
    public void onLoad() {
        log.info("Loaded "+getCreatures().size()+" creature spawns !");
    }
}
