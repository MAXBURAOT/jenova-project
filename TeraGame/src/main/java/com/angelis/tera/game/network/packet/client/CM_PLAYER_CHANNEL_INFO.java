package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_CHANNEL_INFO;
import com.angelis.tera.game.services.WorldService;

public class CM_PLAYER_CHANNEL_INFO extends TeraClientPacket {

    private int channelId;
    private int mapId;
    
    public CM_PLAYER_CHANNEL_INFO(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.channelId = readD();
        this.mapId = readD();
    }

    @Override
    protected void runImpl() {
        final Player player = this.getConnection().getActivePlayer();
        this.getConnection().sendPacket(new SM_PLAYER_CHANNEL_INFO(this.channelId, this.mapId));
        WorldService.getInstance().sendChannelInformations(player, this.mapId);
    }

}
