package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.creature.Npc;
import com.angelis.tera.game.models.quest.Quest;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

/***
 * This class is used to show dialog above npc
 */
public class SM_QUEST_BALLOON extends TeraServerPacket {

    private final Npc npc;
    private final Quest quest;
    
    public SM_QUEST_BALLOON(final Npc npc, final Quest quest) {
        this.npc = npc;
        this.quest = quest;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeB(byteBuffer, "0E00");
        writeUid(byteBuffer, this.npc);
        writeS(byteBuffer, "@quest:"+(this.quest.getQuestFullId()+1));
    }
}
