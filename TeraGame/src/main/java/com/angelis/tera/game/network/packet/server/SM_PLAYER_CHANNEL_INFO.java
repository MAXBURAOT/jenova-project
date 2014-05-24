package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_PLAYER_CHANNEL_INFO extends TeraServerPacket {

    private final int channelId;
    private final int mapId;
    
    public SM_PLAYER_CHANNEL_INFO(final int channelId, final int mapId) {
        this.channelId = channelId;
        this.mapId = mapId;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.mapId);
        writeD(byteBuffer, this.channelId);
        writeD(byteBuffer, 0); // TODO maybe population
    }
}
