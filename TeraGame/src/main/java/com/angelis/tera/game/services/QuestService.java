package com.angelis.tera.game.services;

import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.utils.CollectionUtils;
import com.angelis.tera.common.utils.Rnd;
import com.angelis.tera.game.domain.mapper.xml.QuestMapper;
import com.angelis.tera.game.models.creature.Npc;
import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.models.dialog.Dialog;
import com.angelis.tera.game.models.dialog.DialogButton;
import com.angelis.tera.game.models.dialog.actions.quest.QuestAcceptDialogAction;
import com.angelis.tera.game.models.dialog.actions.quest.QuestCompliteDialogAction;
import com.angelis.tera.game.models.dialog.actions.quest.QuestReadDialogAction;
import com.angelis.tera.game.models.dialog.actions.quest.QuestUpdateDialogAction;
import com.angelis.tera.game.models.dialog.enums.DialogIconEnum;
import com.angelis.tera.game.models.drop.Drop;
import com.angelis.tera.game.models.drop.enums.ItemCategoryEnum;
import com.angelis.tera.game.models.enums.StorageTypeEnum;
import com.angelis.tera.game.models.item.Item;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.quest.QuestList;
import com.angelis.tera.game.models.quest.Quest;
import com.angelis.tera.game.models.quest.QuestEnv;
import com.angelis.tera.game.models.quest.QuestReward;
import com.angelis.tera.game.models.quest.QuestStep;
import com.angelis.tera.game.models.quest.QuestStepValue;
import com.angelis.tera.game.models.quest.enums.QuestNpcIconEnum;
import com.angelis.tera.game.models.quest.enums.QuestStepTypeEnum;
import com.angelis.tera.game.models.visible.VisibleTeraObject;
import com.angelis.tera.game.models.visible.enums.VisibleTypeEnum;
import com.angelis.tera.game.network.SystemMessages;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.network.packet.server.SM_OPCODE_LESS_PACKET;
import com.angelis.tera.game.network.packet.server.SM_QUEST_BALLOON;
import com.angelis.tera.game.network.packet.server.SM_QUEST_INFO;
import com.angelis.tera.game.network.packet.server.SM_QUEST_VILLAGER_INFO;
import com.angelis.tera.game.network.packet.server.SM_QUEST_WORLD_VILLAGER_INFO_CLEAR;
import com.angelis.tera.game.utils.QuestUtils;
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
        quests.clear();
        log.info("QuestService stopped");
    }

    public void onPlayerCreate(final Player player) {
        player.setQuestList(new QuestList());
    }

    public void onPlayerConnect(final Player player) {
        for (final QuestEnv questEnv : player.getQuestList().getQuestEnvs()) {
            player.getConnection().sendPacket(new SM_QUEST_INFO(questEnv, new FastList<Npc>(), false));
        }
    }

    public void onPlayerLevelUp(final Player player) {
        for (final List<Quest> quests : this.quests.values()) {
            for (final Quest quest : quests) {
                if (quest.getRequiredLevel() < player.getLevel() && QuestUtils.checkRequirements(player, quest)) {
                    this.onPlayerStartQuest(player, null, quest);
                }
            }
        }
    }
    
    public void onPlayerPickupItem(final Player player, final Item item) {
        for (final QuestEnv questEnv : player.getQuestList().getQuestEnvsByStepType(QuestStepTypeEnum.COLLECT)) {
            final QuestStep currentQuestStep = questEnv.getCurrentQuestStep();
            final int fullId = item.getId();
            if (currentQuestStep.containsObjectId(fullId)) {
                if (currentQuestStep.getRequiredObjectAmountByObjectId(fullId) < questEnv.getCounters().get(fullId)) {
                    questEnv.addCounter(fullId);
                }
            }
        }

        this.checkQuestStates(player);
    }
    
    public void onPlayerCreatureKill(final Player player, final TeraCreature killed) {
        for (final QuestEnv questEnv : player.getQuestList().getQuestEnvsByStepType(QuestStepTypeEnum.KILL)) {
            final QuestStep currentQuestStep = questEnv.getCurrentQuestStep();
            final int fullId = killed.getFullId();
            
            // KILL
            if (currentQuestStep.containsObjectId(fullId)) {
                if (currentQuestStep.getRequiredObjectAmountByObjectId(fullId) < questEnv.getCounters().get(fullId)) {
                    questEnv.addCounter(fullId);
                }
            }
            
            // AUTOLOOT ON KILL
            for (final Drop questDrop : DropService.getInstance().getLootFromCreature(killed, ItemCategoryEnum.QUEST)) {
                if (currentQuestStep.containsObjectId(questDrop.getItemId())) {
                    float chance = questDrop.getDropChance().value;
                    if (chance < 100) {
                        chance = Rnd.get(0, 100);
                    }
                    
                    if (Rnd.chance(chance)) {
                        StorageService.getInstance().addItem(player, player.getStorage(StorageTypeEnum.INVENTORY), questDrop.getItemId(), 1);
                    }
                }
            }
        }

        this.checkQuestStates(player);
    }

    public void onPlayerProgressQuest(final Player player, final QuestEnv questEnv) {
        questEnv.addStep();
        // TODO
    }

    public void onPlayerStartQuest(final Player player, final Npc npc, final Quest quest) {
        final QuestList questList = player.getQuestList();
        if (questList.hasQuest(quest)) {
            return;
        }

        final QuestEnv questEnv = questList.addQuest(quest);

        final List<TeraServerPacket> packets = new FastList<>();
        this.updatePlayerQuestNpcVillageInfo(player, packets);

        packets.add(new SM_OPCODE_LESS_PACKET("3EE515050000D3A0860401"));
        packets.add(new SM_QUEST_INFO(questEnv, new FastList<Npc>(), true));
        packets.add(new SM_QUEST_BALLOON(npc, quest));
        packets.add(SystemMessages.QUEST_STARTED(String.valueOf((quest.getId()*1000)+1)));

        player.getConnection().sendPackets(packets);
        
        // Check if this quest is not already complete :)
        this.checkQuestStates(player);
    }
    
    public void onPlayerCompliteQuest(final Player player, final Quest quest) {
        final QuestEnv questEnv = player.getQuestList().getQuestEnv(quest);
        if (questEnv == null) {
            return;
        }
        
        questEnv.setComplited(true);
        PlayerService.getInstance().onPlayerUpdateExperience(player, null, quest.getExperienceReward());
        StorageService.getInstance().addItem(player, player.getStorage(StorageTypeEnum.INVENTORY), Item.MONEY_ID, quest.getMoneyReward());

        for (final QuestReward questReward : quest.getQuestRewards()) {
            StorageService.getInstance().addItem(player, player.getStorage(StorageTypeEnum.INVENTORY), questReward.getItemId(), questReward.getAmount());
        }
        
        final List<TeraServerPacket> packets = new FastList<>();
        this.updatePlayerQuestNpcVillageInfo(player, packets);
        packets.add(SystemMessages.QUEST_COMPLITED(String.valueOf((quest.getId()*1000)+1)));
        player.getConnection().sendPackets(packets);
    }

    public void onPlayerCancelQuest(final Player player, final Quest quest) {
        player.getQuestList().removeQuest(quest);

        final List<TeraServerPacket> packets = new FastList<>();
        this.updatePlayerQuestNpcVillageInfo(player, packets);
        player.getConnection().sendPackets(packets);
    }
    
    public void onPlayerReadQuestDialog(final Player player, final Npc npc, final Quest quest) {
        if (player.getController().getDialog() == null) {
            return;
        }
        
        final Dialog dialog = new Dialog(player, npc);
        dialog.setQuest(quest);
        dialog.setPage(player.getController().getDialog().getPage()+1);
        
        final QuestEnv questEnv = player.getQuestList().getQuestEnv(quest);
        if (questEnv == null) {
            dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.DEFAULT_QUEST, "@quest:1", new QuestAcceptDialogAction(player, dialog, quest)));
        } else if (!questEnv.isCurrentQuestStepLast()) {
            dialog.addPage();
            dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.DEFAULT_QUEST, "@quest:"+(quest.getFullId()+dialog.getPage()), new QuestUpdateDialogAction(player, dialog, quest)));
            this.onPlayerProgressQuest(player, questEnv);
        } else {
            dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.DEFAULT_QUEST, "@quest:"+(quest.getFullId()+4), new QuestCompliteDialogAction(player, dialog, quest)));
        }

        final List<TeraServerPacket> packets = new FastList<>();
        this.updatePlayerQuestNpcVillageInfo(player, packets);
        player.getConnection().sendPackets(packets);
        DialogService.getInstance().sendDialogToPlayer(player, dialog);
    }

    public void updatePlayerQuestNpcVillageInfo(final Player player, final List<TeraServerPacket> packets, final TeraCreature teraCreature) {
        if (teraCreature instanceof Npc) {
            boolean hasQuest = false;

            final Npc npc = (Npc) teraCreature;
            final List<Quest> quests = this.getQuestsByCreature(npc);
            
            // Player quest
            for (final QuestEnv questEnv : player.getQuestList().getQuestEnvs()) {
                if (questEnv.isComplited()) {
                    continue;
                }

                final Quest quest = questEnv.getQuest();
                if (questEnv.getCurrentQuestStep().containsObjectId(teraCreature.getFullId())) {
                    hasQuest = true;
                    packets.add(new SM_QUEST_VILLAGER_INFO(teraCreature, QuestUtils.getQuestNpcIconEnum(quest, questEnv)));
                }
            }
            
            if (!hasQuest) {
                // new quest
                if (quests != null && !quests.isEmpty()) {
                    for (final Quest quest : quests) {
                        if (!QuestUtils.checkRequirements(player, quest)) {
                            continue;
                        }
    
                        hasQuest = true;
                        packets.add(new SM_QUEST_VILLAGER_INFO(teraCreature, QuestUtils.getQuestNpcIconEnum(quest, null)));
                    }
                }
            }

            if (!hasQuest) {
                packets.add(new SM_QUEST_VILLAGER_INFO(teraCreature, QuestNpcIconEnum.NONE));
            }
        }
    }

    public void addQuestDialogButtons(final Npc npc, final Player player, final Dialog dialog) {
        final List<Quest> quests = QuestService.getInstance().getQuestsByCreature(npc);

        // Start quest
        if (quests != null && !quests.isEmpty()) {
            for (final Quest quest : quests) {
                if (!QuestUtils.checkRequirements(player, quest)) {
                    continue;
                }

                final DialogIconEnum dialogIcon = QuestUtils.getDialogIconEnum(quest, null);
                dialog.addDialogButton(new DialogButton(dialog, dialogIcon, "@quest:" + (quest.getFullId()+1), new QuestReadDialogAction(player, dialog, quest)));
            }
        }

        // Update quest
        for (final QuestEnv questEnv : player.getQuestList().getQuestEnvs()) {
            if (questEnv.isComplited()) {
                continue;
            }

            final QuestStep questStep = questEnv.getCurrentQuestStep();
            if (questStep.getQuestStepType() == QuestStepTypeEnum.TALK) {
                final Quest quest = questEnv.getQuest();
                if (questStep.containsObjectId(npc.getFullId())) {
                    final DialogIconEnum dialogIcon = QuestUtils.getDialogIconEnum(quest, questEnv);
                    dialog.addDialogButton(new DialogButton(dialog, dialogIcon, "@quest:" + (quest.getFullId()+1), new QuestReadDialogAction(player, dialog, quest)));
                }
            }
        }
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

    private void updatePlayerQuestNpcVillageInfo(final Player player, final List<TeraServerPacket> packets) {
        packets.add(new SM_QUEST_WORLD_VILLAGER_INFO_CLEAR());

        for (final VisibleTeraObject visibleNpc : player.getKnownList().getVisible(VisibleTypeEnum.NPC)) {
            final Npc npc = (Npc) visibleNpc;
            updatePlayerQuestNpcVillageInfo(player, packets, npc);
        }
    }
    
    private void checkQuestStates(final Player player) {
        for (final QuestEnv questEnv : player.getQuestList().getQuestEnvs()) {
            if (questEnv.getCurrentQuestStep().getQuestStepType() == QuestStepTypeEnum.COLLECT) {
                for (final QuestStepValue stepValue : questEnv.getCurrentQuestStep().getStepValues()) {
                    if (player.getStorage(StorageTypeEnum.INVENTORY).getStorageItemByItemId(stepValue.getObjectId()).getCount() >= stepValue.getAmount()) {
                        questEnv.setCounter(stepValue.getObjectId(), stepValue.getAmount());
                    }
                }
            }
            
            if (questEnv.hasAllCounters()) {
                this.onPlayerProgressQuest(player, questEnv);
            }
        }
    }

    /** SINGLETON */
    public static QuestService getInstance() {
        return SingletonHolder.instance;
    }
    
    private static class SingletonHolder {
        protected static final QuestService instance = new QuestService();
    }
}
