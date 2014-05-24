package com.angelis.tera.game.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.utils.Function;
import com.angelis.tera.game.models.channel.Channel;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.enums.PlayerModeEnum;
import com.angelis.tera.game.network.TeleportLocations;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_CHANNEL_LIST;

public class WorldService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(WorldService.class.getName());

    private final Set<Player> onlinePlayers = new FastSet<Player>();
    private final Map<Integer, Channel> channels = new FastMap<>();

    @Override
    public void onInit() {
        log.info("WorldService started");
    }

    @Override
    public void onDestroy() {
    }

    public void onPlayerConnect(final Player player) {
        this.onlinePlayers.add(player);
        
        Channel channel = this.channels.get(player.getWorldPosition().getMapId());
        if (channel == null) {
            channel = new Channel(1);
            this.channels.put(player.getWorldPosition().getMapId(), channel);
        }
        
        player.setChannel(channel);
        channel.addPlayer(player);
    }

    public void onPlayerDisconnect(final Player player) {
        this.onlinePlayers.remove(player);
        
        final Channel channel = this.channels.get(player.getWorldPosition().getMapId());
        if (channel == null) {
            return;
        }
        
        player.setChannel(null);
        channel.removePlayer(player);
    }
    
    public void onPlayerCreate(final Player player) {
        player.setCreationTime(new Date());
        player.setDeletionTime(null);
        player.setLastOnlineTime(null);
        player.setPlayerMode(PlayerModeEnum.NORMAL);
        player.setZoneData(new byte[] { 1, 0, 0, 0, 2, 0, 0, 0, 7, 0, 0, 0 });
        player.setLevel(1);
        player.setWorldPosition(TeleportLocations.getStartingPoint());
    }

    public Player getOnlinePlayerWithName(final String name) {
        for (final Player player : this.onlinePlayers) {
            if (player.getName().toLowerCase().equals(name.toLowerCase())) {
                return player;
            }
        }

        return null;
    }

    public Set<TeraGameConnection> getAllOnlineConnections() {
        final Set<TeraGameConnection> allConnections = new FastSet<TeraGameConnection>();
        for (final Player player : this.onlinePlayers) {
            allConnections.add(player.getConnection());
        }
        return allConnections;
    }

    public Set<Player> getAllOnlinePlayers() {
        return this.onlinePlayers;
    }

    public List<Player> getPlayersByMap(final int mapId) {
        final List<Player> playersInMap = new FastList<>();

        for (final Player player : this.onlinePlayers) {
            if (player.getWorldPosition().getMapId() == mapId) {
                playersInMap.add(player);
            }
        }

        return playersInMap;
    }

    public List<Player> getPlayersByArea(final int areaId) {
        final List<Player> playersInArea = new FastList<>();

        for (final Player player : this.onlinePlayers) {
            if (this.getAreaByMapId(player.getWorldPosition().getMapId()) == areaId) {
                playersInArea.add(player);
            }
        }

        return playersInArea;
    }

    public int getAreaByMapId(final int mapId) {
        // TODO
        return 0;
    }

    public void doOnAllOnlinePlayer(final Function<Player> method) {
        for (final Player player : this.onlinePlayers) {
            method.call(player);
        }
    }
    
    public void sendChannelInformations(final Player player, final int mapId) {
        // TODO
        final List<Channel> channels = new FastList<>();
        channels.add(new Channel(0));
        channels.add(new Channel(1));
        channels.add(new Channel(2));
        channels.add(new Channel(3));
        channels.add(new Channel(4));
        channels.add(new Channel(5));
        channels.add(new Channel(6));
        channels.add(new Channel(7));
        
        player.getConnection().sendPacket(new SM_PLAYER_CHANNEL_LIST(mapId, channels));
    }

    /** SINGLETON */
    public static WorldService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final WorldService instance = new WorldService();
    }
}
