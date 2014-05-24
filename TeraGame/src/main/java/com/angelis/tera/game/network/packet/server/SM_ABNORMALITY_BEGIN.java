package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.abnormality.Abnormality;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_ABNORMALITY_BEGIN extends TeraServerPacket {

    private final Abnormality abnormality;
    
    
    public SM_ABNORMALITY_BEGIN(final Abnormality abnormality) {
        this.abnormality = abnormality;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.abnormality.getTarget());
        writeUid(byteBuffer, this.abnormality.getCaster());
        
        writeD(byteBuffer, this.abnormality.getId());
        writeD(byteBuffer, this.abnormality.getTimeLeft());
        
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 1);
    }

}
