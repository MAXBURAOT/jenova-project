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
import com.angelis.tera.game.network.packet.client.CM_CHARACTER_CREATE;
import com.angelis.tera.game.network.packet.client.CM_CHARACTER_DELETE;
import com.angelis.tera.game.network.packet.client.CM_CHARACTER_RESTORE;
import com.angelis.tera.game.network.packet.client.CM_CHAT_INFO;
import com.angelis.tera.game.network.packet.client.CM_CHECK_VERSION;
import com.angelis.tera.game.network.packet.client.CM_DIALOG;
import com.angelis.tera.game.network.packet.client.CM_DIALOG_EVENT;
import com.angelis.tera.game.network.packet.client.CM_ENCHANT_WINDOW_OPEN;
import com.angelis.tera.game.network.packet.client.CM_ENTER_WORLD;
import com.angelis.tera.game.network.packet.client.CM_GUARD_PK_POLICY;
import com.angelis.tera.game.network.packet.client.CM_HARDWARE_INFO;
import com.angelis.tera.game.network.packet.client.CM_INSTANCERANK_WINDOW_OPEN;
import com.angelis.tera.game.network.packet.client.CM_INVENTORY_OPEN;
import com.angelis.tera.game.network.packet.client.CM_INVENTORY_ORDER;
import com.angelis.tera.game.network.packet.client.CM_ITEM_SIMPLE_INFO;
import com.angelis.tera.game.network.packet.client.CM_ITEM_USE;
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
import com.angelis.tera.game.network.packet.client.CM_PLAYER_CHANNEL_INFO;
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
import com.angelis.tera.game.network.packet.client.CM_PLAYER_REQUEST;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_SELECT_CREATURE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_SEND_REQUEST;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_SET_TITLE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_SKILL_CANCEL;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_SKILL_INSTANCE_USE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_SKILL_USE;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_UNEQUIP;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_UNMOUNT;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_WHISPER;
import com.angelis.tera.game.network.packet.client.CM_PLAYER_ZONE_CHANGE;
import com.angelis.tera.game.network.packet.client.CM_QUIT_GAME;
import com.angelis.tera.game.network.packet.client.CM_QUIT_TO_CHARACTER_LIST;
import com.angelis.tera.game.network.packet.client.CM_REIGN_INFO;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_ACCOUNT_OBJECT;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_CHARACTER_CREATE;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_CHARACTER_LIST;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_CHARACTER_NAME_CHECK;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_CHARACTER_NAME_CHECK_USED;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_GAMESTAT_PING;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_SYSTEM_INFO;
import com.angelis.tera.game.network.packet.client.CM_REQUEST_UNIQUE_OBJECT;
import com.angelis.tera.game.network.packet.client.CM_SERVERGUILD_WINDOW_OPEN;
import com.angelis.tera.game.network.packet.client.CM_SHOP_WINDOW_OPEN;
import com.angelis.tera.game.network.packet.client.CM_SIMPLE_TIP_REPEATED_CHECK;
import com.angelis.tera.game.network.packet.client.CM_UNK_ENTER_WORLD;
import com.angelis.tera.game.network.packet.client.CM_UNK_ENTER_WORLD3;
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
        addPacket((short) 0x4DBC, CM_CHECK_VERSION.class); // OK
        addPacket((short) 0x9594, CM_ACCOUNT_AUTH.class); // OK
        addPacket((short) 0x5DF4, CM_HARDWARE_INFO.class); // OK

        // REQUEST
        addPacket((short) 0xADBF, CM_REQUEST_CHARACTER_LIST.class); // OK
        addPacket((short) 0xD781, CM_REQUEST_SYSTEM_INFO.class);
        addPacket((short) 0xD8F2, CM_REQUEST_CHARACTER_CREATE.class); // OK
        addPacket((short) 0x52D6, CM_REQUEST_CHARACTER_NAME_CHECK.class); // OK
        addPacket((short) 0xEC3B, CM_REQUEST_CHARACTER_NAME_CHECK_USED.class); // OK

        // CHARACTER
        addPacket((short) 0xADF9, CM_CHARACTER_CREATE.class); // OK
        addPacket((short) 0x8810, CM_CHARACTER_DELETE.class); // OK
        addPacket((short) 0xB9AA, CM_CHARACTER_RESTORE.class); // OK

        // MOUNT
        addPacket((short) 0xF72E, CM_PLAYER_UNMOUNT.class); // OK

        // ENTER WORLD
        addPacket((short) 0xBAC8, CM_ENTER_WORLD.class); // OK
        addPacket((short) 0xFCCC, CM_UNK_ENTER_WORLD.class); // OK
        addPacket((short) 0x8FA2, CM_UPDATE_CONTENTS_PLAYTIME.class); // OK
        addPacket((short) 0xAABA, CM_UNK_ENTER_WORLD3.class); // OK
        addPacket((short) 0xD2E5, CM_SIMPLE_TIP_REPEATED_CHECK.class);
        addPacket((short) 0xBC07, CM_PLAYER_CLIMB_START.class); // OK
        addPacket((short) 0x61D2, CM_REQUEST_GAMESTAT_PING.class); // OK
        addPacket((short) 0x7C4D, CM_USER_SETTINGS_SAVE.class); // OK
        addPacket((short) 0xA614, CM_MOVIE_END.class); // OK

        // OPTIONS
        addPacket((short) 0xAE9D, CM_OPTION_SHOW_MASK.class); // OK
        addPacket((short) 0x677D, CM_OPTION_SET_VISIBILITY_DISTANCE.class); // OK

        // CHAT
        addPacket((short) 0xC478, CM_PLAYER_CHAT.class); // OK
        addPacket((short) 0x88BC, CM_PLAYER_WHISPER.class); // OK

        // DIALOG
        addPacket((short) 0x8033, CM_NPC_CONTACT.class); // OK
        addPacket((short) 0xAF2E, CM_DIALOG_EVENT.class); // OK
        addPacket((short) 0xDA01, CM_DIALOG.class); // OK
        
        // ALLIANCE
        addPacket((short) 0xF900, CM_ALLIANCE_INFO.class); // OK

        // PLAYER
        addPacket((short) 0xCDF0, CM_PLAYER_SKILL_USE.class); // OK
        addPacket((short) 0xC2B7, CM_PLAYER_SKILL_INSTANCE_USE.class); // OK
        addPacket((short) 0xD8B4, CM_PLAYER_SKILL_CANCEL.class); // OK
        addPacket((short) 0x5A33, CM_CHAT_INFO.class); // OK
        addPacket((short) 0xB3F3, CM_PLAYER_MOVE.class); // OK
        addPacket((short) 0xCEF5, CM_PLAYER_ZONE_CHANGE.class); // OK
        addPacket((short) 0xA762, CM_LOOKING_FOR_INSTANCE_WINDOW_OPEN.class);
        addPacket((short) 0x8B7A, CM_LOOKING_FOR_BATTELGROUND_WINDOW_OPEN.class);
        addPacket((short) 0xE8A2, CM_PLAYER_REPORT.class); // OK
        addPacket((short) 0x56E0, CM_PLAYER_COMPARE_ACHIEVEMENTS.class); // OK
        addPacket((short) 0xB532, CM_PLAYER_INSPECT.class); // OK
        addPacket((short) 0xD50E, CM_PLAYER_SELECT_CREATURE.class); // OK
        addPacket((short) 0x9CB0, CM_PLAYER_GATHER.class); // OK
        addPacket((short) 0x5D3E, CM_PLAYER_DONJON_CLEAR_COUNT_LIST.class); // OK

        // PROFIL
        addPacket((short) 0xA493, CM_PLAYER_SET_TITLE.class); // OK
        addPacket((short) 0x909B, CM_PLAYER_DESCRIPTION.class); // OK
        addPacket((short) 0xA1A0, CM_PLAYER_REINIT_INSTANCES.class); // OK
        addPacket((short) 0xE9BC, CM_PLAYER_DONJON_STATS_PVP.class); // OK

        // QUESTS
        
        // INVENTORY
        addPacket((short) 0x6F66, CM_INVENTORY_OPEN.class); // OK
        addPacket((short) 0xF27F, CM_PLAYER_ITEM_MOVE.class); // OK
        addPacket((short) 0x5367, CM_ITEM_USE.class); // OK
        addPacket((short) 0xDADB, CM_ITEM_SIMPLE_INFO.class); // OK
        addPacket((short) 0x8ABC, CM_INVENTORY_ORDER.class); // OK
        addPacket((short) 0xA986, CM_PLAYER_UNEQUIP.class); // OK
        addPacket((short) 0xA123, CM_PLAYER_EQUIP.class); // OK
        addPacket((short) 0x8E6C, CM_PLAYER_EQUIPEMENT_ITEM_INFO.class); // OK
        addPacket((short) 0xAB53, CM_PLAYER_DUNGEON_COOLTIME_LIST.class); // OK
        addPacket((short) 0x94E0, CM_PLAYER_ITEM_TRASH.class); // OK
        addPacket((short) 0x8B84, CM_PLAYER_DROP_ITEM_PICKUP.class); // OK

        // MAP
        addPacket((short) 0x8D02, CM_MAP_SHOW.class);

        // ACTIVITIES
        addPacket((short) 0xBB2F, CM_PLAYER_EMOTE.class); // OK
        addPacket((short) 0xEACD, CM_ENCHANT_WINDOW_OPEN.class);
        addPacket((short) 0xC238, CM_INSTANCERANK_WINDOW_OPEN.class);
        addPacket((short) 0x5FB7, CM_BATTLEGROUND_WINDOW_OPEN.class);

        // SOCIAL
        addPacket((short) 0x52C4, CM_SERVERGUILD_WINDOW_OPEN.class);
        addPacket((short) 0x8516, CM_PLAYER_FRIEND_LIST.class); // OK
        addPacket((short) 0xA4F6, CM_PLAYER_FRIEND_ADD.class); // OK
        addPacket((short) 0xA374, CM_PLAYER_FRIEND_REMOVE.class); // OK
        addPacket((short) 0x5AC3, CM_PLAYER_FRIEND_NOTE.class); // OK
        addPacket((short) 0x8537, CM_PLAYER_BLOCK_ADD.class); // OK
        addPacket((short) 0xDA22, CM_PLAYER_BLOCK_REMOVE.class); // OK
        addPacket((short) 0xA4EE, CM_PLAYER_BLOCK_NOTE.class); // OK
        addPacket((short) 0x9860, CM_REIGN_INFO.class); // OK
        addPacket((short) 0x984D, CM_GUARD_PK_POLICY.class); // OK

        // TERA SHOP
        addPacket((short) 0x58B7, CM_SHOP_WINDOW_OPEN.class); // OK
        addPacket((short) 0xABA2, CM_REQUEST_UNIQUE_OBJECT.class); // OK
        addPacket((short) 0xD046, CM_REQUEST_ACCOUNT_OBJECT.class); // OK

        // SYSTEM
        addPacket((short) 0xD0A4, CM_WELCOME_MESSAGE.class); // OK
        addPacket((short) 0xD1D2, CM_PLAYER_SEND_REQUEST.class); // OK
        addPacket((short) 0x5C46, CM_PLAYER_REQUEST.class); // OK
        addPacket((short) 0x69EC, CM_QUIT_TO_CHARACTER_LIST.class); // OK
        addPacket((short) 0xC683, CM_CANCEL_QUIT_TO_CHARACTER_LIST.class); // OK
        addPacket((short) 0xF270, CM_QUIT_GAME.class); // OK
        addPacket((short) 0xC4C8, CM_CANCEL_QUIT_GAME.class); // OK

        // CHANNEL
        addPacket((short) 0x7ECF, CM_PLAYER_CHANNEL_INFO.class); // OK
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
