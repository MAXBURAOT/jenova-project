package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.models.dialog.Dialog;
import com.angelis.tera.game.models.dialog.DialogButton;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_DIALOG extends TeraServerPacket {

    private final Dialog dialog;
    
    public SM_DIALOG(final Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, (short) this.dialog.getDialogButtons().size()); //Buttons count
        
        int buttonsShift = byteBuffer.position();
        writeH(byteBuffer, 0); //First button shift
        
        writeH(byteBuffer, (short) (this.dialog.getQuest() == null ? 0 : 1));
        
        final int rewardShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        writeUid(byteBuffer, this.dialog.getNpc());
        writeD(byteBuffer, this.dialog.getPage()); // page
        writeD(byteBuffer, this.dialog.getQuest() == null ? this.dialog.getNpc().getId() : this.dialog.getQuest().getId()); // dialogId
        writeD(byteBuffer, this.dialog.getSpecial1()); // special1
        writeD(byteBuffer, 0); // special2
        writeD(byteBuffer, this.dialog.getPage()); // page
        writeD(byteBuffer, this.dialog.getUid()); //DialogUid
        writeB(byteBuffer, new byte[5]);
        writeD(byteBuffer, 1);
        writeB(byteBuffer, new byte[8]);
        writeB(byteBuffer, "FFFFFFFF");

        int i = 1;
        for (final DialogButton dialogButton : this.dialog.getDialogButtons()) {
            this.writeBufferPosition(byteBuffer, buttonsShift, Size.H);

            writeH(byteBuffer, (short) byteBuffer.position());
            buttonsShift = byteBuffer.position();
            
            writeH(byteBuffer, 0);

            writeH(byteBuffer, (short) (byteBuffer.position() + 10));
            writeD(byteBuffer, i++);
            writeD(byteBuffer, dialogButton.getDialogIcon().value);
            writeS(byteBuffer, dialogButton.getText());
        }
        
        if (this.dialog.getQuest() != null) {
            this.writeBufferPosition(byteBuffer, rewardShift, Size.H);
            
            writeH(byteBuffer, (short) byteBuffer.position());
            writeB(byteBuffer, "0000000000000000000000000000F401000032000000000000000000000000000000000000000000000000000000");
        }
/*
        if (this.quest != null) {
            int itemsShift = 0;

            byteBuffer.Seek(rewardShift, SeekOrigin.Begin);
            writeH(byteBuffer, (short)byteBuffer.BaseStream.Length);
            byteBuffer.Seek(0, SeekOrigin.End);

            writeH(byteBuffer, (short)byteBuffer.BaseStream.Position);
            writeH(byteBuffer, 0);

            if (Reward != null && Reward.Items != null)
            {
                writeH(byteBuffer, (short) Reward.Items.Count);
                itemsShift = (int) byteBuffer.BaseStream.Position;
                writeH(byteBuffer, 0);
            }
            else
                writeD(byteBuffer, 0);

            writeD(byteBuffer, 0);
            writeD(byteBuffer, Quest.QuestRewardType == QuestRewardType.Choice ? 1 : 3); //1 Selectable reward //2 Unspecified reward //3 All
            writeD(byteBuffer, Quest.RewardExp);
            writeD(byteBuffer, Quest.RewardMoney);
            writeD(byteBuffer, 0);
            writeD(byteBuffer, 0); //Polici points
            writeD(byteBuffer, 0);
            writeD(byteBuffer, 0); //Reputation levels [exp]
            writeD(byteBuffer, 0); //Reputation

            if (Reward == null || Reward.Items == null)
                return;

            for (int x = 0; x < Reward.Items.Count; x++)
            {
                byteBuffer.Seek(itemsShift, SeekOrigin.Begin);
                writeH(byteBuffer, (short) byteBuffer.BaseStream.Length);
                byteBuffer.Seek(0, SeekOrigin.End);

                writeH(byteBuffer, (short)byteBuffer.BaseStream.Position);
                itemsShift = (int) byteBuffer.BaseStream.Position;
                writeH(byteBuffer, 0);

                writeD(byteBuffer, Reward.Items[x].Key);
                writeD(byteBuffer, Reward.Items[x].Value);
            }
        }*/
    }
}
