package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.gather.Gather;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_GATHER_START extends TeraServerPacket {

    private final Player player;
    private final Gather gather;
    
    public SM_GATHER_START(final Player player, final Gather gather) {
        this.player = player;
        this.gather = gather;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.player);
        writeUid(byteBuffer, this.gather);
    }
}
