package com.angelis.tera.game.command.user;

import java.util.EnumSet;

import com.angelis.tera.game.models.account.enums.AccountTypeEnum;
import com.angelis.tera.game.models.visible.WorldPosition;
import com.angelis.tera.game.network.connection.TeraGameConnection;

public class GpsCommand extends AbstractUserCommand {
    
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final WorldPosition worldPosition = connection.getActivePlayer().getWorldPosition();
        final String message = "mapId: "+worldPosition.getMapId()+" | x: "+worldPosition.getX()+" | y: "+worldPosition.getY()+" | z: "+worldPosition.getZ();
        this.sendErrorMessage(connection, message);
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
        return "gps";
    }
}
