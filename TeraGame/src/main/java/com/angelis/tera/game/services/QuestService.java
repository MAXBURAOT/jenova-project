package com.angelis.tera.game.services;

import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.utils.CollectionUtils;
import com.angelis.tera.game.domain.mapper.xml.QuestMapper;
import com.angelis.tera.game.models.creature.Npc;
import com.angelis.tera.game.models.dialog.Dialog;
import com.angelis.tera.game.models.dialog.DialogButton;
import com.angelis.tera.game.models.dialog.actions.ReadQuestDialogAction;
import com.angelis.tera.game.models.dialog.enums.DialogIconEnum;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.quest.QuestList;
import com.angelis.tera.game.models.quest.Quest;
import com.angelis.tera.game.models.quest.QuestData;
import com.angelis.tera.game.models.quest.enums.QuestNpcIconEnum;
import com.angelis.tera.game.models.visible.VisibleTeraObject;
import com.angelis.tera.game.models.visible.enums.VisibleTypeEnum;
import com.angelis.tera.game.network.SystemMessages;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.network.packet.server.SM_OPCODE_LESS_PACKET;
import com.angelis.tera.game.network.packet.server.SM_QUEST_BALLOON;
import com.angelis.tera.game.network.packet.server.SM_QUEST_INFO;
import com.angelis.tera.game.network.packet.server.SM_QUEST_VILLAGER_INFO;
import com.angelis.tera.game.network.packet.server.SM_QUEST_WORLD_VILLAGER_INFO_CLEAR;
import com.angelis.tera.game.xml.entity.quests.QuestEntity;
import com.angelis.tera.game.xml.entity.quests.QuestEntityHolder;

public class QuestService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(QuestService.class.getName());

    private final Map<Integer, List<Quest>> quests = new FastMap<>();

    private QuestService() {
    }

    @Override
    public void onInit() {
        for (final QuestEntity questEntity : XMLService.getInstance().getEntity(QuestEntityHolder.class).getQuests()) {
            CollectionUtils.addToMapOfList(quests, questEntity.getStartNpcFullId(), MapperManager.getXMLMapper(QuestMapper.class).map(questEntity));
        }
        XMLService.getInstance().clearEntity(QuestEntityHolder.class);

        log.info("QuestService started");
    }

    @Override
    public void onDestroy() {
        log.info("QuestService stopped");
    }

    public void onPlayerCreate(final Player player) {
        final QuestList questList = new QuestList();

        player.setQuestList(questList);
    }
    
    public void onPlayerConnect(final Player player) {
        for (final QuestData questData : player.getQuestList().getQuestDatas()) {
            player.getConnection().sendPacket(new SM_QUEST_INFO(questData, new FastList<Npc>(), false));
        }
    }
    
    public void onPlayerMove(final Player player) {
        
    }

    public void onPlayerStartQuest(final Player player, final Npc npc, final Quest quest) {
        final QuestData questData = player.getQuestList().addQuest(quest);

        this.updatePlayerQuestNpcVillageInfo(player);

        final List<TeraServerPacket> packets = new FastList<>();
        packets.add(new SM_OPCODE_LESS_PACKET("3EE515050000D3A0860401"));
        packets.add(new SM_QUEST_INFO(questData, new FastList<Npc>(), true));
        packets.add(new SM_QUEST_BALLOON(npc, quest));
        packets.add(SystemMessages.QUEST_STARTED(String.valueOf(quest.getQuestFullId())));
        
        player.getConnection().sendPackets(packets);
    }
    
    public void onPlayerCancelQuest(final Player player, final Quest quest) {
        player.getQuestList().removeQuest(quest);
        this.updatePlayerQuestNpcVillageInfo(player);
    }

    public void updatePlayerQuestNpcVillageInfo(final Player player) {
        final List<TeraServerPacket> packets = new FastList<>();
        packets.add(new SM_QUEST_WORLD_VILLAGER_INFO_CLEAR());

        for (final VisibleTeraObject visibleNpc : player.getKnownList().getVisible(VisibleTypeEnum.NPC)) {
            final Npc npc = (Npc) visibleNpc;
            
            boolean hasQuest = false;
            
            // new quest
            final List<Quest> quests = this.getQuestsByCreature(npc);
            if (quests != null && !quests.isEmpty()) {
                // TODO conditions (level, required quests ...)
                for (final Quest quest : quests) {
                    if (!player.getQuestList().hasQuest(quest)) {
                        QuestNpcIconEnum questNpcIconEnum = null;
                        switch (quest.getQuestType()) {
                            default:
                                questNpcIconEnum = QuestNpcIconEnum.PROCESS_GUILD;
                        }
                        hasQuest = true;
                        packets.add(new SM_QUEST_VILLAGER_INFO(npc, questNpcIconEnum));
                    }
                }
            }
            
            // Player quest
            for (final QuestData questData : player.getQuestList().getQuestDatas()) {
                final Quest quest = questData.getQuest();
                if (quest.getSteps().get(questData.getStep()-1).getValue() == npc.getFullId()) {
                    QuestNpcIconEnum questNpcIconEnum = null;
                    switch (quest.getQuestType()) {
                        default:
                            questNpcIconEnum = QuestNpcIconEnum.PROCESS_GUILD;
                    }
                    hasQuest = true;
                    packets.add(new SM_QUEST_VILLAGER_INFO(npc, questNpcIconEnum));
                }
            }
            
            if (hasQuest) {
                packets.add(new SM_QUEST_VILLAGER_INFO(npc, QuestNpcIconEnum.NONE));
            }
        }
        
        player.getConnection().sendPackets(packets);
    }
    
    public void addQuestDialogButtons(final Npc npc, final Player player, final Dialog dialog) {
        final List<Quest> quests = QuestService.getInstance().getQuestsByCreature(npc);
        
        // Start quest
        if (quests != null && !quests.isEmpty()) {
            for (final Quest quest : quests) {
                if (player.getQuestList().hasQuest(quest)) {
                    continue;
                }

                DialogIconEnum dialogIcon = null;
                switch (quest.getQuestType()) {
                    case NORMAL:
                    case STORY:
                    case REPEATABLE:
                        dialogIcon = DialogIconEnum.NORMAL_QUEST_START;
                    break;
                }

                dialog.addDialogButton(new DialogButton(dialog, dialogIcon, "@quest:"+(quest.getQuestFullId()), new ReadQuestDialogAction(player, dialog, quest)));
            }
        }
        
        // Update quest
        // TODO
    }

    public final List<Quest> getQuestsByCreature(final Npc npc) {
        return quests.get(npc.getFullId());
    }
    
    public final Quest getQuestById(final int questId) {
        for (final List<Quest> quests : this.quests.values()) {
            for (final Quest quest : quests) {
                if (quest.getId() == questId) {
                    return quest;
                }
            }
        }
        return null;
    }

    /** SINGLETON */
    public static QuestService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final QuestService instance = new QuestService();
    }
}
