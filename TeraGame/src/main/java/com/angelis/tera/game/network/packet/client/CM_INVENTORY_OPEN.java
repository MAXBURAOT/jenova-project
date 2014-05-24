package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_STORAGE;

public class CM_INVENTORY_OPEN extends TeraClientPacket {

    public CM_INVENTORY_OPEN(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD(); // unk 01000000
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_PLAYER_STORAGE(this.getConnection().getActivePlayer(), true));
    }
}
