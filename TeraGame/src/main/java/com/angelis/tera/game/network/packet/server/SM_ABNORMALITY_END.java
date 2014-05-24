package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.abnormality.Abnormality;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_ABNORMALITY_END extends TeraServerPacket {

    private final Abnormality abnormality;
    
    
    public SM_ABNORMALITY_END(final Abnormality abnormality) {
        this.abnormality = abnormality;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.abnormality.getTarget());
        writeD(byteBuffer, this.abnormality.getId());
    }

}
