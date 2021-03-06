package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.enums.StorageTypeEnum;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.storage.Storage;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.services.StorageService;

public class CM_INVENTORY_ORDER extends TeraClientPacket {

    public CM_INVENTORY_ORDER(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD(); // 00000000
        readD(); // FFFFFFFF
        readD(); // FFFFFFFF
        
    }

    @Override
    protected void runImpl() {
        final Player player = this.getConnection().getActivePlayer();
        final Storage storage = player.getStorage(StorageTypeEnum.INVENTORY);
        StorageService.getInstance().reorderStorage(player, storage);
    }

}
