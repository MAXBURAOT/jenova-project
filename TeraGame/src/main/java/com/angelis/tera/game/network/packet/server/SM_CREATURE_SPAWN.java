package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.creature.CreatureCurrentStats;
import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.models.template.TeraCreatureTemplate;
import com.angelis.tera.game.models.visible.WorldPosition;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_CREATURE_SPAWN extends TeraServerPacket {

    public final TeraCreature teraCreature;
    
    public SM_CREATURE_SPAWN(final TeraCreature teraCreature) {
        this.teraCreature = teraCreature;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final WorldPosition worldPosition = this.teraCreature.getWorldPosition();
        final CreatureCurrentStats creatureCurrentStats = this.teraCreature.getCreatureCurrentStats();
        final TeraCreatureTemplate template = this.teraCreature.getTemplate();

        writeD(byteBuffer, 0); //???
        writeH(byteBuffer, 0); //Shit shift

        writeUid(byteBuffer, this.teraCreature);
        writeUid(byteBuffer, this.teraCreature.getTarget());

        writeF(byteBuffer, worldPosition.getX());
        writeF(byteBuffer, worldPosition.getY());
        writeF(byteBuffer, worldPosition.getZ());
        writeH(byteBuffer, worldPosition.getHeading());
        writeD(byteBuffer, creatureCurrentStats.getSpeed());
        writeD(byteBuffer, this.teraCreature.getId());
        writeH(byteBuffer, template.getHuntingZoneId());
        writeD(byteBuffer, template.getModelId());

        writeB(byteBuffer, "000000000500000001010100000000000000000000000000000000000000");

        writeUid(byteBuffer, this.teraCreature.getTarget());

        writeB(byteBuffer, "00000000000000000000000000000000000000000000");

        this.writeBufferPosition(byteBuffer, 8, Size.H);

        writeB(byteBuffer, "D8C5ACB974CE0000"); //Shit
    }
}
