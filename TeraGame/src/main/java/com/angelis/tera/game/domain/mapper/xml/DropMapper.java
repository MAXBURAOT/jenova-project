package com.angelis.tera.game.domain.mapper.xml;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.common.domain.mapper.xml.XMLListMapper;
import com.angelis.tera.common.entity.AbstractEntity;
import com.angelis.tera.common.model.AbstractModel;
import com.angelis.tera.game.models.drop.Drop;
import com.angelis.tera.game.xml.entity.DropEntity;
import com.angelis.tera.game.xml.entity.creatures.CreatureDropsEntity;

public class DropMapper extends XMLListMapper<CreatureDropsEntity, Drop> {

    @Override
    public CreatureDropsEntity map(final List<Drop> models, final AbstractEntity linkedEntity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Drop> map(final CreatureDropsEntity entity, final AbstractModel linkedModel) {
        final List<Drop> drops = new FastList<>();
        for (final DropEntity dropEntity : entity.getDrops()) {
            final Drop drop = new Drop();
            drop.setDropChance(dropEntity.getDropChance());
            drop.setItemId(dropEntity.getItemId());
            drop.setMaxAmount(dropEntity.getMaxAmount());
            drop.setMinAmount(dropEntity.getMinAmount());
            
            drops.add(drop);
        }
        return drops;
    }

    @Override
    public boolean equals(final Drop model, final CreatureDropsEntity entity) {
        // TODO Auto-generated method stub
        return false;
    }

}
