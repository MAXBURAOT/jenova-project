package com.angelis.tera.game.controllers;

import com.angelis.tera.game.models.TeraObject;
import com.angelis.tera.game.models.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.models.enums.StorageTypeEnum;
import com.angelis.tera.game.models.gather.Gather;
import com.angelis.tera.game.models.gather.enums.GatherResultEnum;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.visible.VisibleTeraObject;
import com.angelis.tera.game.network.packet.server.SM_GATHER_END;
import com.angelis.tera.game.services.SpawnService;
import com.angelis.tera.game.services.StorageService;
import com.angelis.tera.game.services.VisibleService;

public class GatherController extends Controller<Gather> {

    @Override
    public void update(final VisibleObjectEventTypeEnum creatureEventType, final TeraObject object, final Object... data) {
        // TODO Auto-generated method stub
    }

    public void gather(final Player player) {
        this.owner.processGather();
        final int remainingGather = this.owner.getRemainingGather();
        if (remainingGather <= 0) {
            this.onDie(this.owner.getGatherer());
        }
        
        int itemId = 1001;
        if (this.owner.getId() >= 7 && this.owner.getId() <= 12) {
            itemId = 1005;
        }
        else if (this.owner.getId() >= 13 && this.owner.getId() <= 21) {
            itemId = 1009;
        }
        else if (this.owner.getId() >= 22 && this.owner.getId() <= 30) {
            itemId = 1013;
        }
        else if (this.owner.getId() >= 31 && this.owner.getId() <= 48) {
            itemId = 1017;
        }
        else if (this.owner.getId() >= 101 && this.owner.getId() <= 106) {
            itemId = 1002;
        }
        else if (this.owner.getId() >= 107 && this.owner.getId() <= 112) {
            itemId = 1006;
        }
        else if (this.owner.getId() >= 113 && this.owner.getId() <= 121) {
            itemId = 1010;
        }
        else if (this.owner.getId() >= 122 && this.owner.getId() <= 130) {
            itemId = 1014;
        }
        else if (this.owner.getId() >= 131 && this.owner.getId() <= 148) {
            itemId = 1018;
        }
        else if (this.owner.getId() >= 301 && this.owner.getId() <= 306) {
            itemId = 1003;
        }
        else if (this.owner.getId() >= 307 && this.owner.getId() <= 312) {
            itemId = 1007;
        }
        else if (this.owner.getId() >= 313 && this.owner.getId() <= 321) {
            itemId = 1011;
        }
        else if (this.owner.getId() >= 322 && this.owner.getId() <= 330) {
            itemId = 1015;
        }
        else if (this.owner.getId() >= 331 && this.owner.getId() <= 348) {
            itemId = 1019;
        }
        
        if (itemId > 0) {
            StorageService.getInstance().addItem(player, player.getStorage(StorageTypeEnum.INVENTORY), itemId, 1);
        }
    }

    @Override
    public void onStartAttack(final VisibleTeraObject target) {
        // Nothing to do
    }

    @Override
    public void onDamage(final VisibleTeraObject attacker, final int damage) {
        // Nothing to do
    }
    
    @Override
    public void onEndAttack() {
        // Nothing to do
    }

    @Override
    public void onDie(final VisibleTeraObject killer) {
        SpawnService.getInstance().despawnGather(this.owner, true);
    }

    @Override
    public void onRespawn() {
        this.owner.initGather();
        this.owner.getKnownList().update();
    }

    public void endGather(final GatherResultEnum gatherResult) {
        final Player player = this.owner.getGatherer();
        if (player == null) {
            return;
        }

        this.owner.setGatherer(null);
        VisibleService.getInstance().sendPacketForVisible(player, new SM_GATHER_END(player, this.owner, gatherResult));
    }
}
