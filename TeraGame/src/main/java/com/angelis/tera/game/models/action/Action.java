package com.angelis.tera.game.models.action;

import com.angelis.tera.common.utils.Point3D;
import com.angelis.tera.game.models.AbstractUniqueTeraModel;
import com.angelis.tera.game.models.attack.enums.AttackTypeEnum;
import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.visible.WorldPosition;

public class Action extends AbstractUniqueTeraModel {

    private Creature target;
    private int skillId;
    private AttackTypeEnum attackType;
    private WorldPosition startPosition;
    private Point3D endPosition;
    private int stage;

    public Action() {
        super(0);
    }

    public Creature getTarget() {
        return target;
    }

    public void setTarget(final Creature target) {
        this.target = target;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(final int skillId) {
        this.skillId = skillId;
    }

    public AttackTypeEnum getAttackType() {
        return attackType;
    }

    public void setAttackType(final AttackTypeEnum attackType) {
        this.attackType = attackType;
    }

    public WorldPosition getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(final WorldPosition startPosition) {
        this.startPosition = startPosition;
    }

    public Point3D getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(final Point3D endPosition) {
        this.endPosition = endPosition;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(final int stage) {
        this.stage = stage;
    }

    public Object getVisualEffect() {
        // TODO Auto-generated method stub
        return null;
    }
}
