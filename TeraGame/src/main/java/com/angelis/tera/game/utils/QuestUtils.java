package com.angelis.tera.game.utils;

import com.angelis.tera.game.models.dialog.enums.DialogIconEnum;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.quest.QuestList;
import com.angelis.tera.game.models.quest.Quest;
import com.angelis.tera.game.models.quest.QuestEnv;
import com.angelis.tera.game.models.quest.enums.QuestNpcIconEnum;

public class QuestUtils {
    public static final QuestNpcIconEnum getQuestNpcIconEnum(final Quest quest, final QuestEnv questEnv) {
        QuestNpcIconEnum questNpcIconEnum = null;
        switch (quest.getQuestType()) {
            case STORY:
                if (questEnv == null) {
                    questNpcIconEnum = QuestNpcIconEnum.STORY_QUEST_AVAILABLE;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    questNpcIconEnum = QuestNpcIconEnum.STORY_QUEST_PROCESS;
                } else {
                    questNpcIconEnum = QuestNpcIconEnum.STORY_QUEST_REWARD;
                }
            break;

            case STORY_REPEATABLE:
                if (questEnv == null) {
                    questNpcIconEnum = QuestNpcIconEnum.STORY_REPEATABLE_QUEST_AVAILABLE;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    questNpcIconEnum = QuestNpcIconEnum.STORY_REPEATABLE_QUEST_PROCESS;
                }
            break;

            case NORMAL:
                if (questEnv == null) {
                    questNpcIconEnum = QuestNpcIconEnum.NORMAL_QUEST_AVAILABLE;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    questNpcIconEnum = QuestNpcIconEnum.NORMAL_QUEST_PROCESS;
                } else {
                    questNpcIconEnum = QuestNpcIconEnum.NORMAL_QUEST_REWARD;
                }
            break;

            case NORMAL_REPEATABLE:
                if (questEnv == null) {
                    questNpcIconEnum = QuestNpcIconEnum.NORMAL_REPEATABLE_QUEST_AVAILABLE;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    questNpcIconEnum = QuestNpcIconEnum.NORMAL_REPEATABLE_QUEST_PROCESS;
                }
            break;

            default:
                questNpcIconEnum = QuestNpcIconEnum.NONE;
        }

        return questNpcIconEnum;
    }

    public static final DialogIconEnum getDialogIconEnum(final Quest quest, final QuestEnv questEnv) {
        DialogIconEnum dialogIcon = null;
        switch (quest.getQuestType()) {
            case STORY:
                if (questEnv == null) {
                    dialogIcon = DialogIconEnum.STORY_QUEST_START;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    dialogIcon = DialogIconEnum.STORY_QUEST_PROGRESS;
                } else {
                    dialogIcon = DialogIconEnum.STORY_QUEST_REWARD;
                }
            break;
            
            case STORY_REPEATABLE:
                if (questEnv == null) {
                    dialogIcon = DialogIconEnum.STORY_REPEATABLE_QUEST_START;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    dialogIcon = DialogIconEnum.STORY_REPEATABLE_QUEST_PROGRESS;
                }
            break;

            case NORMAL:
                if (questEnv == null) {
                    dialogIcon = DialogIconEnum.NORMAL_QUEST_START;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    dialogIcon = DialogIconEnum.NORMAL_QUEST_PROGRESS;
                } else {
                    dialogIcon = DialogIconEnum.NORMAL_QUEST_REWARD;
                }
            break;

            case NORMAL_REPEATABLE:
                if (questEnv == null) {
                    dialogIcon = DialogIconEnum.NORMAL_REPEATABLE_QUEST_START;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    dialogIcon = DialogIconEnum.NORMAL_REPEATABLE_QUEST_PROGRESS;
                }
            break;

            default:
                dialogIcon = DialogIconEnum.STORY_QUEST_START;
        }

        return dialogIcon;
    }
    
    public static final boolean checkRequirements(final Player player, final Quest quest) {
        if (player.getLevel() < quest.getRequiredLevel()) {
            return false;
        }

        final QuestList questList = player.getQuestList();
        if (questList.hasQuest(quest)) {
            return false;
        }
        
        if (!questList.hasCompletedRequiredQuest(quest.getRequiredQuests())) {
            return false;
        }
        
        return true;
    }
}
