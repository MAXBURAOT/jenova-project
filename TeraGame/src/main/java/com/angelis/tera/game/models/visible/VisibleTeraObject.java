package com.angelis.tera.game.models.visible;

import com.angelis.tera.game.ai.AI;
import com.angelis.tera.game.controllers.Controller;
import com.angelis.tera.game.models.TeraObject;

public abstract class VisibleTeraObject extends TeraObject {
    
    private final KnownList knownList;
    private WorldPosition worldPosition;
    private AI<? extends VisibleTeraObject> ai;

    public VisibleTeraObject(final Integer id, final Controller<? extends VisibleTeraObject> controller) {
        super(id, controller);
        this.knownList = new KnownList(this);
    }

    public VisibleTeraObject(final VisibleTeraObject visibleTeraObject, final Controller<? extends VisibleTeraObject> controller) {
        super(visibleTeraObject, controller);
        this.knownList = new KnownList(this);
    }

    public KnownList getKnownList() {
        return knownList;
    }
    
    public WorldPosition getWorldPosition() {
        return worldPosition;
    }

    public void setWorldPosition(final WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    public AI<? extends VisibleTeraObject> getAi() {
        return ai;
    }

    public void setAi(final AI<? extends VisibleTeraObject> ai) {
        this.ai = ai;
    }
}
