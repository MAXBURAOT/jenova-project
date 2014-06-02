package com.angelis.tera.game.models.player;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javolution.util.FastList;

import com.angelis.tera.game.controllers.PlayerController;
import com.angelis.tera.game.models.account.Account;
import com.angelis.tera.game.models.campfire.CampFire;
import com.angelis.tera.game.models.channel.Channel;
import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.enums.StorageTypeEnum;
import com.angelis.tera.game.models.group.Group;
import com.angelis.tera.game.models.guild.Guild;
import com.angelis.tera.game.models.mount.Mount;
import com.angelis.tera.game.models.player.craft.CraftStats;
import com.angelis.tera.game.models.player.enums.GenderEnum;
import com.angelis.tera.game.models.player.enums.PlayerClassEnum;
import com.angelis.tera.game.models.player.enums.PlayerModeEnum;
import com.angelis.tera.game.models.player.enums.RaceEnum;
import com.angelis.tera.game.models.player.gather.GatherStats;
import com.angelis.tera.game.models.player.quest.QuestList;
import com.angelis.tera.game.models.storage.Storage;
import com.angelis.tera.game.models.template.Template;
import com.angelis.tera.game.network.connection.TeraGameConnection;

public class Player extends Creature {

    private String name;
    private GenderEnum gender;
    private RaceEnum race;
    private PlayerClassEnum playerClass;
    private long experience;
    private int currentRestedExperience;
    private int maxRestedExperience;
    private int money;
    private Date creationTime;
    private Date deletionTime;
    private Date lastOnlineTime;
    private boolean online;
    private String description;
    private byte[] userSettings;
    private int title;
    private PlayerModeEnum playerMode;
    private PlayerAppearance playerAppearance;
    private Account account;
    private Guild guild;
    private Set<Storage> storages;
    private CraftStats craftStats;
    private GatherStats gatherStats;
    private List<PlayerRelation> friends;
    private List<PlayerRelation> blockeds;
    private SkillList skillList;
    private QuestList questList;
    private byte[] zoneData;

    private Group group;
    private Channel channel;
    private CampFire activeCampFire;
    private Mount activeMount;
    private Player activeDuelPlayer;

    public Player(final Integer abstractId) {
        super(abstractId, new PlayerController());
        this.getController().setOwner(this);
    }
    
    public Player() {
        this(null);
    }
    
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(final GenderEnum gender) {
        this.gender = gender;
    }

    public RaceEnum getRace() {
        return race;
    }

    public void setRace(final RaceEnum race) {
        this.race = race;
    }

    public PlayerClassEnum getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(final PlayerClassEnum playerClass) {
        this.playerClass = playerClass;
    }

    public long getExperience() {
        return experience;
    }
    
    public void setCurrentRestedExperience(final int currentRestedExperience) {
        this.currentRestedExperience = currentRestedExperience;
    }
    
    public int getCurrentRestedExperience() {
        return currentRestedExperience;
    }
    
    public void setMaxRestedExperience(final int maxRestedExperience) {
       this.maxRestedExperience = maxRestedExperience;
    }
    
    public int getMaxRestedExperience() {
        return maxRestedExperience;
    }

    public void setExperience(final long experience) {
        this.experience = experience;
    }
    
    public void addExperience(final int experience) {
        this.experience += experience;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(final int money) {
        this.money = money;
    }
    
    public void addMoney(final int money) {
        this.money += money;
    }
    
    public void removeMoney(final int money) {
        this.money -= money;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(final Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(final Date deletionTime) {
        this.deletionTime = deletionTime;
    }

    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(final Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(final boolean online) {
        this.online = online;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public byte[] getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(final byte[] userSettings) {
        this.userSettings = userSettings;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(final int title) {
        this.title = title;
    }

    public PlayerModeEnum getPlayerMode() {
        return playerMode;
    }

    public void setPlayerMode(final PlayerModeEnum playerMode) {
        this.playerMode = playerMode;
    }

    public PlayerAppearance getPlayerAppearance() {
        return playerAppearance;
    }

    public void setPlayerAppearance(final PlayerAppearance playerAppearance) {
        this.playerAppearance = playerAppearance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(final Guild guild) {
        this.guild = guild;
    }

    public Set<Storage> getStorages() {
        return storages;
    }

    public void setStorages(final Set<Storage> storages) {
        this.storages = storages;
    }

    public CraftStats getCraftStats() {
        return craftStats;
    }

    public void setCraftStats(final CraftStats craftStats) {
        this.craftStats = craftStats;
    }

    public GatherStats getGatherStats() {
        return gatherStats;
    }

    public void setGatherStats(final GatherStats gatherStats) {
        this.gatherStats = gatherStats;
    }

    public List<PlayerRelation> getFriends() {
        if (friends == null) {
            friends = new FastList<>();
        }
        return friends;
    }
    public PlayerRelation getFriendById(final Integer playerId) {
        PlayerRelation relation = null;
        for (final PlayerRelation playerRelation : this.friends) {
            if (playerRelation.getPlayer().getId() == playerId) {
                relation = playerRelation;
                break;
            }
        }
        
        return relation;
    }

    public void setFriends(final List<PlayerRelation> friends) {
        this.friends = friends;
    }

    public List<PlayerRelation> getBlockeds() {
        if (blockeds == null) {
            blockeds = new FastList<>();
        }
        return blockeds;
    }
    public PlayerRelation getBlockedById(final Integer playerId) {
        PlayerRelation relation = null;
        for (final PlayerRelation playerRelation : this.blockeds) {
            if (playerRelation.getPlayer().getId() == playerId) {
                relation = playerRelation;
                break;
            }
        }
        
        return relation;
    }

    public void setBlockeds(final List<PlayerRelation> blockeds) {
        this.blockeds = blockeds;
    }
    
    public SkillList getSkillList() {
        return skillList;
    }

    public void setSkillList(final SkillList skillList) {
        this.skillList = skillList;
    }

    public byte[] getZoneData() {
        return this.zoneData;
    }
    
    public void setZoneData(final byte[] zoneData) {
        this.zoneData = zoneData;
    }
    
    public QuestList getQuestList() {
        return questList;
    }
    
    public void setQuestList(final QuestList questList) {
        this.questList = questList;
    }

    @Override
    public PlayerController getController() {
        return (PlayerController) this.controller;
    }

    public Storage getStorage(final StorageTypeEnum storageType) {
        for (final Storage storage : this.getStorages()) {
            if (storage.getStorageType() == storageType) {
                return storage;
            }
        }
        
        return null;
    }
    
    public Group getGroup() {
        return this.group;
    }
    
    public void setGroup(final Group group) {
        this.group = group;
    }
    
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(final Channel channel) {
        this.channel = channel;
    }

    public CampFire getActiveCampFire() {
        return activeCampFire;
    }

    public void setActiveCampFire(final CampFire activeCampFire) {
        this.activeCampFire = activeCampFire;
    }

    public Mount getActiveMount() {
        return activeMount;
    }

    public void setActiveMount(final Mount activeMount) {
        this.activeMount = activeMount;
    }

    public Player getActiveDuelPlayer() {
        return activeDuelPlayer;
    }

    public void setActiveDuelPlayer(final Player activeDuelPlayer) {
        this.activeDuelPlayer = activeDuelPlayer;
    }

    public final TeraGameConnection getConnection() {
        return this.getAccount().getConnection();
    }
    
    @Override
    public final Template getTemplate() {
        return null;
    }
    
    public int getRaceGenderClassValue() {
        return (10101 + 200 * this.race.value + 100 * this.gender.getValue() + this.playerClass.value);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Player)) {
            return false;
        }
        final Player other = (Player) obj;
        if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
