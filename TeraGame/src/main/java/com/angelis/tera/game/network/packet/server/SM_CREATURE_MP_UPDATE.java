package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_CREATURE_MP_UPDATE extends TeraServerPacket {

    private final Creature creature;
    private final Creature attacker;
    private final int diff;
    
    public SM_CREATURE_MP_UPDATE(final Creature creature, final Creature attacker, final int diff) {
        this.creature = creature;
        this.attacker = attacker;
        this.diff = diff;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, creature.getCreatureCurrentStats().getMp());
        writeD(byteBuffer, creature.getCreatureBaseStats().getBaseMp());
        writeD(byteBuffer, this.diff);

        writeD(byteBuffer, attacker != null ? 0 : 10);
        writeUid(byteBuffer, creature);
        writeUid(byteBuffer, attacker);
    }
}
