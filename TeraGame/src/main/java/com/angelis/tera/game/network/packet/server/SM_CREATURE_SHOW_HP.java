package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.creature.CreatureBaseStats;
import com.angelis.tera.game.models.creature.CreatureCurrentStats;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_CREATURE_SHOW_HP extends TeraServerPacket {

    private final Creature creature;
    private final boolean isFriendly;
    
    public SM_CREATURE_SHOW_HP(final Creature creature, final boolean isFriendly) {
        this.creature = creature;
        this.isFriendly = isFriendly;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final CreatureBaseStats creatureBaseStats = this.creature.getCreatureBaseStats();
        final CreatureCurrentStats creatureCurrentStats = this.creature.getCreatureCurrentStats();
        
        writeUid(byteBuffer, this.creature);
        writeF(byteBuffer, (creatureCurrentStats.getHp()/(creatureBaseStats.getBaseHp()/100F))/100F);
        writeC(byteBuffer, (byte) (this.isFriendly ? 0 : 1));
        writeD(byteBuffer, 0x00000000);
        writeD(byteBuffer, 0x00000000);
        writeD(byteBuffer, 0x401F0000);
        writeD(byteBuffer, 0x03000000);
    }
}
