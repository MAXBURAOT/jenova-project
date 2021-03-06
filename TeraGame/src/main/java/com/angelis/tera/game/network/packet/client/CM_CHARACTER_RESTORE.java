package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_CHARACTER_RESTORE;
import com.angelis.tera.game.services.PlayerService;

public class CM_CHARACTER_RESTORE extends TeraClientPacket {

    private int playerId;
    
    public CM_CHARACTER_RESTORE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.playerId = readD();
    }

    @Override
    protected void runImpl() {
        PlayerService.getInstance().restorePlayer(playerId);
        this.getConnection().sendPacket(new SM_CHARACTER_RESTORE(true));
    }

}
