package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_VISITED_SECTION_LIST;

public class CM_UPDATE_CONTENTS_PLAYTIME extends TeraClientPacket {

    public CM_UPDATE_CONTENTS_PLAYTIME(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readB(20);
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_VISITED_SECTION_LIST());
    }

}
