package com.angelis.tera.game.database.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.angelis.tera.common.database.entity.AbstractDatabaseEntity;

@Entity
@Table(name = "player_quests")
public class QuestEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = -5365265045972985699L;

    @Column(name = "questId")
    private int questId;

    @Column(name = "step")
    private int step;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    public QuestEntity(final Integer id) {
        super(id);
    }

    public QuestEntity() {

    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(final int questId) {
        this.questId = questId;
    }

    public int getStep() {
        return step;
    }

    public void setStep(final int step) {
        this.step = step;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(final PlayerEntity player) {
        this.player = player;
    }
}
