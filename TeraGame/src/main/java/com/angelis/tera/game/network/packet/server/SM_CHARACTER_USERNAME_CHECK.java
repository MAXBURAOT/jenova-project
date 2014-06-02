package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_CHARACTER_USERNAME_CHECK extends TeraServerPacket {

    private final boolean nameFree;
    
    public SM_CHARACTER_USERNAME_CHECK(final boolean nameNotUsed) {
        this.nameFree = nameNotUsed;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeC(byteBuffer, this.nameFree);
    }
}
