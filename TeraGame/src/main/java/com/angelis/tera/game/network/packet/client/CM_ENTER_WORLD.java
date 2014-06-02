package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.SystemMessages;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_AVAILABLE_SOCIAL_LIST;
import com.angelis.tera.game.network.packet.server.SM_CRAFT_RECIPE_LIST;
import com.angelis.tera.game.network.packet.server.SM_CRAFT_STATS;
import com.angelis.tera.game.network.packet.server.SM_CURRENT_ELECTION_STATE;
import com.angelis.tera.game.network.packet.server.SM_ENTER_WORLD;
import com.angelis.tera.game.network.packet.server.SM_F2P_PREMIUM_USER_PERMISSION;
import com.angelis.tera.game.network.packet.server.SM_FESTIVAL_LIST;
import com.angelis.tera.game.network.packet.server.SM_INVENTORY;
import com.angelis.tera.game.network.packet.server.SM_LOAD_HINT;
import com.angelis.tera.game.network.packet.server.SM_LOAD_TOPO;
import com.angelis.tera.game.network.packet.server.SM_LOGIN;
import com.angelis.tera.game.network.packet.server.SM_MASSTIGE_STATUS;
import com.angelis.tera.game.network.packet.server.SM_MISSION_COMPLETE_INFO;
import com.angelis.tera.game.network.packet.server.SM_MOVE_DISTANCE_DELTA;
import com.angelis.tera.game.network.packet.server.SM_NPC_GUILD_LIST;
import com.angelis.tera.game.network.packet.server.SM_OPCODE_LESS_PACKET;
import com.angelis.tera.game.network.packet.server.SM_PET_INCUBATOR_INFO_CHANGE;
import com.angelis.tera.game.network.packet.server.SM_PET_INFO_CLEAR;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_APPEARANCE_CHANGE;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_DESCRIPTION;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_EQUIP_ITEM_CHANGER;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_SKILL_LIST;
import com.angelis.tera.game.network.packet.server.SM_QUEST_CLEAR_INFO;
import com.angelis.tera.game.network.packet.server.SM_QUEST_DAILY_COMPLETE_COUNT;
import com.angelis.tera.game.network.packet.server.SM_VIRTUAL_LATENCY;
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
        final TeraGameConnection connection = this.getConnection();
        final Player player = connection.getAccount().getPlayerById(this.playerId);

        if (player == null) {
            return;
        }

        PlayerService.getInstance().registerPlayer(connection, player);

        connection.sendPacket(new SM_ENTER_WORLD());

        try {
            Thread.sleep(3000);
        }
        catch (final InterruptedException e) {
            e.printStackTrace();
        }

        connection.sendPacket(new SM_CURRENT_ELECTION_STATE());

        connection.sendPacket(new SM_LOGIN(player));

        connection.sendPacket(new SM_INVENTORY(player, false));
        connection.sendPacket(new SM_PLAYER_SKILL_LIST(player));
        connection.sendPacket(new SM_AVAILABLE_SOCIAL_LIST());
        connection.sendPacket(new SM_QUEST_CLEAR_INFO());
        connection.sendPacket(new SM_QUEST_DAILY_COMPLETE_COUNT(player));
        connection.sendPacket(new SM_MISSION_COMPLETE_INFO());
        connection.sendPacket(new SM_CRAFT_STATS(player.getCraftStats()));
        connection.sendPacket(new SM_CRAFT_RECIPE_LIST(player));
        connection.sendPacket(new SM_NPC_GUILD_LIST());

        connection.sendPacket(new SM_PET_INCUBATOR_INFO_CHANGE());
        connection.sendPacket(new SM_PET_INFO_CLEAR());
        connection.sendPacket(new SM_VIRTUAL_LATENCY());
        connection.sendPacket(new SM_MOVE_DISTANCE_DELTA());
        connection.sendPacket(new SM_PLAYER_DESCRIPTION(player));
        connection.sendPacket(new SM_F2P_PREMIUM_USER_PERMISSION());
        connection.sendPacket(new SM_OPCODE_LESS_PACKET("F07B0000000000000000   "));

        connection.sendPacket(SystemMessages.YOU_CAN_JOIN_GUILD_VIA_SOCIAL_LINK());

        connection.sendPacket(new SM_MASSTIGE_STATUS());
        connection.sendPacket(new SM_PLAYER_APPEARANCE_CHANGE(player));
        connection.sendPacket(new SM_PLAYER_EQUIP_ITEM_CHANGER());
        connection.sendPacket(new SM_FESTIVAL_LIST());

        connection.sendPacket(new SM_LOAD_TOPO(player));

        connection.sendPacket(new SM_LOAD_HINT());
        
        connection.sendPacket(new SM_OPCODE_LESS_PACKET("22CE010009000009000000B20100009E2D7B2C00000000FFFFFF7F0000000000000000"));
        connection.sendPacket(new SM_OPCODE_LESS_PACKET("CEA00000000061D2845300000000"));

        connection.sendPacket(new SM_INVENTORY(player, false));
    }
}
