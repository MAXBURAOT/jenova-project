package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_QUEST_DAILY_COMPLETE_COUNT extends TeraServerPacket {

    private final Player player;
    
    public SM_QUEST_DAILY_COMPLETE_COUNT(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        // TODO
        writeH(byteBuffer, 0); // player quest daily complete count
        writeH(byteBuffer, 10);// maybe max count
    }
}
