package com.angelis.tera.game.xml.entity.quests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.game.models.quest.enums.QuestStepTypeEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "step", namespace = "http://angelis.com/quests")
public class QuestStepEntity {

    @XmlAttribute(name = "type")
    private QuestStepTypeEnum questStepType;

    @XmlAttribute(name = "value")
    private int value;

    public QuestStepTypeEnum getQuestStepType() {
        return questStepType;
    }

    public int getValue() {
        return value;
    }
}
