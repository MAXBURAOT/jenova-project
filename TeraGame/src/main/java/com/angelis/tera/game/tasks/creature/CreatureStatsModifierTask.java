package com.angelis.tera.game.tasks.creature;

import com.angelis.tera.game.config.PlayerConfig;
import com.angelis.tera.game.models.abnormality.Abnormality;
import com.angelis.tera.game.models.campfire.CampFire;
import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.creature.CreatureCurrentStats;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.enums.PlayerModeEnum;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_STATS;
import com.angelis.tera.game.services.VisibleService;
import com.angelis.tera.game.tasks.AbstractTask;
import com.angelis.tera.game.tasks.TaskTypeEnum;

public class CreatureStatsModifierTask extends AbstractTask<Creature> {

    private short tick;

    public CreatureStatsModifierTask(final Creature linkedObject) {
        super(linkedObject, TaskTypeEnum.PLAYER_STATS_MODIFIER);
    }

    @Override
    public void execute() {
        tick++;
        if (tick > 10) {
            tick = 1;
        }

        final CreatureCurrentStats creatureCurrentStats = this.linkedObject.getCreatureCurrentStats();
        for (final Abnormality abnormality : this.linkedObject.getAbnormalities()) {
            if (abnormality.getDelay() % tick == 0) {
                creatureCurrentStats.addHp(abnormality.getHpModifier());
                creatureCurrentStats.addMp(abnormality.getMpModifier());
            }
        }

        if (this.linkedObject instanceof Player) {
            final Player player = (Player) this.linkedObject;
            if (!player.isOnline()) {
                this.cancel();
                return;
            }

            final CampFire activeCampFire = player.getActiveCampFire();
            if (activeCampFire != null) {
                if (PlayerConfig.PLAYER_GAIN_STAMINA_DELAY % tick == 0) {
                    creatureCurrentStats.addStamina(activeCampFire.getRate());
                }
            }

            if (player.getPlayerMode() == PlayerModeEnum.ARMORED) {
                creatureCurrentStats.addHp(this.linkedObject.getCreatureBaseStats().getCombatHpRegen());
                creatureCurrentStats.addMp(this.linkedObject.getCreatureBaseStats().getCombatMpRegen());
            }
            else {
                creatureCurrentStats.addMp(this.linkedObject.getCreatureBaseStats().getNaturalMpRegen());
                creatureCurrentStats.addMp(this.linkedObject.getCreatureBaseStats().getNaturalMpDegen());
                if (creatureCurrentStats.getRe() > 0) {
                    creatureCurrentStats.addRe(-10);
                }
            }
            VisibleService.getInstance().sendPacketForVisible(this.linkedObject, new SM_PLAYER_STATS(player));
        }
    }
}
