package com.angelis.tera.game.network.packet;

import java.util.Map;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.network.packet.AbstractClientPacket;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.client.CM_ACCOUNT_AUTH;
import com.angelis.tera.game.network.packet.client.CM_ALLIANCE_INFO;
import com.angelis.tera.game.network.packet.client.CM_BATTLEGROUND_WINDOW_OPEN;
import com.angelis.tera.game.network.packet.client.CM_CANCEL_QUIT_GAME;
import com.angelis.tera.game.network.packet.client.CM_CANCEL_QUIT_TO_CHARACTER_LIST;
import com.angelis.tera.game.network.packet.client.CM_CHANNEL_LIST;
import com.angelis.tera.game.network.packet.client.CM_CHARACTER_CREATE;
import com.angelis.tera.game.network.packet.client.CM_CHARACTER_CREATE_ALLOWED;
import com.angelis.tera.game.network.packet.client.CM_CHARACTER_DELETE;
import com.angelis.tera.game.network.packet.client.CM_CHARACTER_LIST;
import com.angelis.tera.game.network.packet.client.CM_CHARACTER_RESTORE;
import com.angelis.tera.game.network.packet.client.CM_CHAT_INFO;
import com.angelis.tera.game.network.packet.client.CM_CHECK_VERSION;
import com.angelis.tera.game.network.packet.client.CM_DIALOG;
import com.angelis.tera.game.network.packet.client.CM_DIALOG_EVENT;
import com.angelis.tera.game.network.packet.client.CM_ENCHANT_WINDOW_OPEN;
import com.angelis.tera.game.network.packet.client.CM_ENTER_WORLD;
import com.angelis.tera.game.network.packet.client.CM_GLYPH_REINIT;
import com.angelis.tera.game.network.packet.client.CM_GUARD_PK_POLICY;
import com.angelis.tera.game.network.packet.client.CM_HARDWARE_INFO;
import com.angelis.tera.game.network.packet.client.CM_INSTANCERANK_WINDOW_OPEN;
import com.angelis.tera.game.network.packet.client.CM_INVENTORY_ORDER;
import com.angelis.tera.game.network.packet.client.CM_INVENTORY_SHOW;
import com.angelis.tera.game.network.packet.client.CM_ITEM_SIMPLE_INFO;
import com.angelis.tera.game.network.packet.client.CM_ITEM_USE;
import com.angelis.tera.game.network.packet.client.CM_LOAD_TOPO_FIN;
import com.angelis.tera.game.network.packet.client.CM_LOOKING_FOR_BATTELGROUND_WINDOW_OPEN;
import com.angelis.tera.game.network.packet.client.CM_LOOKING_FOR_INSTANCE_WINDOW_OPEN;
import com.angelis.tera.game.network.packet.client.CM_MAP_SHOW;
import com.angelis.tera.game.network.packet.client.CM_MOVIE_END;
import com.angelis.tera.game.network.packet.client.CM_NPC_CONTACT;
import com.angelis.tera.game.network.packet.client.CM_OPTION_SET_VISIBILITY_DISTANCE;
import com.angelis.tera.game.network.packet.client.CM_OPTION_SHOW_MASK;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_BLOCK_ADD;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_BLOCK_NOTE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_BLOCK_REMOVE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_CHAT;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_CLIMB_START;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_COMPARE_ACHIEVEMENTS;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_DESCRIPTION;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_DONJON_CLEAR_COUNT_LIST;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_DONJON_STATS_PVP;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_DROP_ITEM_PICKUP;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_DUNGEON_COOLTIME_LIST;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_EMOTE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_EQUIP;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_EQUIPEMENT_ITEM_INFO;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_FRIEND_ADD;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_FRIEND_LIST;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_FRIEND_NOTE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_FRIEND_REMOVE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_GATHER;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_INSPECT;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_ITEM_MOVE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_ITEM_TRASH;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_MOVE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_REINIT_INSTANCES;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_REPORT;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_SELECT_CREATURE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_SEND_REQUEST;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_SET_TITLE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_UNEQUIP;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_UNMOUNT;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_WHISPER;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_ZONE_CHANGE;
import com.angelis.tera.game.network.packet.client.CM_QUIT_GAME;
import com.angelis.tera.game.network.packet.client.CM_QUIT_TO_CHARACTER_LIST;
import com.angelis.tera.game.network.packet.client.CM_REIGN_INFO;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_ACCOUNT_OBJECT;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_CHARACTER_NAME_CHECK;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_CHARACTER_NAME_CHECK_USED;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_CONTRACT;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_GAMESTAT_PING;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_SYSTEM_INFO;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_UNIQUE_OBJECT;
import com.angelis.tera.game.network.packet.client.CM_SERVERGUILD_WINDOW_OPEN;
import com.angelis.tera.game.network.packet.client.CM_SHOP_WINDOW_OPEN;
import com.angelis.tera.game.network.packet.client.CM_SIMPLE_TIP_REPEATED_CHECK;
import com.angelis.tera.game.network.packet.client.CM_SKILL_CANCEL;
import com.angelis.tera.game.network.packet.client.CM_SKILL_INSTANCE_START;
import com.angelis.tera.game.network.packet.client.CM_SKILL_START;
import com.angelis.tera.game.network.packet.client.CM_UNK_ENTER_WORLD;
import com.angelis.tera.game.network.packet.client.CM_UPDATE_CONTENTS_PLAYTIME;
import com.angelis.tera.game.network.packet.client.CM_USER_SETTINGS_SAVE;
import com.angelis.tera.game.network.packet.client.CM_WELCOME_MESSAGE;

public class ClientPacketHandler {

    /** LOGGER */
    private static Logger log = Logger.getLogger(ClientPacketHandler.class.getName());

    private static Map<Short, Class<? extends AbstractClientPacket<TeraGameConnection>>> clientPackets = new FastMap<Short, Class<? extends AbstractClientPacket<TeraGameConnection>>>();

    public static final void init() {
        clientPackets.clear();

        // AUTH
        addPacket((short) 0x4DBC, CM_CHECK_VERSION.class);
        addPacket((short) 0x6274, CM_ACCOUNT_AUTH.class);
        addPacket((short) 0xAFCF, CM_HARDWARE_INFO.class);

        // REQUEST
        addPacket((short) 0xD781, CM_REQUEST_SYSTEM_INFO.class); // TODO
        addPacket((short) 0x4E70, CM_REQUEST_CHARACTER_NAME_CHECK.class);
        addPacket((short) 0x6A78, CM_REQUEST_CHARACTER_NAME_CHECK_USED.class);
        addPacket((short) 0xC09A, CM_REQUEST_GAMESTAT_PING.class);
        addPacket((short) 0x66FE, CM_REQUEST_CONTRACT.class);

        // CHARACTER
        addPacket((short) 0x7843, CM_CHARACTER_CREATE_ALLOWED.class);
        addPacket((short) 0x955D, CM_CHARACTER_LIST.class);
        addPacket((short) 0xC79C, CM_CHARACTER_CREATE.class);
        addPacket((short) 0x8188, CM_CHARACTER_DELETE.class);
        addPacket((short) 0xD909, CM_CHARACTER_RESTORE.class);

        // MOUNT
        addPacket((short) 0xB118, CM_PLAYER_UNMOUNT.class);

        // ENTER WORLD
        addPacket((short) 0x6716, CM_ENTER_WORLD.class);
        addPacket((short) 0xA2EA, CM_UNK_ENTER_WORLD.class);
        addPacket((short) 0xB808, CM_UPDATE_CONTENTS_PLAYTIME.class);
        addPacket((short) 0xE26E, CM_LOAD_TOPO_FIN.class);
        addPacket((short) 0x537E, CM_SIMPLE_TIP_REPEATED_CHECK.class);
        addPacket((short) 0xD99E, CM_PLAYER_CLIMB_START.class);
        addPacket((short) 0x7957, CM_USER_SETTINGS_SAVE.class);
        addPacket((short) 0x74F3, CM_MOVIE_END.class);

        // OPTIONS
        addPacket((short) 0x984C, CM_OPTION_SHOW_MASK.class);
        addPacket((short) 0x7A0C, CM_OPTION_SET_VISIBILITY_DISTANCE.class);

        // CHAT
        addPacket((short) 0xD510, CM_PLAYER_CHAT.class);
        addPacket((short) 0x88BC, CM_PLAYER_WHISPER.class); // TODO

        // DIALOG
        addPacket((short) 0xAD01, CM_NPC_CONTACT.class);
        addPacket((short) 0x8E26, CM_DIALOG_EVENT.class);
        addPacket((short) 0xAA56, CM_DIALOG.class);
        
        // ALLIANCE
        addPacket((short) 0xEDBA, CM_ALLIANCE_INFO.class);

        // SKILL
        addPacket((short) 0x85BE, CM_SKILL_START.class);
        addPacket((short) 0xE29D, CM_SKILL_INSTANCE_START.class);
        addPacket((short) 0x8BB4, CM_SKILL_CANCEL.class);
        addPacket((short) 0x738A, CM_GLYPH_REINIT.class);
        
        // PLAYER
        addPacket((short) 0xEA4C, CM_CHAT_INFO.class);
        addPacket((short) 0xFB20, CM_PLAYER_MOVE.class);
        addPacket((short) 0x4EFF, CM_PLAYER_ZONE_CHANGE.class);
        addPacket((short) 0xA762, CM_LOOKING_FOR_INSTANCE_WINDOW_OPEN.class); // TODO
        addPacket((short) 0x8B7A, CM_LOOKING_FOR_BATTELGROUND_WINDOW_OPEN.class); // TODO
        addPacket((short) 0xE8A2, CM_PLAYER_REPORT.class); // TODO
        addPacket((short) 0x8DA7, CM_PLAYER_COMPARE_ACHIEVEMENTS.class);
        addPacket((short) 0xB532, CM_PLAYER_INSPECT.class); // TODO
        addPacket((short) 0xDE72, CM_PLAYER_SELECT_CREATURE.class);
        addPacket((short) 0xC6F8, CM_PLAYER_GATHER.class);
        addPacket((short) 0x5D3E, CM_PLAYER_DONJON_CLEAR_COUNT_LIST.class); // TODO

        // PROFIL
        addPacket((short) 0x96A4, CM_PLAYER_SET_TITLE.class);
        addPacket((short) 0x7653, CM_PLAYER_DESCRIPTION.class);
        addPacket((short) 0x6DF5, CM_PLAYER_REINIT_INSTANCES.class);
        addPacket((short) 0xE9BC, CM_PLAYER_DONJON_STATS_PVP.class); // TODO

        // INVENTORY
        addPacket((short) 0xBD50, CM_INVENTORY_SHOW.class);
        addPacket((short) 0xBFAE, CM_PLAYER_ITEM_MOVE.class);
        addPacket((short) 0x7554, CM_ITEM_USE.class);
        addPacket((short) 0xC681, CM_ITEM_SIMPLE_INFO.class);
        addPacket((short) 0x573B, CM_INVENTORY_ORDER.class);
        addPacket((short) 0xC3D9, CM_PLAYER_UNEQUIP.class);
        addPacket((short) 0xBCD5, CM_PLAYER_EQUIP.class);
        addPacket((short) 0xC8B7, CM_PLAYER_EQUIPEMENT_ITEM_INFO.class);
        addPacket((short) 0xACA1, CM_PLAYER_DUNGEON_COOLTIME_LIST.class);
        addPacket((short) 0xB106, CM_PLAYER_ITEM_TRASH.class);
        addPacket((short) 0xB5EB, CM_PLAYER_DROP_ITEM_PICKUP.class);

        // MAP
        addPacket((short) 0x8D02, CM_MAP_SHOW.class); // TODO

        // ACTIVITIES
        addPacket((short) 0x4E89, CM_PLAYER_EMOTE.class);
        addPacket((short) 0xEACD, CM_ENCHANT_WINDOW_OPEN.class); // TODO
        addPacket((short) 0xC238, CM_INSTANCERANK_WINDOW_OPEN.class); // TODO
        addPacket((short) 0x5FB7, CM_BATTLEGROUND_WINDOW_OPEN.class); // TODO

        // SOCIAL
        addPacket((short) 0x52C4, CM_SERVERGUILD_WINDOW_OPEN.class); // TODO
        addPacket((short) 0xCEBC, CM_PLAYER_FRIEND_LIST.class);
        addPacket((short) 0xAE41, CM_PLAYER_FRIEND_ADD.class);
        addPacket((short) 0xDC58, CM_PLAYER_FRIEND_REMOVE.class);
        addPacket((short) 0x63AE, CM_PLAYER_FRIEND_NOTE.class);
        addPacket((short) 0xB1BA, CM_PLAYER_BLOCK_ADD.class);
        addPacket((short) 0x50A3, CM_PLAYER_BLOCK_REMOVE.class);
        addPacket((short) 0x63B0, CM_PLAYER_BLOCK_NOTE.class);
        addPacket((short) 0xE726, CM_REIGN_INFO.class);
        addPacket((short) 0x5A75, CM_GUARD_PK_POLICY.class);

        // TERA SHOP
        addPacket((short) 0xDEB6, CM_SHOP_WINDOW_OPEN.class);
        addPacket((short) 0x9431, CM_REQUEST_UNIQUE_OBJECT.class);
        addPacket((short) 0xA7A9, CM_REQUEST_ACCOUNT_OBJECT.class);

        // SYSTEM
        addPacket((short) 0xB047, CM_WELCOME_MESSAGE.class);
        addPacket((short) 0xD1D2, CM_PLAYER_SEND_REQUEST.class); // TODO
        addPacket((short) 0xD83A, CM_QUIT_TO_CHARACTER_LIST.class);
        addPacket((short) 0x9D25, CM_CANCEL_QUIT_TO_CHARACTER_LIST.class);
        addPacket((short) 0x513B, CM_QUIT_GAME.class);
        addPacket((short) 0xCAE0, CM_CANCEL_QUIT_GAME.class);

        // CHANNEL
        addPacket((short) 0xDCA4, CM_CHANNEL_LIST.class);
    }

    public static Class<? extends AbstractClientPacket<TeraGameConnection>> getClientPacket(final short id) {
        final Class<? extends AbstractClientPacket<TeraGameConnection>> clientPacketClass = clientPackets.get(id);
        if (clientPacketClass == null) {
            log.error("Unknow packet with id " + String.format("0x%02X: ", id));
        }

        return clientPacketClass;
    }

    private static void addPacket(final Short id, final Class<? extends AbstractClientPacket<TeraGameConnection>> packetClass) {
        if (clientPackets.containsKey(id)) {
            log.error("Client packet with id " + String.format("0x%02X: ", id) + " already exists");
            return;
        }

        clientPackets.put(id, packetClass);
    }
}
