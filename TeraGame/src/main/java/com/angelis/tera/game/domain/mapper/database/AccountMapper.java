package com.angelis.tera.game.domain.mapper.database;

import java.util.List;
import java.util.Set;

import javolution.util.FastList;
import javolution.util.FastSet;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.domain.mapper.database.DatabaseMapper;
import com.angelis.tera.common.entity.AbstractEntity;
import com.angelis.tera.common.model.AbstractModel;
import com.angelis.tera.game.database.entity.AccountEntity;
import com.angelis.tera.game.database.entity.PlayerEntity;
import com.angelis.tera.game.models.account.Account;
import com.angelis.tera.game.models.account.Options;
import com.angelis.tera.game.models.player.Player;

public class AccountMapper extends DatabaseMapper<AccountEntity, Account> {
    
    @Override
    public AccountEntity map(final Account model, final AbstractEntity linkedEntity) {
        final AccountEntity accountEntity = new AccountEntity(model.getId());
        
        // DIRECT
        accountEntity.setLogin(model.getLogin());
        accountEntity.setPassword(model.getPassword());
        accountEntity.setBanned(model.isBanned());
        accountEntity.setAccess(model.getAccess());
        accountEntity.setAccountType(model.getAccountType());
        accountEntity.setLocale(model.getLocale());
        
        // OPTION
        final Options option = model.getOptions();
        accountEntity.setDisplayRange(option.getDisplayRange());
        
        // PLAYER
        final Set<PlayerEntity> players = new FastSet<>();
        for (final Player player : model.getPlayers()) {
            if (linkedEntity instanceof PlayerEntity && MapperManager.getDatabaseMapper(PlayerMapper.class).equals(player, (PlayerEntity) linkedEntity)) {
                players.add((PlayerEntity) linkedEntity);
            } else {
                players.add(MapperManager.getDatabaseMapper(PlayerMapper.class).map(player, accountEntity));
            }
        }
        accountEntity.setPlayers(players);
        
        return accountEntity;
    }
    
    @Override
    public Account map(final AccountEntity model, final AbstractModel linkedModel) {
        final Account account = new Account(model.getId());
        
        // DIRECT
        account.setLogin(model.getLogin());
        account.setPassword(model.getPassword());
        account.setBanned(model.isBanned());
        account.setAccess(model.getAccess());
        account.setAccountType(model.getAccountType());
        account.setLocale(model.getLocale());
        
        // OPTION
        final Options option = new Options();
        option.setDisplayRange(model.getDisplayRange());
        account.setOptions(option);
        
        // PLAYER
        final List<Player> players = new FastList<Player>();
        for (final PlayerEntity playerEntity : model.getPlayers()) {
            if (linkedModel instanceof Player && MapperManager.getDatabaseMapper(PlayerMapper.class).equals((Player) linkedModel, playerEntity)) {
                players.add((Player) linkedModel);
            } else {
                players.add(MapperManager.getDatabaseMapper(PlayerMapper.class).map(playerEntity, account));
            }
            
        }
        account.setPlayers(players);
        
        return account;
    }

    @Override
    public boolean equals(final Account model, final AccountEntity entity) {
        return model.getLogin().equals(entity.getLogin());
    }
}
