package com.angelis.tera.game.xml.entity.creatures;

import java.util.Set;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import com.angelis.tera.common.xml.entity.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "creature_spawn", namespace = "http://angelis.com/creature_spawns")
public class CreatureSpawnEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = -5082744641054235756L;

    @XmlAttribute(name = "id", required = true)
    private Integer id;

    @XmlAttribute(name = "offensive", required = true)
    private boolean offensive;

    @XmlElement(name = "spawn", namespace = "http://angelis.com/creature_spawns")
    private Set<TeraCreatureSpawnEntity> creatureSpawns;

    @Override
    public Integer getId() {
        return id;
    }

    public boolean isOffensive() {
        return offensive;
    }

    public Set<TeraCreatureSpawnEntity> getCreatureSpawns() {
        if (this.creatureSpawns == null) {
            this.creatureSpawns = new FastSet<>(0);
        }
        return this.creatureSpawns;
    }

    @Override
    public void afterUnmarshalling(final Unmarshaller unmarshaller) {
    }

    @Override
    public void onLoad() {
    }
}
