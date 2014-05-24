package com.angelis.tera.game.controllers;

import java.util.EnumSet;
import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.game.controllers.enums.RightEnum;
import com.angelis.tera.game.models.TeraObject;
import com.angelis.tera.game.models.campfire.CampFire;
import com.angelis.tera.game.models.chainedaction.AbstractChainedAction;
import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.creature.Npc;
import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.models.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.models.dialog.Dialog;
import com.angelis.tera.game.models.drop.DropItem;
import com.angelis.tera.game.models.enums.DespawnTypeEnum;
import com.angelis.tera.game.models.gather.Gather;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.enums.EmoteEnum;
import com.angelis.tera.game.models.player.enums.PlayerModeEnum;
import com.angelis.tera.game.models.player.enums.PlayerMoveTypeEnum;
import com.angelis.tera.game.models.player.enums.PlayerRelationEnum;
import com.angelis.tera.game.models.player.request.Request;
import com.angelis.tera.game.models.visible.VisibleTeraObject;
import com.angelis.tera.game.network.SystemMessages;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.network.packet.server.SM_CAMPFIRE_DESPAWN;
import com.angelis.tera.game.network.packet.server.SM_CAMPFIRE_SPAWN;
import com.angelis.tera.game.network.packet.server.SM_CREATURE_DESPAWN;
import com.angelis.tera.game.network.packet.server.SM_CREATURE_HP_UPDATE;
import com.angelis.tera.game.network.packet.server.SM_CREATURE_MOVE;
import com.angelis.tera.game.network.packet.server.SM_CREATURE_SHOW_HP;
import com.angelis.tera.game.network.packet.server.SM_CREATURE_SPAWN;
import com.angelis.tera.game.network.packet.server.SM_DROP_ITEM_DESPAWN;
import com.angelis.tera.game.network.packet.server.SM_DROP_ITEM_SPAWN;
import com.angelis.tera.game.network.packet.server.SM_GATHER_DESPAWN;
import com.angelis.tera.game.network.packet.server.SM_GATHER_SPAWN;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_DESPAWN;
import com.angelis.tera.game.network.packet.server.SM_SOCIAL;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_MOVE;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_SPAWN;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_STATE;
import com.angelis.tera.game.services.QuestService;
import com.angelis.tera.game.services.VisibleService;

public class PlayerController extends Controller<Player> {

    private final EnumSet<RightEnum> rights = EnumSet.of(RightEnum.WALK, RightEnum.TALK);

    private Request request;
    private Dialog dialog;
    private AbstractChainedAction chainedAction;
    private boolean isInBattle;

    public boolean can(final RightEnum right) {
        return this.rights.contains(right);
    }

    public final void removeRight(final RightEnum right) {
        this.rights.remove(right);
    }

    public final void addRight(final RightEnum right) {
        this.rights.add(right);
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(final Request request) {
        this.request = request;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(final Dialog dialog) {
        this.dialog = dialog;
    }

    public AbstractChainedAction getChainedAction() {
        return chainedAction;
    }

    public void setChainedAction(final AbstractChainedAction chainedAction) {
        this.chainedAction = chainedAction;
    }

    @Override
    public void update(final VisibleObjectEventTypeEnum creatureEventType, final TeraObject object, final Object... data) {
        final Player player = this.owner;
        if (player == null || player.getConnection() == null) {
            return;
        }

        final List<TeraServerPacket> packets = new FastList<>();
        switch (creatureEventType) {
            case APPEAR:
                if (object instanceof Player) {
                    // TODO player relation
                    packets.add(new SM_PLAYER_SPAWN((Player) object, PlayerRelationEnum.FRIENDLY));
                }
                else if (object instanceof TeraCreature) {
                    packets.add(new SM_CREATURE_SPAWN((TeraCreature) object));
                }
                else if (object instanceof Gather) {
                    final Gather gather = (Gather) object;
                    packets.add(new SM_GATHER_SPAWN(gather));
                }
                else if (object instanceof CampFire) {
                    packets.add(new SM_CAMPFIRE_SPAWN((CampFire) object));
                }
                else if (object instanceof DropItem) {
                    packets.add(new SM_DROP_ITEM_SPAWN((DropItem) object));
                }
                else {
                    System.err.println("UNKNOW APPEAR OBJECT");
                }
            break;

            case DISAPPEAR:
                if (object instanceof Player) {
                    packets.add(new SM_PLAYER_DESPAWN((Player) object));
                }
                else if (object instanceof TeraCreature) {
                    final TeraCreature teraCreature = (TeraCreature) object;
                    if (teraCreature.getCreatureCurrentStats().isDead()) {
                        packets.add(new SM_CREATURE_DESPAWN(teraCreature, DespawnTypeEnum.DEATH));
                    }
                    else {
                        packets.add(new SM_CREATURE_DESPAWN(teraCreature, DespawnTypeEnum.DELETE));
                    }
                }
                else if (object instanceof Gather) {
                    final Gather gather = (Gather) object;
                    if (gather.getRemainingGather() == 0) {
                        packets.add(new SM_GATHER_DESPAWN(gather, DespawnTypeEnum.GATHERED));
                    }
                    else {
                        packets.add(new SM_GATHER_DESPAWN(gather, DespawnTypeEnum.DELETE));
                    }
                }
                else if (object instanceof CampFire) {
                    packets.add(new SM_CAMPFIRE_DESPAWN((CampFire) object));
                }
                else if (object instanceof DropItem) {
                    packets.add(new SM_DROP_ITEM_DESPAWN((DropItem) object));
                }
                else {
                    System.err.println("UNKNOW DISAPPEAR OBJECT");
                }
            break;

            case CREATURE_MOVE:
                final float x1 = (float) data[0];
                final float y1 = (float) data[1];
                final float z1 = (float) data[2];
                final short heading = (short) data[3];
                final float x2 = (float) data[4];
                final float y2 = (float) data[5];
                final float z2 = (float) data[6];

                if (player.getWorldPosition().distanceTo(x1, y1, z1) > player.getAccount().getOptions().getDisplayRange().value) {
                    // Creature is no more visible so it's a remove
                    if (object instanceof Player) {
                        packets.add(new SM_PLAYER_DESPAWN((Player) object));
                    }
                    else if (object instanceof TeraCreature) {
                        packets.add(new SM_CREATURE_DESPAWN((TeraCreature) object, DespawnTypeEnum.DELETE));
                    }
                }
                else {
                    // Creature is still visible so it's a simple move
                    if (object instanceof Player) {
                        final PlayerMoveTypeEnum playerMoveType = (PlayerMoveTypeEnum) data[7];
                        final byte[] unk2 = (byte[]) data[8];
                        final int unk3 = (int) data[9];

                        packets.add(new SM_PLAYER_MOVE((Player) object, x1, y1, z1, heading, x2, y2, z2, playerMoveType, unk2, unk3));
                    }
                    else if (object instanceof TeraCreature) {
                        packets.add(new SM_CREATURE_MOVE((TeraCreature) object, x1, y1, z1));
                    }
                }
            break;

            case CREATURE_EMOTE:
                final EmoteEnum emote = (EmoteEnum) data[0];
                final int duration = (int) data[1];
                packets.add(new SM_SOCIAL((Player) object, emote, duration));
            break;

            default:
        }

        for (final TeraServerPacket packet : packets) {
            player.getConnection().sendPacket(packet);
        }
        
        // THIS SHOULD BE MOVED BUT MUST BE AFTER THE APPEAR PACKETS
        if (object instanceof Npc) {
            QuestService.getInstance().updatePlayerQuestNpcVillageInfo(this.owner);
        }
    }

    @Override
    public void onDie(final VisibleTeraObject killer) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onRespawn() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStartAttack(final VisibleTeraObject target) {
        if (!isInBattle) {
            this.owner.setPlayerMode(PlayerModeEnum.ARMORED);

            final List<TeraServerPacket> packets = new FastList<>();
            packets.add(new SM_PLAYER_STATE(this.owner));
            packets.add(SystemMessages.BATTLE_START());
            VisibleService.getInstance().sendPacketsForVisible(this.owner, packets);
            this.isInBattle = true;
        }
    }

    @Override
    public void onDamage(final VisibleTeraObject attacker, final int damage) {
        this.owner.getCreatureCurrentStats().addHp(-damage);

        final List<TeraServerPacket> packets = new FastList<>();
        packets.add(new SM_CREATURE_HP_UPDATE(this.owner, (Creature) attacker, -damage));
        packets.add(new SM_CREATURE_SHOW_HP(this.owner, false));
        VisibleService.getInstance().sendPacketsForVisible(this.owner, packets);
    }

    @Override
    public void onEndAttack() {
        if (this.isInBattle) {
            this.owner.setPlayerMode(PlayerModeEnum.NORMAL);
            this.owner.getConnection().sendPacket(SystemMessages.BATTLE_END());
            VisibleService.getInstance().sendPacketForVisible(this.owner, new SM_PLAYER_STATE(this.owner));
            this.isInBattle = false;
        }
    }
}
