package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.enums.EmoteEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_SOCIAL extends TeraServerPacket {

    private final Player player;
    private final EmoteEnum emote;
    private final int duration;
    
    public SM_SOCIAL(final Player player, final EmoteEnum emote, final int duration) {
        this.player = player;
        this.emote = emote;
        this.duration = duration;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, player);
        writeD(byteBuffer, this.emote.value);
        writeD(byteBuffer, duration);
        writeC(byteBuffer, 0); //unk
    }
}
