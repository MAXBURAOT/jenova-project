package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.creature.Creature;
import com.angelis.tera.game.models.quest.enums.QuestNpcIconEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_QUEST_VILLAGER_INFO extends TeraServerPacket {

    private final Creature creature;
    private final QuestNpcIconEnum questNpcIcon;
    
    public SM_QUEST_VILLAGER_INFO(final Creature creature, final QuestNpcIconEnum questNpcIcon) {
        this.creature = creature;
        this.questNpcIcon = questNpcIcon;
    }   

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.creature);
        writeD(byteBuffer, questNpcIcon.value);
        writeC(byteBuffer, 1);
    }
}
