package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.enums.ObjectFamilyEnum;
import com.angelis.tera.game.models.item.Item;
import com.angelis.tera.game.models.storage.enums.ViewModeEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.services.ItemService;
import com.angelis.tera.game.services.ObjectIDService;

public class CM_PLAYER_EQUIPEMENT_ITEM_INFO extends TeraClientPacket {
    
    private ViewModeEnum viewMode;
    private int itemUid;
    private String playerName;
    
    public CM_PLAYER_EQUIPEMENT_ITEM_INFO(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readH(); // 0x26
        this.viewMode = ViewModeEnum.fromValue(readD());
        this.itemUid = readD();
        readB(24); // unk
        this.playerName = readS();
    }

    @Override
    protected void runImpl() {
        ItemService.getInstance().showItemInfo(this.getConnection().getActivePlayer(), this.viewMode, (Item) ObjectIDService.getInstance().getObjectFromUId(ObjectFamilyEnum.INVENTORY_ITEM, this.itemUid), this.playerName);
    }
}
