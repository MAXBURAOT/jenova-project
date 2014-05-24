package com.angelis.tera.game.network.packet;

import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.network.packet.AbstractServerPacket;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.server.*;
import com.angelis.tera.game.network.packet.server.key.KeyServerPacket;

public class ServerPacketHandler {

    /** LOGGER */
    private static Logger log = Logger.getLogger(ClientPacketHandler.class.getName());

    private static Map<Short, Class<? extends AbstractServerPacket<TeraGameConnection>>> serverPackets = new FastMap<Short, Class<? extends AbstractServerPacket<TeraGameConnection>>>();

    public static final void init() {
        serverPackets.clear();

        // AUTH
        addPacket((short) 0x4DBD, SM_CHECK_VERSION.class); // OK
        addPacket((short) 0xF0F8, SM_LOADING_SCREEN_CONTROL_INFO.class); // OK
        addPacket((short) 0xB91F, SM_SYSTEM_INFO.class); // OK
        addPacket((short) 0xD2A6, SM_REMAIN_PLAY_TIME.class); // OK
        addPacket((short) 0xBBEE, SM_LOGIN_ARBITER.class); // OK
        addPacket((short) 0x77C1, SM_LOGIN_ACCOUNT_INFO.class); // OK

        // RESPONSE
        addPacket((short) 0xB3BD, SM_RESPONSE_CHARACTER_LIST.class); // OK
        addPacket((short) 0xCF15, SM_RESPONSE_CREATE_CHARACTER.class); // OK
        addPacket((short) 0xCE32, SM_RESPONSE_CHARACTER_NAME_CHECK.class); // OK
        addPacket((short) 0x72C0, SM_RESPONSE_CHARACTER_NAME_CHECK_USED.class); // OK

        // CHARACTER
        addPacket((short) 0xDFCB, SM_CHARACTER_CREATE.class); // OK
        addPacket((short) 0xA426, SM_CHARACTER_DELETE.class); // OK
        addPacket((short) 0x843F, SM_CHARACTER_RESTORE.class); // OK

        // PEGASUS
        addPacket((short) 0xC6D5, SM_PEGASUS_START.class); // OK
        addPacket((short) 0xA27F, SM_PEGASUS_END.class); // OK
        addPacket((short) 0xEA84, SM_PEGASUS_MAP_SHOW.class); // OK

        // MOUNT
        addPacket((short) 0x9A0B, SM_PLAYER_MOUNT.class); // OK
        addPacket((short) 0x76C4, SM_PLAYER_UNMOUNT.class); // OK

        // ENTER WORLD
        addPacket((short) 0x7F5F, SM_ENTER_WORLD.class); // OK
        addPacket((short) 0xE850, SM_SIMPLE_TIP_REPEATED_CHECK.class); // OK
        addPacket((short) 0xE751, SM_CURRENT_ELECTION_STATE.class); // OK
        addPacket((short) 0xB738, SM_USER_SETTINGS_LOAD.class); // OK
        addPacket((short) 0x6909, SM_MOVIE_PLAY.class); // OK

        // ALLIANCE
        addPacket((short) 0x7344, SM_ALLIANCE_INFO.class); // OK

        // ATTACK
        addPacket((short) 0xAB46, SM_ACTION_STAGE.class); // OK
        addPacket((short) 0xBD95, SM_ACTION_END.class); // OK
        addPacket((short) 0xA06C, SM_SKILL_RESULTS.class); // OK
        addPacket((short) 0xC581, SM_CREATURE_INSTANCE_ARROW.class); // OK
        addPacket((short) 0xC6CB, SM_PLAYER_EXPERIENCE_UPDATE.class); // OK

        // OPTIONS
        addPacket((short) 0xEC8C, SM_OPTION_SHOW_MASK.class); // OK

        // PLAYER
        addPacket((short) 0xE48C, SM_PLAYER_INIT.class); // OK
        addPacket((short) 0xA84B, SM_PLAYER_CHAT.class); // OK
        addPacket((short) 0xE8D8, SM_PLAYER_WHISPER.class); // OK
        addPacket((short) 0xA0BD, SM_CHAT_INFO.class); // OK
        addPacket((short) 0xF5AD, SM_PLAYER_FRIEND_LIST.class);
        addPacket((short) 0xF32B, SM_PLAYER_ENTER_CHANNEL.class);
        addPacket((short) 0xD576, SM_PLAYER_TELEPORT.class); // OK
        addPacket((short) 0xB8A1, SM_PLAYER_BIND.class); // OK
        addPacket((short) 0xD4CF, SM_PLAYER_STATS.class); // OK
        addPacket((short) 0xE04F, SM_PLAYER_MOVE.class); // OK
        addPacket((short) 0x570D, SM_PLAYER_ZONE_CHANGE.class); // OK
        addPacket((short) 0xFBF1, SM_PLAYER_SELECT_CREATURE.class); // OK
        addPacket((short) 0xA049, SM_PLAYER_STATE.class); // OK
        addPacket((short) 0xAD32, SM_PLAYER_GATHER_STATS.class); // OK
        addPacket((short) 0xFA29, SM_PLAYER_CRAFT_STATS.class); // OK
        addPacket((short) 0xF3C4, SM_PLAYER_SKILL_LIST.class); // OK
        addPacket((short) 0xA43D, SM_SKILL_START_COOLTIME.class); // OK
        addPacket((short) 0x57D6, SM_SKILL_PERIOD.class); // OK
        addPacket((short) 0x93FB, SM_RESPONSE_GAMESTAT_PONG.class); // OK
        addPacket((short) 0xF861, SM_PLAYER_DONJON_CLEAR_COUNT_LIST.class); // OK
        addPacket((short) 0x8BDD, SM_PLAYER_SPAWN.class); // OK
        addPacket((short) 0x7C20, SM_PLAYER_DESPAWN.class); // OK
        addPacket((short) 0xABA9, SM_PLAYER_CLIMB_START.class); // OK
        addPacket((short) 0xCA85, SM_PLAYER_DESCRIPTION.class); // OK

        // ABNORMALITY
        addPacket((short) 0xCD04, SM_ABNORMALITY_BEGIN.class); // OK
        addPacket((short) 0x67AE, SM_ABNORMALITY_END.class); // OK

        // CREATURE
        addPacket((short) 0x99C6, SM_CREATURE_HP_UPDATE.class); // OK
        addPacket((short) 0xBD7F, SM_CREATURE_MP_UPDATE.class); // OK
        addPacket((short) 0xC84B, SM_CREATURE_SPAWN.class); // OK
        addPacket((short) 0x541F, SM_CREATURE_DESPAWN.class); // OK
        addPacket((short) 0xC704, SM_CREATURE_MOVE.class); // OK
        addPacket((short) 0xCDA6, SM_DROP_ITEM_LOOT.class); // OK
        addPacket((short) 0xFB17, SM_DROP_ITEM_DESPAWN.class); // OK
        addPacket((short) 0xF8AB, SM_DROP_ITEM_SPAWN.class); // OK

        // GATHER
        addPacket((short) 0x6424, SM_GATHER_START.class); // OK
        addPacket((short) 0xE266, SM_GATHER_PROGRESS.class); // OK
        addPacket((short) 0x9D5A, SM_GATHER_END.class); // OK

        // DIALOG
        addPacket((short) 0x7D06, SM_DIALOG.class); // OK
        addPacket((short) 0xC587, SM_PLAYER_DIALOG_CLOSE.class);
        addPacket((short) 0xDB24, SM_DIALOG_EVENT.class); // OK
        addPacket((short) 0xBEB1, SM_NPC_MENU_SELECT.class); // OK

        // FIRECAMP
        addPacket((short) 0xCF6E, SM_CAMPFIRE_SPAWN.class); // OK
        addPacket((short) 0x73C2, SM_CAMPFIRE_DESPAWN.class); // OK

        // GROUP
        addPacket((short) 0xCF40, SM_GROUP_CREATE.class); // OK

        // PROFIL
        addPacket((short) 0xFFBC, SM_PLAYER_SET_TITLE.class); // OK
        addPacket((short) 0xA65B, SM_PLAYER_DONJON_STATS_PVP.class); // OK

        // QUEST
        addPacket((short) 0x8942, SM_QUEST_INFO.class); // OK
        addPacket((short) 0xDF96, SM_QUEST_BALLOON.class); // OK
        addPacket((short) 0x748A, SM_QUEST_VILLAGER_INFO.class); // OK
        addPacket((short) 0xFD28, SM_QUEST_WORLD_VILLAGER_INFO.class); // OK
        addPacket((short) 0xBC89, SM_QUEST_WORLD_VILLAGER_INFO_CLEAR.class); // OK
        
        addPacket((short) 0x9BC4, SM_QUEST_UPDATE.class); // OK

        // INVENTORY
        addPacket((short) 0xD133, SM_PLAYER_STORAGE.class); // OK
        addPacket((short) 0xF328, SM_ITEM_ADD.class); // OK
        addPacket((short) 0xC81A, SM_ITEM_INFO.class); // OK
        addPacket((short) 0xC92D, SM_ITEM_SIMPLE_INFO.class); // OK
        addPacket((short) 0xEE3F, SM_PLAYER_INVENTORY_SLOT_CHANGED.class); // OK
        addPacket((short) 0xB23F, SM_PLAYER_APPEARANCE_CHANGE.class); // OK
        addPacket((short) 0xC693, SM_ITEM_START_COOLTIME.class); // OK

        // MAP
        addPacket((short) 0x9860, SM_MAP.class); // OK

        // ACTIVITIES
        addPacket((short) 0xC391, SM_SOCIAL.class); // OK

        // SOCIAL
        addPacket((short) 0xFAB3, SM_PLAYER_FRIEND_LIST.class); // OK
        addPacket((short) 0x688B, SM_PLAYER_FRIEND_ADD_SUCCESS.class); // OK
        addPacket((short) 0xBF72, SM_PLAYER_FRIEND_REMOVE_SUCCESS.class); // OK
        addPacket((short) 0xAC7D, SM_REIGN_INFO.class); // OK
        addPacket((short) 0xB3F0, SM_GUARD_PK_POLICY.class); // OK

        // SHOP
        addPacket((short) 0xA3F4, SM_SHOP_WINDOW_OPEN.class); // OK
        addPacket((short) 0xFB8B, SM_RESPONSE_UNIQUE_OBJECT.class); // OK
        addPacket((short) 0xF28A, SM_RESPONSE_ACCOUNT_OBJECT.class); // OK

        // SYSTEM
        addPacket((short) 0x9FD4, SM_WELCOME_MESSAGE.class); // OK
        addPacket((short) 0xC566, SM_RESPONSE_PLAYER_UNLOCK.class); // OK
        addPacket((short) 0xC578, SM_RETURN_TO_CHARACTER_LIST_WINDOW_SHOW.class); // OK
        addPacket((short) 0xC555, SM_RETURN_TO_PLAYER_LIST.class); // OK
        addPacket((short) 0xA2E1, SM_CANCEL_QUIT_TO_CHARACTER_LIST.class); // OK
        addPacket((short) 0x61A8, SM_QUIT_WINDOW_SHOW.class); // OK
        addPacket((short) 0x863B, SM_QUIT.class); // OK
        addPacket((short) 0xE989, SM_CANCEL_QUIT_GAME.class); // OK

        // REQUEST
        addPacket((short) 0xEDDC, SM_PLAYER_REQUEST_ALLOWED.class); // OK
        addPacket((short) 0xA944, SM_PLAYER_REQUEST_WAIT_WINDOW.class); // OK
        addPacket((short) 0xA31E, SM_PLAYER_REQUEST_WAIT_WINDOW_HIDE.class); // OK

        // CHANNEL
        addPacket((short) 0xC749, SM_PLAYER_CHANNEL_INFO.class); // OK
        addPacket((short) 0x7DDC, SM_PLAYER_CHANNEL_LIST.class); // OK

        // OTHER
        addPacket((short) 0xADA3, SM_SYSTEM_MESSAGE.class); // OK

        // NPC
        addPacket((short) 0xB8B9, SM_CREATURE_ROTATE.class); // OK
        
        // GATHER
        addPacket((short) 0x9F83, SM_GATHER_SPAWN.class); // OK
        addPacket((short) 0xC8EC, SM_GATHER_DESPAWN.class); // OK

        // CUSTOM
        addPacket((short) 0xFFFE, SM_OPCODE_LESS_PACKET.class);
        addPacket((short) 0x9DEE, SM_CREATURE_TARGET_PLAYER.class);
        addPacket((short) 0xFBB1, SM_CREATURE_SHOW_HP.class);
        addPacket((short) 0xFFFF, KeyServerPacket.class);
    }

    @SuppressWarnings("rawtypes")
    public static Short getServerPacketId(final Class<? extends AbstractServerPacket> packetClass) {
        for (final Entry<Short, Class<? extends AbstractServerPacket<TeraGameConnection>>> entry : serverPackets.entrySet()) {
            if (entry.getValue() == packetClass) {
                return entry.getKey();
            }
        }

        log.error("Can't find packet for class " + packetClass.getName());
        return null;
    }

    private static void addPacket(final Short id, final Class<? extends AbstractServerPacket<TeraGameConnection>> packetClass) {
        if (serverPackets.containsKey(id)) {
            log.error("Server packet with id " + String.format("0x%02X: ", id) + " already exists");
            return;
        }
        serverPackets.put(id, packetClass);
    }
}
