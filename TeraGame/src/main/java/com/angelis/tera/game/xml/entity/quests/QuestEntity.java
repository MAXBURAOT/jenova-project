package com.angelis.tera.game.xml.entity.quests;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.common.xml.entity.AbstractXMLEntity;
import com.angelis.tera.game.models.quest.enums.QuestIconTypeEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "quest", namespace = "http://angelis.com/quests")
public class QuestEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = -3186829934050437871L;

    @XmlAttribute(name = "quest_id")
    private int questId;

    @XmlAttribute(name = "quest_type")
    private QuestIconTypeEnum questType;

    @XmlAttribute(name = "start_npc_full_id")
    private int startNpcFullId;

    @XmlAttribute(name = "required_level")
    private int requiredLevel;

    @XmlAttribute(name = "need_quest")
    private int needQuest;

    @XmlAttribute(name = "reward_exp")
    private int rewardExp;

    @XmlAttribute(name = "reward_money")
    private int rewardMoney;
    
    @XmlElement(name = "step", namespace = "http://angelis.com/quests")
    private List<QuestStepEntity> questSteps;

    public int getQuestId() {
        return questId;
    }

    public QuestIconTypeEnum getQuestType() {
        return questType;
    }

    public int getStartNpcFullId() {
        return startNpcFullId;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getNeedQuest() {
        return needQuest;
    }

    public int getRewardExp() {
        return rewardExp;
    }

    public int getRewardMoney() {
        return rewardMoney;
    }

    public List<QuestStepEntity> getQuestSteps() {
        return questSteps;
    }

    @Override
    public void afterUnmarshalling(final Unmarshaller unmarshaller) {
    }

    @Override
    public void onLoad() {
    }

}
