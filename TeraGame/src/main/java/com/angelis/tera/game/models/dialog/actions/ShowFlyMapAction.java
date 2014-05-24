package com.angelis.tera.game.models.dialog.actions;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.game.models.dialog.AbstractDialogAction;
import com.angelis.tera.game.models.dialog.Dialog;
import com.angelis.tera.game.models.pegasus.PegasusFlyPoint;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.packet.server.SM_PEGASUS_MAP_SHOW;

public class ShowFlyMapAction extends AbstractDialogAction {

    public ShowFlyMapAction(final Player player, final Dialog dialog) {
        super(player, dialog);
    }

    @Override
    public void action() {
        // TODO
        final List<PegasusFlyPoint> pegasusFlyPoints = new FastList<>();
        pegasusFlyPoints.add(new PegasusFlyPoint(1));
        
        this.player.getConnection().sendPacket(new SM_PEGASUS_MAP_SHOW(pegasusFlyPoints));
    }
}
