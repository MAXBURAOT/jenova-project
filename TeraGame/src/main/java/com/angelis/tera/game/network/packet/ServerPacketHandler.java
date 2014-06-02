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
        addPacket((short) 0x4DBD, SM_CHECK_VERSION.class);
        addPacket((short) 0x937A, SM_LOADING_SCREEN_CONTROL_INFO.class);
        addPacket((short) 0xDE2F, SM_SYSTEM_INFO.class);
        addPacket((short) 0x9CEF, SM_REMAIN_PLAY_TIME.class);
        addPacket((short) 0xB274, SM_LOGIN_ARBITER.class);
        addPacket((short) 0x5D79, SM_LOGIN_ACCOUNT_INFO.class);

        // RESPONSE
        addPacket((short) 0x6291, SM_RESPONSE_CHARACTER_LIST.class);
        addPacket((short) 0x9275, SM_RESPONSE_CHARACTER_NAME_CHECK.class);

        // CHARACTER
        addPacket((short) 0xCCF3, SM_CHARACTER_CREATE_ALLOWED.class);
        addPacket((short) 0x7502, SM_CHARACTER_USERNAME_CHECK.class);
        addPacket((short) 0xF177, SM_CHARACTER_CREATE.class);
        addPacket((short) 0x5275, SM_CHARACTER_DELETE.class);
        addPacket((short) 0xD727, SM_CHARACTER_RESTORE.class);

        // PEGASUS
        addPacket((short) 0xBE73, SM_PEGASUS_START.class);
        addPacket((short) 0xE502, SM_PEGASUS_END.class);
        addPacket((short) 0xD4E5, SM_PEGASUS_MAP_SHOW.class);

        // MOUNT
        addPacket((short) 0xFB59, SM_PLAYER_MOUNT.class);
        addPacket((short) 0xCF34, SM_PLAYER_UNMOUNT.class);

        // ENTER WORLD
        addPacket((short) 0x5721, SM_ENTER_WORLD.class);
        addPacket((short) 0x8F1D, SM_AVAILABLE_SOCIAL_LIST.class);
        addPacket((short) 0x8D76, SM_QUEST_CLEAR_INFO.class);
        addPacket((short) 0xE6AC, SM_QUEST_DAILY_COMPLETE_COUNT.class);
        addPacket((short) 0xF6F7, SM_MISSION_COMPLETE_INFO.class);
        addPacket((short) 0xDAD0, SM_NPC_GUILD_LIST.class);
        addPacket((short) 0xE227, SM_VIRTUAL_LATENCY.class);
        addPacket((short) 0x4F7A, SM_MOVE_DISTANCE_DELTA.class);
        addPacket((short) 0x7137, SM_F2P_PREMIUM_USER_PERMISSION.class);
        addPacket((short) 0x99E7, SM_PLAYER_EQUIP_ITEM_CHANGER.class);
        addPacket((short) 0xD22A, SM_FESTIVAL_LIST.class);
        addPacket((short) 0xCA4E, SM_LOAD_HINT.class);
        addPacket((short) 0x5BFC, SM_MASSTIGE_STATUS.class);
        addPacket((short) 0x57C8, SM_LOGIN.class);
        addPacket((short) 0xCDB9, SM_SIMPLE_TIP_REPEATED_CHECK.class);
        addPacket((short) 0xCAFE, SM_CURRENT_ELECTION_STATE.class);
        addPacket((short) 0xB40C, SM_USER_SETTINGS_LOAD.class);
        addPacket((short) 0xA7DE, SM_MOVIE_PLAY.class);
        addPacket((short) 0xB826, SM_VISITED_SECTION_LIST.class);
        addPacket((short) 0x50DC, SM_TRADEBROKER_HIGHEST_ITEM_LEVEL.class);
        addPacket((short) 0xC8CA, SM_ACCOUNT_PACKAGE_LIST.class);
        addPacket((short) 0x954D, SM_ACHIEVEMENT_PROGRESS_UPDATE.class);

        // PET
        addPacket((short) 0xE120, SM_PET_INCUBATOR_INFO_CHANGE.class);
        addPacket((short) 0x7171, SM_PET_INFO_CLEAR.class);
        
        // ALLIANCE
        addPacket((short) 0xDC98, SM_ALLIANCE_INFO.class);

        // ATTACK
        addPacket((short) 0x99E3, SM_ACTION_STAGE.class);
        addPacket((short) 0xDFF8, SM_ACTION_END.class);
        addPacket((short) 0x8FC3, SM_SKILL_RESULTS.class);
        addPacket((short) 0xD3A1, SM_CREATURE_INSTANCE_ARROW.class);
        addPacket((short) 0x64DA, SM_PLAYER_EXPERIENCE_UPDATE.class);

        // OPTIONS
        addPacket((short) 0x6A4E, SM_OPTION_SHOW_MASK.class);

        // SKILL
        addPacket((short) 0xA2F7, SM_SKILL_START_COOLTIME.class);
        addPacket((short) 0xA909, SM_SKILL_PERIOD.class);
        
        // PLAYER
        addPacket((short) 0x56D8, SM_PLAYER_CHAT.class);
        addPacket((short) 0xE8D8, SM_PLAYER_WHISPER.class); // TODO
        addPacket((short) 0xC299, SM_CHAT_INFO.class);
        addPacket((short) 0xE1B4, SM_PLAYER_FRIEND_LIST.class);
        addPacket((short) 0xFD64, SM_PLAYER_TELEPORT.class);
        addPacket((short) 0xB53E, SM_LOAD_TOPO.class);
        addPacket((short) 0xB74D, SM_PLAYER_STATS_UPDATE.class);
        addPacket((short) 0xAF28, SM_PLAYER_MOVE.class);
        addPacket((short) 0x5133, SM_PLAYER_ZONE_CHANGE.class);
        addPacket((short) 0xC258, SM_PLAYER_SELECT_CREATURE.class);
        addPacket((short) 0xB195, SM_PLAYER_STATE.class);
        addPacket((short) 0xBBB0, SM_PLAYER_GATHER_STATS.class);
        addPacket((short) 0xEF03, SM_PLAYER_SKILL_LIST.class);
        addPacket((short) 0xA3C0, SM_RESPONSE_GAMESTAT_PONG.class);
        addPacket((short) 0x6022, SM_PLAYER_DONJON_CLEAR_COUNT_LIST.class);
        addPacket((short) 0xDF5B, SM_PLAYER_SPAWN.class);
        addPacket((short) 0xEF19, SM_PLAYER_DESPAWN.class);
        addPacket((short) 0xC7A3, SM_PLAYER_CLIMB_START.class);
        addPacket((short) 0xEEFA, SM_PLAYER_DESCRIPTION.class);
        
        // CRAFT
        addPacket((short) 0x6DB9, SM_CRAFT_STATS.class);
        addPacket((short) 0xC38A, SM_CRAFT_RECIPE_LIST.class);

        // ABNORMALITY
        addPacket((short) 0x7486, SM_ABNORMALITY_BEGIN.class);
        addPacket((short) 0xC9A4, SM_ABNORMALITY_END.class);

        // CREATURE
        addPacket((short) 0xB34F, SM_CREATURE_HP_UPDATE.class);
        addPacket((short) 0xC875, SM_CREATURE_MP_UPDATE.class);
        addPacket((short) 0xEE60, SM_CREATURE_SPAWN.class);
        addPacket((short) 0x850D, SM_CREATURE_DESPAWN.class);
        addPacket((short) 0xD41B, SM_CREATURE_MOVE.class);
        addPacket((short) 0xC47B, SM_DROP_ITEM_LOOT.class);
        addPacket((short) 0x5DF8, SM_DROP_ITEM_DESPAWN.class);
        addPacket((short) 0xA6A7, SM_DROP_ITEM_SPAWN.class);
        addPacket((short) 0xEC17, SM_CREATURE_ROTATE.class);
        addPacket((short) 0xC2EA, SM_CREATURE_TARGET_PLAYER.class);
        addPacket((short) 0xD62B, SM_CREATURE_SHOW_HP.class);

        // DIALOG
        addPacket((short) 0x7D06, SM_DIALOG.class); // TODO
        addPacket((short) 0xAE4F, SM_PLAYER_DIALOG_CLOSE.class);
        addPacket((short) 0xA554, SM_DIALOG_EVENT.class);
        addPacket((short) 0xA8F5, SM_NPC_MENU_SELECT.class);

        // FIRECAMP
        addPacket((short) 0xCCE4, SM_CAMPFIRE_SPAWN.class);
        addPacket((short) 0xB5EF, SM_CAMPFIRE_DESPAWN.class);

        // GROUP
        addPacket((short) 0xC689, SM_GROUP_MEMBER_LIST.class);

        // PROFIL
        addPacket((short) 0xF8E2, SM_PLAYER_SET_TITLE.class);
        addPacket((short) 0xB8C4, SM_PLAYER_DONJON_STATS_PVP.class); // TODO

        // QUEST
        addPacket((short) 0xE352, SM_QUEST_INFO.class);
        addPacket((short) 0xF929, SM_QUEST_BALLOON.class);
        addPacket((short) 0x8F45, SM_QUEST_VILLAGER_INFO.class);
        addPacket((short) 0xFB81, SM_QUEST_WORLD_VILLAGER_INFO.class);
        addPacket((short) 0x5714, SM_QUEST_WORLD_VILLAGER_INFO_CLEAR.class);
        addPacket((short) 0xB433, SM_QUEST_UPDATE.class);

        // INVENTORY
        addPacket((short) 0x8731, SM_INVENTORY.class);
        addPacket((short) 0x7C7F, SM_ITEM_ADD.class);
        addPacket((short) 0xAEE7, SM_ITEM_INFO.class);
        addPacket((short) 0xD3D7, SM_ITEM_SIMPLE_INFO.class);
        addPacket((short) 0xBB5B, SM_PLAYER_INVENTORY_SLOT_CHANGED.class);
        addPacket((short) 0xD9E2, SM_PLAYER_APPEARANCE_CHANGE.class);
        addPacket((short) 0x5601, SM_ITEM_START_COOLTIME.class);

        // MAP
        addPacket((short) 0x9860, SM_MAP.class); // TODO

        // ACTIVITIES
        addPacket((short) 0x8969, SM_SOCIAL.class);

        // SOCIAL
        addPacket((short) 0xC18B, SM_PLAYER_FRIEND_LIST.class);
        addPacket((short) 0x9547, SM_PLAYER_FRIEND_ADD_SUCCESS.class);
        addPacket((short) 0x9946, SM_PLAYER_FRIEND_REMOVE_SUCCESS.class);
        addPacket((short) 0xC156, SM_REIGN_INFO.class);
        addPacket((short) 0xCAD2, SM_GUARD_PK_POLICY.class);

        // SHOP
        addPacket((short) 0xA3F4, SM_SHOP_WINDOW_OPEN.class); // TODO
        addPacket((short) 0xB79F, SM_RESPONSE_UNIQUE_OBJECT.class);
        addPacket((short) 0xC2D8, SM_RESPONSE_ACCOUNT_OBJECT.class);

        // SYSTEM
        addPacket((short) 0x6726, SM_WELCOME_MESSAGE.class);
        addPacket((short) 0xA9E9, SM_RESPONSE_PLAYER_UNLOCK.class);
        addPacket((short) 0xD8F1, SM_RETURN_TO_CHARACTER_LIST_WINDOW_SHOW.class);
        addPacket((short) 0x98B5, SM_RETURN_TO_PLAYER_LIST.class);
        addPacket((short) 0xE39C, SM_CANCEL_QUIT_TO_CHARACTER_LIST.class);
        addPacket((short) 0x9B12, SM_QUIT_WINDOW_SHOW.class);
        addPacket((short) 0xD4A7, SM_QUIT.class);
        addPacket((short) 0xE989, SM_CANCEL_QUIT_GAME.class); // TODO

        // REQUEST
        addPacket((short) 0xDFD7, SM_REQUEST_CONTRACT_REPLY.class);
        addPacket((short) 0x5EEC, SM_REQUEST_CONTRACT.class);
        addPacket((short) 0xA249, SM_REQUEST_CONTRACT_CANCEL.class);

        // CHANNEL
        addPacket((short) 0xF32B, SM_PLAYER_ENTER_CHANNEL.class); // TODO
        addPacket((short) 0xEFBB, SM_PLAYER_CHANNEL_INFO.class);
        addPacket((short) 0xE17E, SM_PLAYER_CHANNEL_LIST.class);

        // OTHER
        addPacket((short) 0xB62F, SM_SYSTEM_MESSAGE.class);

        // GATHER
        addPacket((short) 0xACDF, SM_GATHER_START.class);
        addPacket((short) 0xCBF8, SM_GATHER_PROGRESS.class);
        addPacket((short) 0x73BC, SM_GATHER_END.class);
        addPacket((short) 0xE2E4, SM_GATHER_SPAWN.class);
        addPacket((short) 0xFFE5, SM_GATHER_DESPAWN.class);

        // CUSTOM
        addPacket((short) 0xFFFE, SM_OPCODE_LESS_PACKET.class);
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
