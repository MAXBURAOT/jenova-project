package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.command.AdminErrorMessageEnum;
import com.angelis.tera.game.models.account.Account;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_EXPERIENCE_UPDATE;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_STATS;
import com.angelis.tera.game.services.PlayerService;
import com.angelis.tera.game.services.WorldService;

public class SetCommand extends AbstractAdminCommand {

    private enum CommandEnum {
        LEVEL,
        SPEED
    }

    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final Player targetPlayer = WorldService.getInstance().getOnlinePlayerWithName(arguments[1]);
        if (targetPlayer == null) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_NOT_FOUND.key);
            return;
        }
        
        final Account targetAccount = targetPlayer.getAccount();
        if (targetAccount.getAccess() > connection.getAccount().getAccess()) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_HAS_MORE_RIGHTS_THAN_YOU.key);
            return;
        }
        
        final CommandEnum command = CommandEnum.valueOf(arguments[0].toUpperCase());
        switch (command) {
            case LEVEL:
                PlayerService.getInstance().levelUpPlayer(targetPlayer, Integer.parseInt(arguments[2]));
                connection.sendPacket(new SM_PLAYER_EXPERIENCE_UPDATE(connection.getActivePlayer(), null, 0));
            break;
            
            case SPEED:
                targetPlayer.getCreatureCurrentStats().setSpeed(Integer.parseInt(arguments[2]));
                targetPlayer.getConnection().sendPacket(new SM_PLAYER_STATS(targetPlayer));
            break;
        }
    }

    @Override
    public int getAccessLevel() {
        return 1;
    }

    @Override
    public int getArgumentCount() {
        return 3;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            CommandEnum.valueOf(arguments[0].toUpperCase());
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "set {level|speed} [targetName] [value]";
    }
}
