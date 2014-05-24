package com.angelis.tera.game.database.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.angelis.tera.common.database.entity.AbstractDatabaseEntity;

@Entity
@Table(name = "player_skills")
public class SkillEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = 5707897773820333174L;

    @Column
    private int skillId;
    
    @Column
    private int level;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;
    
    public SkillEntity(final Integer id) {
        super(id);
    }
    
    public SkillEntity() {
        
    }
    
    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(final int skillId) {
        this.skillId = skillId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(final PlayerEntity player) {
        this.player = player;
    }
}
