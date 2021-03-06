package com.angelis.tera.game.services;

import org.apache.log4j.Logger;

import com.angelis.tera.game.models.action.Action;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.packet.server.SM_ACTION_END;
import com.angelis.tera.game.network.packet.server.SM_ACTION_STAGE;
import com.angelis.tera.game.network.packet.server.SM_SKILL_RESULTS;

public class ActionService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(BaseStatService.class.getName());

    @Override
    public void onInit() {
        log.info("ActionService started");
    }

    @Override
    public void onDestroy() {
        log.info("ActionService stopped");
    }

    public void onActionStart(final Player player, final Action action) {
        player.getConnection().sendPacket(new SM_ACTION_STAGE(player, action));
    }

    public void onActionResults(final Player player, final Action action) {
        VisibleService.getInstance().sendPacketForVisible(player, new SM_SKILL_RESULTS(player, action));
    }
    
    public void onActionEnd(final Player player, final Action action) {
        player.getConnection().sendPacket(new SM_ACTION_END(player, action));
    }

    /** SINGLETON */
    public static ActionService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final ActionService instance = new ActionService();
    }
}
