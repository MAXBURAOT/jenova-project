package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_PLAYER_DIALOG_CLOSE extends TeraServerPacket {

    private final TeraCreature teraCreature;
    
    public SM_PLAYER_DIALOG_CLOSE(final TeraCreature teraCreature) {
        this.teraCreature = teraCreature;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        this.writeUid(byteBuffer, this.teraCreature);
    }

}
