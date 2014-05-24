package com.angelis.tera.game.domain.mapper.xml;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.common.domain.mapper.xml.XMLListMapper;
import com.angelis.tera.common.entity.AbstractEntity;
import com.angelis.tera.common.model.AbstractModel;
import com.angelis.tera.game.models.creature.CreatureBaseStats;
import com.angelis.tera.game.models.creature.CreatureBonusStats;
import com.angelis.tera.game.models.creature.CreatureCurrentStats;
import com.angelis.tera.game.models.creature.Monster;
import com.angelis.tera.game.models.creature.Npc;
import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.models.visible.WorldPosition;
import com.angelis.tera.game.xml.entity.creatures.CreatureSpawnEntity;
import com.angelis.tera.game.xml.entity.creatures.TeraCreatureSpawnEntity;

public class CreatureSpawnMapper extends XMLListMapper<CreatureSpawnEntity, TeraCreature> {

    @Override
    public CreatureSpawnEntity map(final List<TeraCreature> model, final AbstractEntity linkedEntity) {
        return null;
    }

    @Override
    public List<TeraCreature> map(final CreatureSpawnEntity entity, final AbstractModel linkedModel) {
        final List<TeraCreature> creatures = new FastList<>();
        
        for (final TeraCreatureSpawnEntity spawnEntity : entity.getCreatureSpawns()) {
            TeraCreature teraCreature = null;
            if (entity.isOffensive()) {
                teraCreature = new Monster(entity.getId());
            } else {
                teraCreature = new Npc(entity.getId());
            }
            
            // World position
            final WorldPosition worldPosition = new WorldPosition(spawnEntity.getMapId(), spawnEntity.getX(), spawnEntity.getY(), spawnEntity.getZ(), spawnEntity.getHeading());
            teraCreature.setWorldPosition(worldPosition);
            
            // BASE STATS
            final CreatureBaseStats creatureBaseStats = new CreatureBaseStats();
            teraCreature.setCreatureBaseStats(creatureBaseStats);
            
            // CURRENT STATS
            final CreatureCurrentStats creatureCurrentStats = new CreatureCurrentStats();
            creatureCurrentStats.setHp(creatureBaseStats.getBaseHp());
            teraCreature.setCreatureCurrentStats(creatureCurrentStats);
            
            // BONUS STATS
            final CreatureBonusStats creatureBonusStats = new CreatureBonusStats();
            teraCreature.setCreatureBonusStats(creatureBonusStats);
            
            /*if (aggresive) {
                AI<Creature> ai = new RandomAI(teraCreature);
                teraCreature.setAi(ai);
                ai.start();
            }*/

            creatures.add(teraCreature);
        }
        
        return creatures;
    }

    @Override
    public boolean equals(final TeraCreature model, final CreatureSpawnEntity entity) {
        return false;
    }
}
