package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_PLAYER_DONJON_CLEAR_COUNT_LIST extends TeraServerPacket {

    public SM_PLAYER_DONJON_CLEAR_COUNT_LIST(final Player findPlayerByName) {
        
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {

    }

}
