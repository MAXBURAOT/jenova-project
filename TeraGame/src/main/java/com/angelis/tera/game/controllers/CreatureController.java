package com.angelis.tera.game.controllers;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.game.config.GlobalConfig;
import com.angelis.tera.game.models.TeraObject;
import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.models.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.models.drop.DropItem;
import com.angelis.tera.game.models.enums.DespawnTypeEnum;
import com.angelis.tera.game.models.group.Group;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.visible.VisibleTeraObject;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.network.packet.server.SM_CREATURE_DESPAWN;
import com.angelis.tera.game.network.packet.server.SM_CREATURE_HP_UPDATE;
import com.angelis.tera.game.network.packet.server.SM_CREATURE_SHOW_HP;
import com.angelis.tera.game.services.CreatureService;
import com.angelis.tera.game.services.DropService;
import com.angelis.tera.game.services.GroupService;
import com.angelis.tera.game.services.PlayerService;
import com.angelis.tera.game.services.QuestService;
import com.angelis.tera.game.services.SpawnService;
import com.angelis.tera.game.services.VisibleService;

public class CreatureController extends Controller<TeraCreature> {

    @Override
    public void update(final VisibleObjectEventTypeEnum creatureEventType, final TeraObject object, final Object... data) {

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
    public void onDie(final VisibleTeraObject killer) {
        this.owner.getAggroList().clear();
        QuestService.getInstance().onPlayerCreatureKill((Player) killer, this.owner);

        if (killer instanceof Player) {
            final Player player = (Player) killer;
            final int totalExperience = (int) (CreatureService.getInstance().getExperience(this.owner.getLevel()) * GlobalConfig.GLOBAL_KILL_RATE);

            final Group group = player.getGroup();
            if (group != null) {
                GroupService.getInstance().onGroupUpdateExperience(group, this.owner, totalExperience);
            }
            else {
                PlayerService.getInstance().onPlayerUpdateExperience(player, this.owner, totalExperience);
            }
        }

        final List<TeraServerPacket> packets = new FastList<>();
        packets.add(new SM_CREATURE_DESPAWN(this.owner, DespawnTypeEnum.DEATH));

        final List<DropItem> dropItems = DropService.getInstance().generateDrop(this.owner, (Player) killer);
        for (final DropItem dropItem : dropItems) {
            SpawnService.getInstance().spawnDropItem(dropItem);
        }

        SpawnService.getInstance().despawnCreature(this.owner, true);
        VisibleService.getInstance().sendPacketsForVisible(killer, packets);
    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onStartAttack(final VisibleTeraObject target) {
        this.owner.getAggroList().addAggro(target);
    }

    @Override
    public void onEndAttack() {
        // TODO Auto-generated method stub

    }
}
