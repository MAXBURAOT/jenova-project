package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.database.entity.PlayerEntity;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_LOOKING_FOR_GROUP_CHAT extends TeraServerPacket {

    private final PlayerEntity player;
    private final String message;
    
    public SM_LOOKING_FOR_GROUP_CHAT(final PlayerEntity player, final String message) {
        this.player = player;
        this.message = message;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeB(byteBuffer, "580009BC0E002000EAFC08000000");
        writeS(byteBuffer, player.getName());
        writeS(byteBuffer, message);
    }
}
