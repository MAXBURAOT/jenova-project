package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.command.AdminErrorMessageEnum;
import com.angelis.tera.game.models.account.Account;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.services.WorldService;

public class PromoteCommand extends AbstractAdminCommand {

    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final Player targetPlayer = WorldService.getInstance().getOnlinePlayerWithName(arguments[0]);
        if (targetPlayer == null) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_NOT_FOUND.key);
            return;
        }
        
        final Account targetAccount = targetPlayer.getAccount();
        if (targetAccount.getAccess() > connection.getAccount().getAccess()) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_HAS_MORE_RIGHTS_THAN_YOU.key);
            return;
        }
        
        targetAccount.setAccess(1);
    }

    @Override
    public int getAccessLevel() {
        return 0;
    }

    @Override
    public int getArgumentCount() {
        return 1;
    }
    
    @Override
    public boolean checkArguments(final String[] arguments) {
        return true;
    }

    @Override
    public String getSyntax() {
        return "promote [playerName]";
    }
}
