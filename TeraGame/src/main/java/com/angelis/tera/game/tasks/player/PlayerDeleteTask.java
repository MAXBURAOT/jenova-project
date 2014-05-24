package com.angelis.tera.game.tasks.player;

import com.angelis.tera.game.delegate.database.PlayerDelegate;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.tasks.AbstractTask;
import com.angelis.tera.game.tasks.TaskTypeEnum;

public class PlayerDeleteTask extends AbstractTask<Player> {

    public PlayerDeleteTask(final Player linkedObject) {
        super(linkedObject, TaskTypeEnum.PLAYER_DELETE);
    }

    @Override
    public void execute() {
        PlayerDelegate.deletePlayerModel(this.linkedObject);
    }

}
