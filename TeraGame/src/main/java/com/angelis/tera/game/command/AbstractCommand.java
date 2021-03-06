package com.angelis.tera.game.command;

import com.angelis.tera.game.models.enums.ChatTypeEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_CHAT;
import com.angelis.tera.game.services.I18nService;

public abstract class AbstractCommand {
    
    public final void sendErrorMessage(final TeraGameConnection connection, final String message) {
        connection.sendPacket(new SM_PLAYER_CHAT(null, message, ChatTypeEnum.SYSTEM));
    }
    
    public final void sendTranslatedErrorMessage(final TeraGameConnection connection, final String key, final Object... args) {
        final String message = I18nService.getInstance().translate(connection.getAccount().getLocale(), key, args);
        this.sendErrorMessage(connection, message);
    }
    
    public abstract void execute(TeraGameConnection connection, String[] arguments);
    public abstract int getArgumentCount();
    public abstract boolean checkArguments(String[] arguments);
    public abstract String getSyntax();
}
