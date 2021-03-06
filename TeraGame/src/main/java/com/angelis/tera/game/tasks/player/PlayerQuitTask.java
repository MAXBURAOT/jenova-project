package com.angelis.tera.game.tasks.player;

import com.angelis.tera.game.models.account.Account;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.server.SM_QUIT;
import com.angelis.tera.game.services.PlayerService;
import com.angelis.tera.game.tasks.AbstractTask;
import com.angelis.tera.game.tasks.TaskTypeEnum;

public class PlayerQuitTask extends AbstractTask<Player> {

    public PlayerQuitTask(final Player linkedObject) {
        super(linkedObject, TaskTypeEnum.PLAYER_QUIT);
    }

    @Override
    public void execute() {
        final Player player = this.linkedObject;
        if (player == null) {
            return;
        }
        
        final Account account = player.getAccount();
        if (account == null) {
            return;
        }
        
        final TeraGameConnection connection = account.getConnection();
        if (connection == null) {
            return;
        }
        
        connection.sendPacket(new SM_QUIT());
        PlayerService.getInstance().onPlayerDisconnect(player);
    }
}
