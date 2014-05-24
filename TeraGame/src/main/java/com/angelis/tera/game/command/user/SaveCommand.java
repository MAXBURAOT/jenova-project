package com.angelis.tera.game.command.user;

import java.util.EnumSet;

import com.angelis.tera.game.models.account.enums.AccountTypeEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.services.PlayerService;

public class SaveCommand extends AbstractUserCommand {

    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        PlayerService.getInstance().onPlayerUpdate(connection.getActivePlayer());
    }

    @Override
    public EnumSet<? extends AccountTypeEnum> getAllowedAccountTypes() {
        return EnumSet.allOf(AccountTypeEnum.class);
    }
    
    @Override
    public int getArgumentCount() {
        return 0;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        return true;
    }

    @Override
    public String getSyntax() {
        return "save";
    }
}
