package com.angelis.tera.game.delegate.database;

import com.angelis.tera.common.database.dao.DAOManager;
import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.domain.mapper.database.DatabaseMapper;
import com.angelis.tera.game.database.dao.AccountDAO;
import com.angelis.tera.game.database.entity.AccountEntity;
import com.angelis.tera.game.domain.mapper.database.AccountMapper;
import com.angelis.tera.game.models.account.Account;

public class AccountDelegate {
    //------------------------------------------------------------
    //                       AccountEntity
    //------------------------------------------------------------
    public static void createAccountEntity(final AccountEntity entity) {
        DAOManager.getDAO(AccountDAO.class).create(entity);
    }
    
    public static AccountEntity readAccountEntityById(final Integer id) {
        return DAOManager.getDAO(AccountDAO.class).read(id);
    }
    
    public static AccountEntity readAccountEntityByLogin(final String login) {
        return DAOManager.getDAO(AccountDAO.class).readByLogin(login);
    }
    
    public static void updateAccountEntity(final AccountEntity entity) {
        DAOManager.getDAO(AccountDAO.class).update(entity);
    }
    
    //------------------------------------------------------------
    //                          Account
    //------------------------------------------------------------
    public static void createAccountModel(final Account model) {
        final AccountEntity accountEntity = MapperManager.getDatabaseMapper(AccountMapper.class).map(model);
        AccountDelegate.createAccountEntity(accountEntity);
        DatabaseMapper.merge(AccountDelegate.readAccountModelById(accountEntity.getId()), model);
    }
    
    public static Account readAccountModelById(final Integer id) {
        Account player = null;
        final AccountEntity accountEntity = AccountDelegate.readAccountEntityById(id);
        if (accountEntity != null) {
            player = MapperManager.getDatabaseMapper(AccountMapper.class).map(accountEntity);
        }
        return player;
    }
    
    public static Account readAccountModelByLogin(final String login) {
        Account account = null;
        final AccountEntity accountEntity = AccountDelegate.readAccountEntityByLogin(login);
        if (accountEntity != null) {
            account = MapperManager.getDatabaseMapper(AccountMapper.class).map(accountEntity);
        }
        return account;
    }
    
    public static void updateAccountModel(final Account model) {
        final AccountEntity accountEntity = MapperManager.getDatabaseMapper(AccountMapper.class).map(model);
        AccountDelegate.updateAccountEntity(accountEntity);
        DatabaseMapper.merge(AccountDelegate.readAccountModelById(accountEntity.getId()), model);
    }
}
