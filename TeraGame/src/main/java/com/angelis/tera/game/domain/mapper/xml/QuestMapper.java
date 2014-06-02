package com.angelis.tera.game.domain.mapper.xml;

import java.util.LinkedList;
import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.common.domain.mapper.xml.XMLMapper;
import com.angelis.tera.common.entity.AbstractEntity;
import com.angelis.tera.common.model.AbstractModel;
import com.angelis.tera.game.models.quest.Quest;
import com.angelis.tera.game.models.quest.QuestReward;
import com.angelis.tera.game.models.quest.QuestStep;
import com.angelis.tera.game.models.quest.QuestStepValue;
import com.angelis.tera.game.xml.entity.quests.QuestEntity;
import com.angelis.tera.game.xml.entity.quests.QuestRequiredEntity;
import com.angelis.tera.game.xml.entity.quests.QuestRewardEntity;
import com.angelis.tera.game.xml.entity.quests.QuestStepEntity;
import com.angelis.tera.game.xml.entity.quests.QuestStepValueEntity;

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
        quest.setExperienceReward(entity.getExperienceReward());
        quest.setMoneyReward(entity.getMoneyReward());
        quest.setQuestRewardType(entity.getQuestRewardType());
        quest.setPolicyPointsReward(entity.getPolicyPointsReward());
        quest.setAllianceContributionPointsReward(entity.getAllianceContributionPointsReward());
        quest.setReputationPointsReward(entity.getReputationPointsReward());
        quest.setCreditPointsReward(entity.getCreditPointsReward());
        
        // REQUIRED QUESTS
        final List<Integer> requiredQuests = new FastList<>();
        if (entity.getRequiredQuests() != null && !entity.getRequiredQuests().isEmpty()) {
            for (final QuestRequiredEntity questRequiredEntity : entity.getRequiredQuests())  {
                requiredQuests.add(questRequiredEntity.getQuestId());
            }
        }
        quest.setRequiredQuests(requiredQuests);
        
        // STEPS
        final List<QuestStep> questSteps = new LinkedList<>();
        for (final QuestStepEntity questStepEntity : entity.getQuestSteps()) {
            final List<QuestStepValue> questStepValues = new FastList<>(questStepEntity.getStepValues().size());
            for (final QuestStepValueEntity questStepValueEntity : questStepEntity.getStepValues()) {
                questStepValues.add(new QuestStepValue(questStepValueEntity.getObjectId(), questStepValueEntity.getAmount()));
            }
            questSteps.add(new QuestStep(questStepEntity.getQuestStepType(), questStepValues));
        }
        quest.setQuestSteps(questSteps);
        
        final List<QuestReward> questRewards = new FastList<>();
        if (entity.getQuestRewards() != null && !entity.getQuestRewards().isEmpty()) {
            for (final QuestRewardEntity questRewardEntity : entity.getQuestRewards()) {
                final QuestReward questReward = new QuestReward();
                questReward.setItemId(questRewardEntity.getItemId());
                questReward.setAmount(questRewardEntity.getAmount());
                questRewards.add(questReward);
            }
        }
        quest.setQuestRewards(questRewards);
        
        return quest;
    }

    @Override
    public boolean equals(final Quest model, final QuestEntity entity) {
        // TODO Auto-generated method stub
        return false;
    }

}
