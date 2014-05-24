package com.angelis.tera.game.domain.mapper.database;

import com.angelis.tera.common.domain.mapper.database.DatabaseMapper;
import com.angelis.tera.common.entity.AbstractEntity;
import com.angelis.tera.common.model.AbstractModel;
import com.angelis.tera.game.database.entity.FriendEntity;
import com.angelis.tera.game.models.player.PlayerRelation;

public class PlayerRelationMapper extends DatabaseMapper<FriendEntity, PlayerRelation> {

    @Override
    public FriendEntity map(final PlayerRelation model, final AbstractEntity linkedEntity) {
        final FriendEntity friendEntity = new FriendEntity(model.getId());
        
        return friendEntity;
    }

    @Override
    public PlayerRelation map(final FriendEntity entity, final AbstractModel linkedModel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals(final PlayerRelation model, final FriendEntity entity) {
        // TODO Auto-generated method stub
        return false;
    }

}
