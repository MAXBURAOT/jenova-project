package com.angelis.tera.game.tasks.creature;

import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.tasks.AbstractTask;
import com.angelis.tera.game.tasks.TaskTypeEnum;

public class CreatureRespawnTask extends AbstractTask<Creature> {

    public CreatureRespawnTask(final Creature linkedObject) {
        super(linkedObject, TaskTypeEnum.TERA_OBJECT_RESPAWN);
    }

    @Override
    public void execute() {
        
    }
}
