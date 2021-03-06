package com.angelis.tera.game.tasks;

import com.angelis.tera.common.utils.Pair;
import com.angelis.tera.game.models.abnormality.Abnormality;
import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.network.packet.server.SM_ABNORMALITY_END;
import com.angelis.tera.game.services.VisibleService;

public class CreatureEndAbnormality extends AbstractTask<Pair<Creature, Abnormality>> {

    public CreatureEndAbnormality(final Pair<Creature, Abnormality> linkedObject) {
        super(linkedObject, TaskTypeEnum.PLAYER_ABNORMALITY_END);
    }

    @Override
    public void execute() {
        this.linkedObject.first.getAbnormalities().remove(this.linkedObject.second);
        VisibleService.getInstance().sendPacketForVisible(this.linkedObject.first, new SM_ABNORMALITY_END(this.linkedObject.second));
    }
}
