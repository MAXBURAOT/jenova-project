package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;
import java.util.Map.Entry;

import com.angelis.tera.game.models.enums.StorageTypeEnum;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.storage.Storage;
import com.angelis.tera.game.models.storage.StorageItem;
import com.angelis.tera.game.models.storage.enums.InventorySlotEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_PLAYER_APPEARANCE_CHANGE extends TeraServerPacket {

    private final Player player;
    
    public SM_PLAYER_APPEARANCE_CHANGE(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final Storage storage = this.player.getStorage(StorageTypeEnum.INVENTORY);

        writeUid(byteBuffer, this.player);
        for (final Entry<InventorySlotEnum, StorageItem> entry : storage.getStorageItems(Storage.PLAYER_EQUIPEMENT).entrySet()) {
            final StorageItem storageItem = entry.getValue();
            writeD(byteBuffer,  (storageItem != null) ? storageItem.getItem().getId() : 0);
        }
        
        writeB(byteBuffer, new byte[68]);
        writeC(byteBuffer, 1);
    }

}
