package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.visible.WorldPosition;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_LOAD_TOPO extends TeraServerPacket {

    private final Player player;
    
    public SM_LOAD_TOPO(final Player player) {
        super();
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final WorldPosition worldPosition = this.player.getWorldPosition();
        
        writeD(byteBuffer, worldPosition.getMapId());
        writeF(byteBuffer, worldPosition.getX());
        writeF(byteBuffer, worldPosition.getY());
        writeF(byteBuffer, worldPosition.getZ());
        writeC(byteBuffer, 0); //NOT Heading
    }

}
