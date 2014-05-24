package com.angelis.tera.game.xml.entity.creatures;

import java.util.Set;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.xml.entity.AbstractXMLEntity;
import com.angelis.tera.game.xml.entity.DropEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="creature_drop", namespace="http://angelis.com/creature_drops")
public class CreatureDropsEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = 3991450977758936639L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(CreatureDropsEntity.class.getName());
    
    @XmlAttribute(name="creature_id")
    private Integer creatureId;
    
    @XmlAttribute
    private String name;
    
    @XmlElement(name="drop", namespace = "http://angelis.com/creature_drops")
    private Set<DropEntity> drops;

    public Integer getCreatureId() {
        return creatureId;
    }

    public String getName() {
        return name;
    }

    public Set<DropEntity> getDrops() {
        if (this.drops == null) {
            this.drops = new FastSet<DropEntity>(0);
        }
        return this.drops;
    }

    @Override
    public void afterUnmarshalling(final Unmarshaller unmarshaller) {
    }
    
    @Override
    public void onLoad() {
        log.info("Loaded "+getDrops().size()+" creature_drops !");
    }
}

