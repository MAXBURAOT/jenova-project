package com.angelis.tera.game.services;

import org.apache.log4j.Logger;

import com.angelis.tera.common.utils.Function;
import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.models.group.Group;
import com.angelis.tera.game.models.player.Player;

public class GroupService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(DropService.class.getName());

    @Override
    public void onInit() {
        log.info("GroupService started");
    }

    @Override
    public void onDestroy() {
        log.info("GroupService stopped");
    }

    public void onGroupUpdateExperience(final Group group, final TeraCreature creature, final int totalExperience) {
        final int experience = totalExperience / group.size();
        final int bonusExperience = (group.size() > 2) ? experience * (group.size()-2)*10/100 : 0;

        group.doOnEachPlayer(new Function<Player>() {
            @Override
            public void call(final Player player) {
                PlayerService.getInstance().onPlayerUpdateExperience(player, creature, experience+bonusExperience);
            }
        });
    }

    /** SINGLETON */
    public static GroupService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final GroupService instance = new GroupService();
    }
}
