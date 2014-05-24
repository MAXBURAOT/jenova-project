package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;
import java.util.Map.Entry;

import com.angelis.tera.game.models.enums.StorageTypeEnum;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.storage.Storage;
import com.angelis.tera.game.models.storage.StorageItem;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_PLAYER_STORAGE extends TeraServerPacket {

    private final Player player;
    private final boolean isInventory;
    
    public SM_PLAYER_STORAGE(final Player player, final boolean isInventory) {
        this.player = player;
        this.isInventory = isInventory;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final Storage storage = player.getStorage(StorageTypeEnum.INVENTORY); 
                
        writeH(byteBuffer, storage.getStorageItems().size()); // item count
        
        final int firstItemShift = byteBuffer.position();
        writeH(byteBuffer, 0); //first item shift
        
        writeUid(byteBuffer, player);
        writeQ(byteBuffer, player.getMoney());
        
        writeC(byteBuffer, isInventory);
        
        writeH(byteBuffer, 1); //Unk
        writeD(byteBuffer, storage.getSize()); // storage max size

        writeD(byteBuffer, storage.getMaxEquipedLevel(false));
        writeD(byteBuffer, storage.getMaxEquipedLevel(true));
        writeB(byteBuffer, "000000000000000000000000");
        
        this.writeBufferPosition(byteBuffer, firstItemShift, Size.H);
        
        int index = 0;
        for (final Entry<Integer, StorageItem> entry : storage.getStorageItems().entrySet()) {
            final int nowShift = byteBuffer.position();
            writeH(byteBuffer, nowShift); //Now shift
            
            final int nextShift = byteBuffer.position();
            writeH(byteBuffer, 0); // next shift
            
            writeD(byteBuffer, 0);
            
            final int nextShift2 = byteBuffer.position();
            writeH(byteBuffer, 0); // next shift - 2
            
            writeD(byteBuffer, entry.getValue().getItem().getId());
            writeQ(byteBuffer, entry.getValue().getItem().getUid());
            
            writeD(byteBuffer, this.player.getId());
            writeD(byteBuffer, 0);
            
            writeD(byteBuffer, entry.getKey()); // item slot
            writeD(byteBuffer, 0);
            
            writeD(byteBuffer, entry.getValue().getCount());
            
            writeD(byteBuffer, 0);
            writeD(byteBuffer, 0);
            writeD(byteBuffer, 1); //Binded?
            writeD(byteBuffer, 0);
            writeD(byteBuffer, 0);
            writeD(byteBuffer, 0);
            writeD(byteBuffer, 0);
            
            writeC(byteBuffer, 0);
            
            writeD(byteBuffer, 0); //EffectId
            writeD(byteBuffer, 0); //EffectId
            writeD(byteBuffer, 0); //EffectId
            writeD(byteBuffer, 0); //EffectId
            
            writeB(byteBuffer, new byte[60]); //unk
            writeD(byteBuffer, 1); //Item Level
            writeD(byteBuffer, 0);
            
            if (++index < storage.getStorageItems().size()) {
                final int position = byteBuffer.position();
                this.writeBufferPosition(byteBuffer, position, nextShift, Size.H);
                this.writeBufferPosition(byteBuffer, position-2, nextShift2, Size.H);
            }
        }
    }
}
