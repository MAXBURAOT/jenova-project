package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_DIALOG_EVENT extends TeraServerPacket {

    private final TeraCreature teraCreature;
    private final Creature target;
    private final int page;
    
    public SM_DIALOG_EVENT(final TeraCreature teraCreature, final Creature target, final int page) {
        this.teraCreature = teraCreature;
        this.target = target;
        this.page = page;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.teraCreature);
        writeUid(byteBuffer, this.target);

        writeD(byteBuffer, 0);
        writeD(byteBuffer, page); // this can be 0, 1, 2, 3, 4 or 5
        writeD(byteBuffer, 0);
    }
}
