package com.angelis.tera.game.tasks.player;

import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.services.PlayerService;
import com.angelis.tera.game.tasks.AbstractTask;
import com.angelis.tera.game.tasks.TaskTypeEnum;

public class PlayerAutoSaveTask extends AbstractTask<Player> {

    public PlayerAutoSaveTask(final Player linkedObject) {
        super(linkedObject, TaskTypeEnum.PLAYER_AUTO_SAVE);
    }

    @Override
    public void execute() {
        PlayerService.getInstance().onPlayerUpdate(this.linkedObject);
    }
}
