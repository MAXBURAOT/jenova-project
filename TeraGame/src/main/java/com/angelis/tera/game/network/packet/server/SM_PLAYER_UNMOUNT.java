package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.mount.Mount;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_PLAYER_UNMOUNT extends TeraServerPacket {

    private final Player player;
    private final Mount mount;
    
    public SM_PLAYER_UNMOUNT(final Player player, final Mount mount) {
        this.player = player;
        this.mount = mount;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.player);
        writeD(byteBuffer, this.mount.getId() + 111110);
    }

}
