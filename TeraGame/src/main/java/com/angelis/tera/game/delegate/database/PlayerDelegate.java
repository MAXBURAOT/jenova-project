package com.angelis.tera.game.delegate.database;

import com.angelis.tera.common.database.dao.DAOManager;
import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.domain.mapper.database.DatabaseMapper;
import com.angelis.tera.game.database.dao.PlayerDAO;
import com.angelis.tera.game.database.entity.PlayerEntity;
import com.angelis.tera.game.domain.mapper.database.PlayerMapper;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.connection.TeraGameConnection;

public class PlayerDelegate {
    
    //------------------------------------------------------------
    //                       PlayerEntity
    //------------------------------------------------------------
    public static void createPlayerEntity(final PlayerEntity entity) {
        DAOManager.getDAO(PlayerDAO.class).create(entity);
    }
    
    public static PlayerEntity readPlayerEntityById(final Integer id) {
        return DAOManager.getDAO(PlayerDAO.class).read(id);
    }
    
    public static PlayerEntity readPlayerEntityByName(final String name) {
        return DAOManager.getDAO(PlayerDAO.class).readByName(name);
    }
    
    public static void updatePlayerEntity(final PlayerEntity entity) {
        DAOManager.getDAO(PlayerDAO.class).update(entity);
    }
    
    public static void deletePlayerEntity(final PlayerEntity entity) {
        DAOManager.getDAO(PlayerDAO.class).delete(entity);
    }
    
    //------------------------------------------------------------
    //                          Player
    //------------------------------------------------------------
    public static void createPlayerModel(final Player model) {
        final PlayerEntity playerEntity = MapperManager.getDatabaseMapper(PlayerMapper.class).map(model);
        PlayerDelegate.createPlayerEntity(playerEntity);
        
        PlayerDelegate.merge(playerEntity, model);
    }
    
    public static Player readPlayerModelById(final Integer id) {
        Player player = null;
        final PlayerEntity playerEntity = PlayerDelegate.readPlayerEntityById(id);
        if (playerEntity != null) {
            player = MapperManager.getDatabaseMapper(PlayerMapper.class).map(playerEntity);
        }
        return player;
    }
    
    public static Player readPlayerModelByName(final String name) {
        Player player = null;
        final PlayerEntity playerEntity = PlayerDelegate.readPlayerEntityByName(name);
        if (playerEntity != null) {
            player = MapperManager.getDatabaseMapper(PlayerMapper.class).map(playerEntity);
        }
        return player;
    }
    
    public static void updatePlayerModel(final Player model) {
        final PlayerEntity playerEntity = MapperManager.getDatabaseMapper(PlayerMapper.class).map(model);
        PlayerDelegate.updatePlayerEntity(playerEntity);
        
        PlayerDelegate.merge(playerEntity, model);
    }

    public static void deletePlayerModel(final Player model) {
        final PlayerEntity playerEntity = MapperManager.getDatabaseMapper(PlayerMapper.class).map(model);
        PlayerDelegate.deletePlayerEntity(playerEntity);
    }
    
    private static void merge(final PlayerEntity playerEntity, final Player model) {
        final TeraGameConnection connection = model.getAccount().getConnection();
        DatabaseMapper.merge(PlayerDelegate.readPlayerModelById(playerEntity.getId()), model);
        
        if (model.isOnline()) {
            model.getAccount().setConnection(connection);
        }
    }
}
