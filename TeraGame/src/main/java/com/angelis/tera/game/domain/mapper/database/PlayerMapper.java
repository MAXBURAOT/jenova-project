package com.angelis.tera.game.domain.mapper.database;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javolution.util.FastMap;
import javolution.util.FastSet;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.domain.mapper.database.DatabaseMapper;
import com.angelis.tera.common.entity.AbstractEntity;
import com.angelis.tera.common.model.AbstractModel;
import com.angelis.tera.game.database.entity.AccountEntity;
import com.angelis.tera.game.database.entity.CraftEntity;
import com.angelis.tera.game.database.entity.FriendEntity;
import com.angelis.tera.game.database.entity.GatherEntity;
import com.angelis.tera.game.database.entity.PlayerEntity;
import com.angelis.tera.game.database.entity.QuestEntity;
import com.angelis.tera.game.database.entity.SkillEntity;
import com.angelis.tera.game.database.entity.StorageEntity;
import com.angelis.tera.game.models.account.Account;
import com.angelis.tera.game.models.creature.CreatureCurrentStats;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.PlayerAppearance;
import com.angelis.tera.game.models.player.PlayerRelation;
import com.angelis.tera.game.models.player.SkillList;
import com.angelis.tera.game.models.player.craft.CraftStatInfo;
import com.angelis.tera.game.models.player.craft.CraftStats;
import com.angelis.tera.game.models.player.gather.GatherStatInfo;
import com.angelis.tera.game.models.player.gather.GatherStats;
import com.angelis.tera.game.models.player.quest.QuestList;
import com.angelis.tera.game.models.quest.QuestData;
import com.angelis.tera.game.models.skill.Skill;
import com.angelis.tera.game.models.storage.Storage;
import com.angelis.tera.game.models.visible.WorldPosition;
import com.angelis.tera.game.services.QuestService;

public class PlayerMapper extends DatabaseMapper<PlayerEntity, Player> {
    
    @Override
    public PlayerEntity map(final Player model, final AbstractEntity linkedEntity) {
        final PlayerEntity playerEntity = new PlayerEntity(model.getId());

        // DIRECT
        playerEntity.setName(model.getName());
        playerEntity.setGender(model.getGender());
        playerEntity.setRace(model.getRace());
        playerEntity.setPlayerClass(model.getPlayerClass());
        playerEntity.setLevel(model.getLevel());
        playerEntity.setExperience(model.getExperience());
        playerEntity.setCurrentRestedExperience(model.getCurrentRestedExperience());
        playerEntity.setMaxRestedExperience(model.getMaxRestedExperience());
        playerEntity.setMoney(model.getMoney());
        playerEntity.setCreationTime(model.getCreationTime());
        playerEntity.setDeletionTime(model.getDeletionTime());
        playerEntity.setLastOnlineTime(model.getLastOnlineTime());
        playerEntity.setOnline(model.isOnline());
        playerEntity.setDescription(model.getDescription());
        playerEntity.setUserSettings(model.getUserSettings());
        playerEntity.setTitle(model.getTitle());
        playerEntity.setPlayerMode(model.getPlayerMode());
        playerEntity.setZoneData(model.getZoneData());
        
        // GUILD
        playerEntity.setGuild(MapperManager.getDatabaseMapper(GuildMapper.class).map(model.getGuild()));
        
        // STORAGE
        final Set<StorageEntity> storages = new FastSet<>();
        for (final Storage storage: model.getStorages()) {
            storages.add(MapperManager.getDatabaseMapper(StorageMapper.class).map(storage, playerEntity));
        }
        playerEntity.setStorages(storages);
        
        // FRIENDS
        final Set<FriendEntity> friends = new FastSet<>();
        for (final PlayerRelation relation : model.getFriends()) {
            if (linkedEntity instanceof PlayerEntity && MapperManager.getDatabaseMapper(PlayerRelationMapper.class).equals(relation, (FriendEntity) linkedEntity)) {
                friends.add((FriendEntity) linkedEntity);
            } else {
                friends.add(MapperManager.getDatabaseMapper(PlayerRelationMapper.class).map(relation, playerEntity));
            }
        }
        //playerEntity.setFriends(playerEntity);
        
        // BLOCKEDS
        final Set<FriendEntity> blockeds = new FastSet<>();
        for (final PlayerRelation relation : model.getBlockeds()) {
            if (linkedEntity instanceof PlayerEntity && MapperManager.getDatabaseMapper(PlayerRelationMapper.class).equals(relation, (FriendEntity) linkedEntity)) {
                blockeds.add((FriendEntity) linkedEntity);
            } else {
                blockeds.add(MapperManager.getDatabaseMapper(PlayerRelationMapper.class).map(relation, playerEntity));
            }
        }
        //playerEntity.setBlockeds(blockeds);
        
        // CREATURE STATS
        final CreatureCurrentStats currentStats = model.getCreatureCurrentStats();
        playerEntity.setHp(currentStats.getHp());
        playerEntity.setMp(currentStats.getMp());
        playerEntity.setStamina(currentStats.getStamina());
        
        // WORLD POSITION
        final WorldPosition worldPosition = model.getWorldPosition();
        playerEntity.setMapId(worldPosition.getMapId());
        playerEntity.setX(worldPosition.getX());
        playerEntity.setY(worldPosition.getY());
        playerEntity.setZ(worldPosition.getZ());
        playerEntity.setHeading(worldPosition.getHeading());
        
        // PLAYER APPEARANCE
        final PlayerAppearance playerAppearance = model.getPlayerAppearance();
        playerEntity.setData(playerAppearance.getData());
        playerEntity.setDetails1(playerAppearance.getDetails1());
        playerEntity.setDetails2(playerAppearance.getDetails2());
        
        // CRAFT
        final Set<CraftEntity> crafts = new FastSet<>();
        for (final CraftStatInfo craftStatInfo : model.getCraftStats().getCraftStatInfos()) {
            final CraftEntity craftEntity = new CraftEntity(craftStatInfo.getId());
            craftEntity.setCraftType(craftStatInfo.getCraftType());
            craftEntity.setLevel(craftStatInfo.getLevel());
            craftEntity.setPlayer(playerEntity);
            crafts.add(craftEntity);
        }
        playerEntity.setCrafts(crafts);
        
        // GATHER
        final Set<GatherEntity> gathers = new FastSet<>();
        for (final GatherStatInfo gatherStatInfo : model.getGatherStats().getGatherStatInfos()) {
            final GatherEntity gatherEntity = new GatherEntity(gatherStatInfo.getId());
            gatherEntity.setGatherType(gatherStatInfo.getGatherType());
            gatherEntity.setLevel(gatherStatInfo.getLevel());
            gatherEntity.setPlayer(playerEntity);
            gathers.add(gatherEntity);
        }
        playerEntity.setGathers(gathers);
        
        // SKILLLIST
        final Set<SkillEntity> skills = new FastSet<>();
        for (final Entry<Skill, Integer> entry : model.getSkillList().getSkillLevels().entrySet()) {
            final SkillEntity skillEntity = new SkillEntity(entry.getKey().getId());
            skillEntity.setSkillId(entry.getKey().getSkillId());
            skillEntity.setLevel(entry.getValue());
            skillEntity.setPlayer(playerEntity);
            skills.add(skillEntity);
        }
        playerEntity.setSkills(skills);
        
        // QUESTLIST
        final Set<QuestEntity> quests = new FastSet<>();
        for (final QuestData questData : model.getQuestList().getQuestDatas()) {
            final QuestEntity questEntity = new QuestEntity(questData.getQuest().getId());
            questEntity.setStep(questData.getStep());
            quests.add(questEntity);
        }
        playerEntity.setQuests(quests);

        // ACCOUNT
        if (linkedEntity instanceof AccountEntity) {
            playerEntity.setAccount((AccountEntity) linkedEntity);
        } else {
            playerEntity.setAccount(MapperManager.getDatabaseMapper(AccountMapper.class).map(model.getAccount(), playerEntity));
        }
        
        return playerEntity;
    }
    
    @Override
    public Player map(final PlayerEntity entity, final AbstractModel linkedModel) {
        final Player player = new Player(entity.getId());
        
        // DIRECT
        player.setName(entity.getName());
        player.setGender(entity.getGender());
        player.setRace(entity.getRace());
        player.setPlayerClass(entity.getPlayerClass());
        player.setLevel(entity.getLevel());
        player.setExperience(entity.getExperience());
        player.setCurrentRestedExperience(entity.getCurrentRestedExperience());
        player.setMaxRestedExperience(entity.getMaxRestedExperience());
        player.setMoney(entity.getMoney());
        player.setCreationTime(entity.getCreationTime());
        player.setDeletionTime(entity.getDeletionTime());
        player.setLastOnlineTime(entity.getLastOnlineTime());
        player.setOnline(entity.isOnline());
        player.setDescription(entity.getDescription());
        player.setUserSettings(entity.getUserSettings());
        player.setTitle(entity.getTitle());
        player.setPlayerMode(entity.getPlayerMode());
        player.setZoneData(entity.getZoneData());

        // GUILD
        player.setGuild(MapperManager.getDatabaseMapper(GuildMapper.class).map(entity.getGuild()));
        
        // STORAGE
        final Set<Storage> storages = new FastSet<>();
        for (final StorageEntity storage: entity.getStorages()) {
            storages.add(MapperManager.getDatabaseMapper(StorageMapper.class).map(storage));
        }
        player.setStorages(storages);
        
        // CREATURE STATS
        final CreatureCurrentStats currentStats = new CreatureCurrentStats();
        currentStats.setHp(entity.getHp());
        currentStats.setMp(entity.getMp());
        currentStats.setStamina(entity.getStamina());
        player.setCreatureCurrentStats(currentStats);
        
        // WORLD POSITION
        final WorldPosition worldPosition = new WorldPosition();
        worldPosition.setMapId(entity.getMapId());
        worldPosition.setX(entity.getX());
        worldPosition.setY(entity.getY());
        worldPosition.setZ(entity.getZ());
        worldPosition.setHeading(entity.getHeading());
        player.setWorldPosition(worldPosition);
        
        // PLAYER APPEARANCE
        final PlayerAppearance playerAppearance = new PlayerAppearance();
        playerAppearance.setData(entity.getData());
        playerAppearance.setDetails1(entity.getDetails1());
        playerAppearance.setDetails2(entity.getDetails2());
        player.setPlayerAppearance(playerAppearance);
        
        // CRAFT
        final Set<CraftStatInfo> craftStatInfos = new FastSet<>();
        for (final CraftEntity craftEntity : entity.getCrafts()) {
            final CraftStatInfo craftStatInfo = new CraftStatInfo(craftEntity.getId());
            craftStatInfo.setCraftType(craftEntity.getCraftType());
            craftStatInfo.setLevel(craftEntity.getLevel());
            craftStatInfos.add(craftStatInfo);
        }
        player.setCraftStats(new CraftStats(craftStatInfos));
        
        // GATHER
        final Set<GatherStatInfo> gatherStatInfos = new FastSet<>();
        for (final GatherEntity gatherEntity : entity.getGathers()) {
            final GatherStatInfo gatherStatInfo = new GatherStatInfo(gatherEntity.getId());
            gatherStatInfo.setGatherType(gatherEntity.getGatherType());
            gatherStatInfo.setLevel(gatherEntity.getLevel());
            gatherStatInfos.add(gatherStatInfo);
        }
        player.setGatherStats(new GatherStats(gatherStatInfos));
        
        // SKILLLIST
        final Map<Skill, Integer> skillLevels = new FastMap<>();
        for (final SkillEntity skillEntity : entity.getSkills()) {
            final Skill skill = new Skill(skillEntity.getId());
            skill.setSkillId(skillEntity.getSkillId());
            skillLevels.put(skill, skillEntity.getLevel());
        }
        player.setSkillList(new SkillList(skillLevels));
        
        // QUESTLIST
        final Set<QuestData> quests = new FastSet<>();
        for (final QuestEntity questEntity : entity.getQuests()) {
            final QuestData questData = new QuestData(QuestService.getInstance().getQuestById(questEntity.getQuestId()));
            questData.setStep(questData.getStep());
            quests.add(questData);
        }
        player.setQuestList(new QuestList(quests));
        
        // ACCOUNT
        if (linkedModel instanceof Account) {
            player.setAccount((Account) linkedModel);
        } else {
            player.setAccount(MapperManager.getDatabaseMapper(AccountMapper.class).map(entity.getAccount()));
        }
        
        return player;
    }

    @Override
    public boolean equals(final Player model, final PlayerEntity entity) {
        return model.getName().equals(entity.getName());
    }
}
