package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.account.enums.AccountTypeEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_CHARACTER_CREATE_ALLOWED;

/**
 * 
 * @author Angelis
 * 
 * Client ask server if he can create moer characters
 */
public class CM_CHARACTER_CREATE_ALLOWED extends TeraClientPacket {

    public CM_CHARACTER_CREATE_ALLOWED(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // empty
    }

    @Override
    protected void runImpl() {
        final AccountTypeEnum accountType = this.getConnection().getAccount().getAccountType();
        final int playerCount = this.getConnection().getAccount().getPlayers().size();
        this.getConnection().sendPacket(new SM_CHARACTER_CREATE_ALLOWED(playerCount < accountType.maxPlayerCount));
    }
}
