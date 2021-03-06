package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_NPC_MENU_SELECT extends TeraServerPacket {

    private final int menu;
    
    public SM_NPC_MENU_SELECT(final int menu) {
        this.menu = menu;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.menu);
    }

}
