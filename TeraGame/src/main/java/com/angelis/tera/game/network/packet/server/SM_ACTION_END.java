package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.action.Action;
import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.visible.WorldPosition;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_ACTION_END extends TeraServerPacket {

    private final Creature creature;
    private final Action action;
    
    public SM_ACTION_END(final Creature creature, final Action action) {
        this.creature = creature;
        this.action = action;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final WorldPosition worldPosition = this.creature.getWorldPosition();
        int model = 0;

        if (creature instanceof Player) {
            model = ((Player) creature).getRaceGenderClassValue();
        }
        else if (creature instanceof TeraCreature) {
            model = creature.getId();
        }

        writeUid(byteBuffer, this.creature);
        writeWorldPosition(byteBuffer, worldPosition);
        writeD(byteBuffer, model);
        writeD(byteBuffer, this.action.getSkillId() + 0x4000000);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, this.action.getUid());
    }
}
