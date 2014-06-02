package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.server.SM_OPCODE_LESS_PACKET;

public class SendFakePacketCommand extends AbstractAdminCommand {

    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        
        // 41E50100080008000000E2010000E2C3525300000000
        // success
        connection.sendPacket(new SM_OPCODE_LESS_PACKET("937100000A0000"));
    }

    @Override
    public int getAccessLevel() {
        return 0;
    }

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        return true;
    }

    @Override
    public String getSyntax() {
        return "sendfakepacket [data]";
    }
}
