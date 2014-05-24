package com.angelis.tera.game.models.creature;

import java.util.Set;

import javolution.util.FastSet;

import com.angelis.tera.game.controllers.Controller;
import com.angelis.tera.game.models.abnormality.Abnormality;
import com.angelis.tera.game.models.template.Template;
import com.angelis.tera.game.models.visible.VisibleTeraObject;
import com.angelis.tera.game.services.BaseStatService;


public abstract class Creature extends VisibleTeraObject {
    
    private CreatureBaseStats creatureBaseStats;
    private CreatureCurrentStats creatureCurrentStats;
    private CreatureBonusStats creatureBonusStats;
    private int level;
    private Creature target;
    private final Set<Abnormality> abnormalities = new FastSet<>();
    
    public Creature(final Integer id, final Controller<? extends Creature> controller) {
        super(id, controller);
    }

    public Creature(final Creature creature, final Controller<? extends Creature> controller) {
        super(creature, controller);
        this.creatureBaseStats = creature.creatureBaseStats;
        this.level = creature.level;
        BaseStatService.getInstance().affectCreatureCurrentStats(this);
    }

    public CreatureBaseStats getCreatureBaseStats() {
        return creatureBaseStats;
    }

    public void setCreatureBaseStats(final CreatureBaseStats creatureBaseStats) {
        this.creatureBaseStats = creatureBaseStats;
    }

    public CreatureCurrentStats getCreatureCurrentStats() {
        return creatureCurrentStats;
    }

    public void setCreatureCurrentStats(final CreatureCurrentStats creatureCurrentStats) {
        this.creatureCurrentStats = creatureCurrentStats;
    }
    
    public CreatureBonusStats getCreatureBonusStats() {
        return creatureBonusStats;
    }

    public void setCreatureBonusStats(final CreatureBonusStats creatureBonusStats) {
        this.creatureBonusStats = creatureBonusStats;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public Creature getTarget() {
        return target;
    }

    public void setTarget(final Creature target) {
        this.target = target;
    }
    
    public Set<Abnormality> getAbnormalities() {
        return abnormalities;
    }

    public abstract Template getTemplate();
}
