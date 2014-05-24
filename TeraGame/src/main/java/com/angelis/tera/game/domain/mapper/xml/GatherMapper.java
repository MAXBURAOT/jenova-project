package com.angelis.tera.game.domain.mapper.xml;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.common.domain.mapper.xml.XMLListMapper;
import com.angelis.tera.common.entity.AbstractEntity;
import com.angelis.tera.common.model.AbstractModel;
import com.angelis.tera.game.models.gather.Gather;
import com.angelis.tera.game.models.gather.enums.GatherTypeNameEnum;
import com.angelis.tera.game.models.template.GatherTemplate;
import com.angelis.tera.game.models.visible.WorldPosition;
import com.angelis.tera.game.xml.entity.SpawnEntity;
import com.angelis.tera.game.xml.entity.gathers.GatherSpawnEntity;

public class GatherMapper extends XMLListMapper<GatherSpawnEntity, Gather> {

    @Override
    public GatherSpawnEntity map(final List<Gather> models, final AbstractEntity linkedEntity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Gather> map(final GatherSpawnEntity entity, final AbstractModel linkedModel) {
        final List<Gather> gathers = new FastList<>();
        
        for (final SpawnEntity spawnEntity : entity.getSpawns()) {
            final Gather gather = new Gather(entity.getId());
            
            // WORLD POSITION
            gather.setWorldPosition(new WorldPosition(spawnEntity.getMapId(), spawnEntity.getX(), spawnEntity.getY(), spawnEntity.getZ()));
            
            // TEMPLATE
            final GatherTemplate template = gather.getTemplate();
            template.setGrade(0); // TODO
            template.setGatherTypeName(GatherTypeNameEnum.NONE); // TODO
            
            gather.initGather();
            gathers.add(gather);
        }
        
        return gathers;
    }

    @Override
    public boolean equals(final Gather model, final GatherSpawnEntity entity) {
        return false;
    }
}
