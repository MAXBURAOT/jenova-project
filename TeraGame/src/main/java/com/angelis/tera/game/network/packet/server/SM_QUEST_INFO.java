package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;
import java.util.List;

import com.angelis.tera.game.models.creature.Npc;
import com.angelis.tera.game.models.quest.QuestData;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_QUEST_INFO extends TeraServerPacket {

    private final QuestData questData;
    private final List<Npc> npcs;
    private final boolean countersComplete;
    
    public SM_QUEST_INFO(final QuestData questData, final List<Npc> npcs, final boolean countersComplete) {
        this.questData = questData;
        this.npcs = npcs;
        this.countersComplete = countersComplete;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, 1);
        writeH(byteBuffer, 15);
        writeH(byteBuffer, 12);
        writeB(byteBuffer, "00010000");
        writeH(byteBuffer, 15);
        writeH(byteBuffer, 0);
        
        writeH(byteBuffer, 0); // npc size
        int npcShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeH(byteBuffer, (short) this.questData.getCounters().size());
        int countersShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        writeD(byteBuffer, this.questData.getQuest().getId());
        writeD(byteBuffer, this.questData.getQuest().getId()); //QuestUId???
        writeD(byteBuffer, this.questData.getStep() + 1);
        writeD(byteBuffer, 1);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 1); // visibility switch

        writeC(byteBuffer, 0);
        writeC(byteBuffer, this.countersComplete);
        writeC(byteBuffer, 1);

        writeD(byteBuffer, 0);

        for (final Npc npc : this.npcs) {
            this.writeBufferPosition(byteBuffer, npcShift, Size.H);

            writeH(byteBuffer, byteBuffer.position());
            npcShift = byteBuffer.position();
            writeH(byteBuffer, 0);

            writeF(byteBuffer, npc.getWorldPosition().getX());
            writeF(byteBuffer, npc.getWorldPosition().getY());
            writeF(byteBuffer, npc.getWorldPosition().getZ());
            writeD(byteBuffer, npc.getTemplate().getCreatureType());
            writeD(byteBuffer, npc.getId());
            writeD(byteBuffer, 13); //???
        }

        for (final int counter : this.questData.getCounters()) {
            this.writeBufferPosition(byteBuffer, countersShift, Size.H);

            writeH(byteBuffer, byteBuffer.position());
            countersShift = byteBuffer.position();
            writeH(byteBuffer, 0);

            writeD(byteBuffer, counter);
        }
    }
}
