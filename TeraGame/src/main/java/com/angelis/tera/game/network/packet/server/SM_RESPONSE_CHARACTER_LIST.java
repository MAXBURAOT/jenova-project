package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.account.Account;
import com.angelis.tera.game.models.enums.StorageTypeEnum;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.storage.Storage;
import com.angelis.tera.game.models.storage.enums.InventorySlotEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_RESPONSE_CHARACTER_LIST extends TeraServerPacket {

    private final Account account;
    
    public SM_RESPONSE_CHARACTER_LIST(final Account account) {
        this.account = account;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, (short) account.getPlayers().size());
        writeH(byteBuffer, (short) account.getPlayers().size() == 0 ? 0 : 39);
        writeB(byteBuffer, new byte[9]);
        writeD(byteBuffer, account.getAccountType().maxPlayerCount); // max character count
        writeD(byteBuffer, 1);
        writeH(byteBuffer, 0);
        writeB(byteBuffer, "280000000000000018000000");
        
        for (int i = 0 ; i < this.account.getPlayers().size() ; i++) {
            final Player player = this.account.getPlayers().get(i);
            
            final short shift = (short) byteBuffer.position();
            writeH(byteBuffer, shift);
            writeH(byteBuffer, 0); // next shift
            
            writeD(byteBuffer, (short) 0);
            
            writeH(byteBuffer, 0); //Name shift
            writeH(byteBuffer, 0); //Details shift
            writeH(byteBuffer, player.getPlayerAppearance().getDetails1().length); //Details length
            writeH(byteBuffer, 0); //Details shift2
            writeH(byteBuffer, player.getPlayerAppearance().getDetails2().length); //Details length2

            writeD(byteBuffer, player.getId()); //PlayerId
            writeD(byteBuffer, player.getGender().getValue()); //Gender
            writeD(byteBuffer, player.getRace().value); //Race
            writeD(byteBuffer, player.getPlayerClass().value); //Class
            writeD(byteBuffer, player.getLevel()); //Level
            
            writeB(byteBuffer, "16560000F3070000"); //A0860100A0860100 Unknown?!?
            
            writeB(byteBuffer, player.getZoneData()); // player zone data
            writeD(byteBuffer, player.getLastOnlineTime() != null ? (int) (player.getLastOnlineTime().getTime()/1000) : 0);
            
            writeB(byteBuffer, "00000000008F5F01000000000018D7D4AC");
            
            final Storage storage = player.getStorage(StorageTypeEnum.INVENTORY);
            writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.WEAPON) != null ? storage.getStorageItemByInventorySlot(InventorySlotEnum.WEAPON).getItem().getId() : 0);
            writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.EARING_LEFT) != null ? storage.getStorageItemByInventorySlot(InventorySlotEnum.EARING_LEFT).getItem().getId() : 0);
            writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.EARING_RIGHT) != null ? storage.getStorageItemByInventorySlot(InventorySlotEnum.EARING_RIGHT).getItem().getId() : 0);
            writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.ARMOR) != null ? storage.getStorageItemByInventorySlot(InventorySlotEnum.ARMOR).getItem().getId() : 0);
            writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.GLOVES) != null ? storage.getStorageItemByInventorySlot(InventorySlotEnum.GLOVES).getItem().getId() : 0);
            writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.FOOT) != null ? storage.getStorageItemByInventorySlot(InventorySlotEnum.FOOT).getItem().getId() : 0);
            writeD(byteBuffer, 0); //Item (unknownSlot)?
            writeD(byteBuffer, 0); //Item (ring1 left?)
            writeD(byteBuffer, 0); //Item (ring2 right?)
            writeD(byteBuffer, 0); //Item (HairSlot ?)
            writeD(byteBuffer, 0); //Item (FaceSlot ?)
            writeD(byteBuffer, 0); // new
            
            writeB(byteBuffer, player.getPlayerAppearance().getData());
            writeC(byteBuffer, 0); //Offline?
            writeB(byteBuffer, "00000000000000000000000000D8CDD8AC"); // unk
            
            writeB(byteBuffer, new byte[48]); // unk
            
            //Item Color?
            /*WriteD(writer, player.Inventory.GetItem(1) != null ? player.Inventory.GetItem(1).Color : 0);
            WriteD(writer, player.Inventory.GetItem(3) != null ? player.Inventory.GetItem(3).Color : 0);
            WriteD(writer, player.Inventory.GetItem(4) != null ? player.Inventory.GetItem(4).Color : 0);
            WriteD(writer, player.Inventory.GetItem(5) != null ? player.Inventory.GetItem(5).Color : 0);*/
            writeD(byteBuffer, 0);
            writeD(byteBuffer, 0);
            writeD(byteBuffer, 0);
            writeD(byteBuffer, 0);
            
            writeB(byteBuffer, new byte[16]); //16 bytes possible colors
            
            writeD(byteBuffer, 0); //Item (Skin Head)
            writeD(byteBuffer, 0); //Item (Skin Face)
            writeD(byteBuffer, 0); //Item (Skin Backpack)?
            writeD(byteBuffer, 0); //Item (Skin Weapon)
            writeD(byteBuffer, 0); //Item (Skin Armor)
            
            writeD(byteBuffer, 0); //unk -followed classes
            //WriteB(writer, "0C000000"); // unk berz =12
            //WriteB(writer, "06000000"); // unk sorc =6
            //WriteB(writer, "09000000"); // unk lance =9
            //WriteB(writer, "09000000"); // unk heal =9
            //WriteB(writer, "05000000"); // unk warr = 5
            //WriteB(writer, "00000000"); // unk slay = 0
            //WriteB(writer, "00000000"); // unk arch =0
            //WriteB(writer, "06000000"); // unk myst = 6

            writeD(byteBuffer, 0); // unk new
            writeD(byteBuffer, 0); // unk new
            
            writeD(byteBuffer, 0); //Rested (current)
            writeD(byteBuffer, 0); //Rested (max)
            
            writeB(byteBuffer, "0100000000000100000000");

            this.writeBufferPosition(byteBuffer, shift+8, Size.H);
            writeS(byteBuffer, player.getName());
            
            writeB(byteBuffer, player.getPlayerAppearance().getDetails1());
            writeB(byteBuffer, player.getPlayerAppearance().getDetails2());
            
            if (i != this.account.getPlayers().size() - 1) {
                this.writeBufferPosition(byteBuffer, shift+2, Size.H);
            }
        }
    }
}
