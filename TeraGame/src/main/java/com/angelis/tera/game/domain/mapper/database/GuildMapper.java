package com.angelis.tera.game.domain.mapper.database;

import com.angelis.tera.common.domain.mapper.database.DatabaseMapper;
import com.angelis.tera.common.entity.AbstractEntity;
import com.angelis.tera.common.model.AbstractModel;
import com.angelis.tera.game.database.entity.GuildEntity;
import com.angelis.tera.game.models.guild.Guild;

public class GuildMapper extends DatabaseMapper<GuildEntity, Guild> {

    @Override
    public GuildEntity map(final Guild model, final AbstractEntity linkedEntity) {
        return null;
    }
    
    @Override
    public Guild map(final GuildEntity entity, final AbstractModel linkedModel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals(final Guild model, final GuildEntity entity) {
        // TODO Auto-generated method stub
        return false;
    }
}
