package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;
import java.util.List;

import com.angelis.tera.game.models.pegasus.PegasusFlyPoint;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_PEGASUS_MAP_SHOW extends TeraServerPacket {

    private final List<PegasusFlyPoint> pegasusFlyPoints;

    public SM_PEGASUS_MAP_SHOW(final List<PegasusFlyPoint> pegasusFlyPoints) {
        this.pegasusFlyPoints = pegasusFlyPoints;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, pegasusFlyPoints.size());
        writeH(byteBuffer, 8);
        
        for (int i = 0 ; i < this.pegasusFlyPoints.size() ; i++) {
            final PegasusFlyPoint PegasusFlyPoint = this.pegasusFlyPoints.get(i);
            final short shift = (short) byteBuffer.position();
            writeH(byteBuffer, 0);

            writeD(byteBuffer, PegasusFlyPoint.getId());
            writeD(byteBuffer, PegasusFlyPoint.getCost());
            writeD(byteBuffer, PegasusFlyPoint.getFromNameId());
            writeD(byteBuffer, PegasusFlyPoint.getToNameId());
            writeD(byteBuffer, PegasusFlyPoint.getLevel());

            if (this.pegasusFlyPoints.size() >= i + 1) {
                this.writeBufferPosition(byteBuffer, shift, Size.H);
            }
        }
    }

}
