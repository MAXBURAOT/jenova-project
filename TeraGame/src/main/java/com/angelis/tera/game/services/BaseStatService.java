package com.angelis.tera.game.services;

import org.apache.log4j.Logger;

import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.creature.CreatureBaseStats;
import com.angelis.tera.game.models.creature.CreatureBonusStats;
import com.angelis.tera.game.models.creature.CreatureCurrentStats;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.enums.PlayerClassEnum;
import com.angelis.tera.game.xml.entity.BaseStatEntity;
import com.angelis.tera.game.xml.entity.players.PlayerBaseStatsEntity;
import com.angelis.tera.game.xml.entity.players.PlayerBaseStatsEntityHolder;

public class BaseStatService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(BaseStatService.class.getName());

    @Override
    public void onInit() {
        log.info("BaseStatService started");
    }

    @Override
    public void onDestroy() {
        log.info("BaseStatService started");
    }

    public void onPlayerCreate(final Player player) {
        affectCreatureBaseStats(player, getPlayerBaseStatByLevel(player.getPlayerClass(), player.getLevel()));
        affectCreatureCurrentStats(player);
        affectCreatureBonusStats(player);
    }

    public void onPlayerConnect(final Player player) {
        affectCreatureBaseStats(player, getPlayerBaseStatByLevel(player.getPlayerClass(), player.getLevel()));
        affectCreatureCurrentStats(player);
        affectCreatureBonusStats(player);
    }

    public void onPlayerUpdate(final Player player) {
        affectCreatureBaseStats(player, getPlayerBaseStatByLevel(player.getPlayerClass(), player.getLevel()));
        affectCreatureCurrentStats(player);
        affectCreatureBonusStats(player);
    }

    public void onPlayerLevelUp(final Player player) {
        affectCreatureBaseStats(player, getPlayerBaseStatByLevel(player.getPlayerClass(), player.getLevel()));
        affectCreatureCurrentStats(player);
        affectCreatureBonusStats(player);
    }

    public BaseStatEntity getPlayerBaseStatByLevel(final PlayerClassEnum playerClass, final int level) {
        final PlayerBaseStatsEntity playerBaseStats = XMLService.getInstance().getEntity(PlayerBaseStatsEntityHolder.class).getPlayerBaseStatsByTargetClass(playerClass);
        for (final BaseStatEntity baseStat : playerBaseStats.getBaseStats()) {
            if (baseStat.getLevel() == level) {
                return baseStat;
            }
        }

        return null;
    }

    public final void affectCreatureBaseStats(final Creature creature, final BaseStatEntity baseStat) {
        CreatureBaseStats creatureBaseStats = creature.getCreatureBaseStats();
        if (creatureBaseStats == null) {
            creatureBaseStats = new CreatureBaseStats();
            creature.setCreatureBaseStats(creatureBaseStats);
        }

        if (baseStat == null) {
            return;
        }

        creatureBaseStats.setBaseHp(baseStat.getBaseHp());
        creatureBaseStats.setBaseMp(baseStat.getBaseMp());
        if (creature instanceof Player) {
            if (((Player) creature).getPlayerClass() == PlayerClassEnum.WARRIOR) {
                creatureBaseStats.setBaseRe(100);
            }
        }

        creatureBaseStats.setAttack(baseStat.getAttack());
        creatureBaseStats.setDefense(baseStat.getDefense());
        creatureBaseStats.setImpact(baseStat.getImpact());
        creatureBaseStats.setBalance(baseStat.getBalance());
        creatureBaseStats.setCritRate(baseStat.getCritRate());
        creatureBaseStats.setCritResistance(baseStat.getCritResistance());
        creatureBaseStats.setCritPower(baseStat.getCritPower());
        creatureBaseStats.setPower(baseStat.getPower());
        creatureBaseStats.setEndurance(baseStat.getEndurance());
        creatureBaseStats.setImpactFactor(baseStat.getImpactFactor());
        creatureBaseStats.setBalanceFactor(baseStat.getBalanceFactor());
        creatureBaseStats.setAttackSpeed(baseStat.getAttackSpeed());
        creatureBaseStats.setSpeed(baseStat.getSpeed());
        creatureBaseStats.setWeakeningResistance(baseStat.getWeakeningResistance());
        creatureBaseStats.setPeriodicResistance(baseStat.getPeriodicResistance());
        creatureBaseStats.setStunResistance(baseStat.getStunResistance());
        creatureBaseStats.setNaturalMpRegen(baseStat.getNaturalMpRegen());
        creatureBaseStats.setNaturalMpDegen(baseStat.getNaturalMpDegen());
        creatureBaseStats.setCombatHpRegen(baseStat.getCombatHpRegen());
        creatureBaseStats.setCombatMpRegen(baseStat.getCombatMpRegen());
    }

    public final void affectCreatureBonusStats(final Creature creature) {
        CreatureBonusStats creatureBonusStats = creature.getCreatureBonusStats();
        if (creatureBonusStats == null) {
            creatureBonusStats = new CreatureBonusStats();
            creature.setCreatureBonusStats(creatureBonusStats);
        }

        if (creature instanceof Player) {
            final Player player = (Player) creature;
            creatureBonusStats.setSpeed(0);
            if (player.getActiveMount() != null) {
                final int delta = player.getActiveMount().getSpeed() - creature.getCreatureCurrentStats().getSpeed();
                creatureBonusStats.setSpeed(delta);
            }
        }
    }

    public void affectCreatureCurrentStats(final Creature creature) {
        CreatureCurrentStats creatureCurrentStats = creature.getCreatureCurrentStats();
        if (creatureCurrentStats == null) {
            creatureCurrentStats = new CreatureCurrentStats();
            creature.setCreatureCurrentStats(creatureCurrentStats);
        }
        
        final CreatureBaseStats creatureBaseStats = creature.getCreatureBaseStats();
        creatureCurrentStats.setHp(creatureBaseStats.getBaseHp());
        creatureCurrentStats.setMp(creatureBaseStats.getBaseMp());
        creatureCurrentStats.setSpeed(creatureBaseStats.getSpeed());
    }
    
    /** SINGLETON */
    public static BaseStatService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final BaseStatService instance = new BaseStatService();
    }
}
