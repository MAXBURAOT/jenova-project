package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_TRADEBROKER_HIGHEST_ITEM_LEVEL;

public class CM_UNK_ENTER_WORLD extends TeraClientPacket {

    public CM_UNK_ENTER_WORLD(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_TRADEBROKER_HIGHEST_ITEM_LEVEL());
    }

}
