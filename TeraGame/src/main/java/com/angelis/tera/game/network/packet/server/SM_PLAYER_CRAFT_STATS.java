package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.player.craft.CraftStatInfo;
import com.angelis.tera.game.models.player.craft.CraftStats;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_PLAYER_CRAFT_STATS extends TeraServerPacket {

    private final CraftStats craftStats;
    
    public SM_PLAYER_CRAFT_STATS(final CraftStats craftStats) {
        this.craftStats = craftStats;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, 7); // craft skill counter?
        writeH(byteBuffer, 0); //first skill shift
        writeQ(byteBuffer, 0);
        writeB(byteBuffer, "009A0100000E000000");

        this.writeBufferPosition(byteBuffer, 6, Size.H);

        int i = 0;
        for (final CraftStatInfo craftStatInfo : craftStats.getCraftStatInfos()) {
            final int position = byteBuffer.position();
            writeH(byteBuffer, position);
            writeH(byteBuffer, 0); // next skill shift
            
            writeD(byteBuffer, craftStatInfo.getCraftType().value);
            writeD(byteBuffer, craftStatInfo.getCraftType().value);
            writeD(byteBuffer, craftStatInfo.getLevel());
            
            if (++i < craftStats.getCraftStatInfos().size()) {
                this.writeBufferPosition(byteBuffer, position+2, Size.H);
            }
        }
    }

}
