package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map.Entry;

import com.angelis.tera.game.models.creature.Npc;
import com.angelis.tera.game.models.quest.Quest;
import com.angelis.tera.game.models.quest.QuestEnv;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_QUEST_INFO extends TeraServerPacket {

    private final QuestEnv questEnv;
    private final List<Npc> npcs;
    private final boolean countersComplete;
    
    public SM_QUEST_INFO(final QuestEnv questEnv, final List<Npc> npcs, final boolean countersComplete) {
        this.questEnv = questEnv;
        this.npcs = npcs;
        this.countersComplete = countersComplete;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final Quest quest = this.questEnv.getQuest();

        writeH(byteBuffer, 1);
        writeH(byteBuffer, 15);
        writeH(byteBuffer, 12);
        writeB(byteBuffer, "00010000");
        writeH(byteBuffer, 15);
        writeH(byteBuffer, 0);
        
        writeH(byteBuffer, 0); // npc size
        int npcShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeH(byteBuffer, (short) this.questEnv.getCounters().size());
        int countersShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        writeD(byteBuffer, quest.getId());
        writeD(byteBuffer, quest.getId()); //QuestUId???
        writeD(byteBuffer, this.questEnv.getCurrentStep() + 1);
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

        for (final Entry<Integer, Integer> entry : this.questEnv.getCounters().entrySet()) {
            this.writeBufferPosition(byteBuffer, countersShift, Size.H);

            writeH(byteBuffer, byteBuffer.position());
            countersShift = byteBuffer.position();
            writeH(byteBuffer, 0);

            writeD(byteBuffer, entry.getValue());
        }
    }
}
