package com.angelis.tera.game.domain.mapper.xml;

import java.util.LinkedList;
import java.util.List;

import com.angelis.tera.common.domain.mapper.xml.XMLMapper;
import com.angelis.tera.common.entity.AbstractEntity;
import com.angelis.tera.common.model.AbstractModel;
import com.angelis.tera.game.models.quest.Quest;
import com.angelis.tera.game.models.quest.QuestStep;
import com.angelis.tera.game.xml.entity.quests.QuestEntity;
import com.angelis.tera.game.xml.entity.quests.QuestStepEntity;

public class QuestMapper extends XMLMapper<QuestEntity, Quest> {

    @Override
    public QuestEntity map(final Quest model, final AbstractEntity linkedEntity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Quest map(final QuestEntity entity, final AbstractModel linkedModel) {
        final Quest quest = new Quest(entity.getQuestId());
        quest.setQuestType(entity.getQuestType());
        quest.setStartNpcFullId(entity.getStartNpcFullId());
        quest.setRequiredLevel(entity.getRequiredLevel());
        quest.setNeedQuest(entity.getNeedQuest());
        quest.setRewardExp(entity.getRewardExp());
        quest.setRewardMoney(entity.getRewardMoney());
        
        final List<QuestStep> steps = new LinkedList<>();
        for (final QuestStepEntity questStepEntity : entity.getQuestSteps()) {
            steps.add(new QuestStep(questStepEntity.getQuestStepType(), questStepEntity.getValue()));
        }
        quest.setSteps(steps);
        
        return quest;
    }

    @Override
    public boolean equals(final Quest model, final QuestEntity entity) {
        // TODO Auto-generated method stub
        return false;
    }

}
