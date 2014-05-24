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
@XmlRootElement(name="creature_templates", namespace="http://angelis.com/creature_templates")
public class CreatureTemplateEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = 1262475264628559730L;
    
    /** LOGGER */
    private static Logger log = Logger.getLogger(CreatureDropsEntityHolder.class.getName());
    
    @XmlElement(name="creature_template", namespace = "http://angelis.com/creature_templates")
    private Set<CreatureTemplateEntity> creatureTemplates;

    public Set<CreatureTemplateEntity> getCreatureTemplates() {
        if (creatureTemplates == null) {
            creatureTemplates = new FastSet<>(0);
        }
        return creatureTemplates;
    }

    @Override
    public void afterUnmarshalling(final Unmarshaller unmarshaller) {
    }

    @Override
    public void onLoad() {
        log.info("Loaded "+getCreatureTemplates().size()+" creature templates !");
    }
}
