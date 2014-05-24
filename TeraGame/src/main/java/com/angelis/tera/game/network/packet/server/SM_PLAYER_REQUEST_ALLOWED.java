package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.player.request.enums.RequestTypeEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_PLAYER_REQUEST_ALLOWED extends TeraServerPacket {

    private final RequestTypeEnum requestType;
    
    public SM_PLAYER_REQUEST_ALLOWED(final RequestTypeEnum requestType) {
        this.requestType = requestType;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.requestType.value);
    }

}
