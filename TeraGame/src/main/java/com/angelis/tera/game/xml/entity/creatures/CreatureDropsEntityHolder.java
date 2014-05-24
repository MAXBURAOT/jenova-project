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
@XmlRootElement(name="creature_drops", namespace="http://angelis.com/creature_drops")
public class CreatureDropsEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = -1081662686213542284L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(CreatureDropsEntityHolder.class.getName());
    
    @XmlElement(name="creature_drop", namespace = "http://angelis.com/creature_drops")
    private Set<CreatureDropsEntity> creatureDrops;

    public Set<CreatureDropsEntity> getCreatureDrops() {
        if (this.creatureDrops == null) {
            this.creatureDrops = new FastSet<CreatureDropsEntity>(0);
        }
        return this.creatureDrops;
    }

    @Override
    public void afterUnmarshalling(final Unmarshaller unmarshaller) {
    }
    
    @Override
    public void onLoad() {
        log.info("Loaded "+getCreatureDrops().size()+" creature drops !");
    }
}
