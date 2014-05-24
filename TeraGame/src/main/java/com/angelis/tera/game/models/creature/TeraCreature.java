package com.angelis.tera.game.models.creature;

import com.angelis.tera.game.controllers.CreatureController;
import com.angelis.tera.game.models.template.HasTemplate;
import com.angelis.tera.game.models.template.TeraCreatureTemplate;

public abstract class TeraCreature extends Creature implements HasTemplate<TeraCreatureTemplate> {

    private final TeraCreatureTemplate template = new TeraCreatureTemplate();
    private final AggroList aggroList = new AggroList();
    
    public TeraCreature(final Integer id) {
        super(id, new CreatureController());
        this.getController().setOwner(this);
    }
    
    public TeraCreature(final TeraCreature teraCreature) {
        super(teraCreature, new CreatureController());
        this.template.copy(teraCreature.template);
        this.getController().setOwner(this);
    }

    @Override
    public CreatureController getController() {
        return (CreatureController) this.controller;
    }

    @Override
    public TeraCreatureTemplate getTemplate() {
        return this.template;
    }
    
    public AggroList getAggroList() {
        return aggroList;
    }

    public final int getFullId() {
        return this.getId() + this.getTemplate().getHuntingZoneId()*100000;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof TeraCreature)) {
            return false;
        }
        
        return true;
    }
}
