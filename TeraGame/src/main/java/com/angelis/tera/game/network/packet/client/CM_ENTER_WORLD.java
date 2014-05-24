package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.SystemMessages;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_CURRENT_ELECTION_STATE;
import com.angelis.tera.game.network.packet.server.SM_ENTER_WORLD;
import com.angelis.tera.game.network.packet.server.SM_OPCODE_LESS_PACKET;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_BIND;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_CRAFT_STATS;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_DESCRIPTION;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_INIT;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_SKILL_LIST;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_STORAGE;
import com.angelis.tera.game.services.PlayerService;

public class CM_ENTER_WORLD extends TeraClientPacket {

    private int playerId;
    private boolean withProlog;

    public CM_ENTER_WORLD(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.playerId = readD();
        this.withProlog = readC() == 1;
    }

    @Override
    protected void runImpl() {
        final Player player = this.getConnection().getAccount().getPlayerById(this.playerId);

        if (player == null) {
            return;
        }

        PlayerService.getInstance().registerPlayer(this.getConnection(), player);

        this.getConnection().sendPacket(new SM_ENTER_WORLD());

        try {
            Thread.sleep(3000);
        }
        catch (final InterruptedException e) {
            e.printStackTrace();
        }

        this.getConnection().sendPacket(new SM_CURRENT_ELECTION_STATE());

        this.getConnection().sendPacket(new SM_PLAYER_INIT(player));

        this.getConnection().sendPacket(new SM_PLAYER_STORAGE(player, false));
        this.getConnection().sendPacket(new SM_PLAYER_SKILL_LIST(player));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("8C8600000000"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("0EC0"));

        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("937100000A0000"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("E676040008000800100015050000100018001705000018002000180500002000000019050000"));
        this.getConnection().sendPacket(new SM_PLAYER_CRAFT_STATS(player.getCraftStats()));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("3FF8000000000001"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("CE6400000000923A0E0000800000"));

        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("1FF60500160001000000000000003C000000000016002200000000000000000022002E00FFFFFFFF000000002E003A00FFFFFFFF000000003A004600FFFFFFFF0000000046000000FFFFFFFF00000000"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("77E2"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("C7BE0000000000000000"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("027700004843"));
        this.getConnection().sendPacket(new SM_PLAYER_DESCRIPTION(player));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("67BB01001400050000000000803F0000803F1400000001000000"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("C5A10000000000000000"));

        this.getConnection().sendPacket(SystemMessages.YOU_CAN_JOIN_GUILD_VIA_SOCIAL_LINK());

        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("A6E7000000000000000000000000000000000000000000000000"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("3FB2923A0E00008000007B3000002845000029450000334500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("CC6E0A001000923A0E000080000010001C0001000000000000001C002800030000000000000028003400040000000000000034004000050000000000000040004C0006000000000000004C005800070000000000000058006400080000000000000064007000090000000000000070007C000A000000000000007C0000000B00000000000000"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("8BB300000000"));

        this.getConnection().sendPacket(new SM_PLAYER_BIND(player));

        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("129900000000"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("7656010009000009000000B2010000937DBA2C00000000FFFFFF7F0000000000000000"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("107E8F9400006C82455300000000"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("43B400000000"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("4D9C0000000000000000"));
        this.getConnection().sendPacket(new SM_OPCODE_LESS_PACKET("D65700000000923A0E0000800000"));

        this.getConnection().sendPacket(new SM_PLAYER_STORAGE(player, false));
    }
}
