package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.services.PlayerService;

public class SM_PLAYER_EXPERIENCE_UPDATE extends TeraServerPacket {

    private final Player player;
    private final TeraCreature teraCreature;
    private final long added;
    
    public SM_PLAYER_EXPERIENCE_UPDATE(final Player player, final TeraCreature teraCreature, final long added) {
        this.player = player;
        this.teraCreature = teraCreature;
        this.added = added;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final int currentRestedExperience = this.player.getCurrentRestedExperience();
        final int maxRestedExperience = this.player.getMaxRestedExperience();
        
        writeQ(byteBuffer, this.added);
        writeQ(byteBuffer, this.player.getExperience());
        writeQ(byteBuffer, PlayerService.getInstance().getExpShown(this.player));
        writeQ(byteBuffer, PlayerService.getInstance().getExpNeeded(this.player));
        writeUid(byteBuffer, this.teraCreature);

        /*new EXP params like death penalty or something else*/

        float restedPercent = 0;
        if (currentRestedExperience > 0 && maxRestedExperience > 0) {
            restedPercent = currentRestedExperience/maxRestedExperience;
        }
        
        writeD(byteBuffer, (int) (this.added * restedPercent));
        writeD(byteBuffer, 0); 
        writeD(byteBuffer, currentRestedExperience);
        writeD(byteBuffer, maxRestedExperience); 
        writeB(byteBuffer, "0000803F00000000");
    }
}
