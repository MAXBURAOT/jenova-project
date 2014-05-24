package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.command.AdminErrorMessageEnum;
import com.angelis.tera.game.models.creature.Monster;
import com.angelis.tera.game.models.creature.Npc;
import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.services.SpawnService;

public class SpawnCommand extends AbstractAdminCommand {
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final TeraCreature teraCreature = SpawnService.getInstance().getCreatureById(Integer.valueOf(arguments[0]));
        if (teraCreature == null) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.CREATURE_WITH_THIS_ID_NOT_FOUND.key);
            return;
        }
        
        TeraCreature spawnedCreature = null;
        if (teraCreature instanceof Npc) {
            spawnedCreature = new Npc((Npc) teraCreature);
        }
        else {
            spawnedCreature = new Monster((Monster) teraCreature);
        }

        spawnedCreature.setWorldPosition(connection.getActivePlayer().getWorldPosition().clone());
        SpawnService.getInstance().spawnCreature(spawnedCreature);
    }

    @Override
    public int getAccessLevel() {
        return 1;
    }

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            Integer.valueOf(arguments[0]);
            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "spawn [creatureId]";
    }
}
