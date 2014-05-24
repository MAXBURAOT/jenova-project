package com.angelis.tera.game.services;

import java.util.List;

import javolution.util.FastList;

import org.apache.log4j.Logger;

import com.angelis.tera.game.models.creature.Npc;
import com.angelis.tera.game.models.creature.TeraCreature;
import com.angelis.tera.game.models.dialog.Dialog;
import com.angelis.tera.game.models.dialog.DialogButton;
import com.angelis.tera.game.models.dialog.actions.ShowBankAction;
import com.angelis.tera.game.models.dialog.actions.ShowFlyMapAction;
import com.angelis.tera.game.models.dialog.enums.DialogIconEnum;
import com.angelis.tera.game.models.dialog.enums.DialogStringEnum;
import com.angelis.tera.game.models.enums.CreatureTitleEnum;
import com.angelis.tera.game.models.enums.ObjectFamilyEnum;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.enums.EmoteEnum;
import com.angelis.tera.game.models.template.TeraCreatureTemplate;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.network.packet.server.SM_DIALOG;
import com.angelis.tera.game.network.packet.server.SM_DIALOG_EVENT;
import com.angelis.tera.game.network.packet.server.SM_NPC_MENU_SELECT;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_DIALOG_CLOSE;
import com.angelis.tera.game.network.packet.server.SM_SOCIAL;

public class DialogService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(DialogService.class.getName());

    @Override
    public void onInit() {
        log.info("DialogService started");
    }

    @Override
    public void onDestroy() {
        log.info("DialogService stopped");
    }

    public void onNpcContact(final Player player, final int uid) {
        final TeraCreature creature = (TeraCreature) ObjectIDService.getInstance().getObjectFromUId(ObjectFamilyEnum.CREATURE, uid);
        if (creature == null) {
            return;
        }

        final Npc npc = (Npc) creature;
        final TeraCreatureTemplate creatureTemplate = creature.getTemplate();
        final Dialog dialog = new Dialog(player, npc, creatureTemplate.getHuntingZoneId());

        player.getConnection().sendPacket(new SM_NPC_MENU_SELECT(1));

        // TODO creature template
        final CreatureTitleEnum creatureTitle = creatureTemplate.getCreatureTitle();
        if (creatureTitle != null) {
            switch (creatureTitle) {
                case FLIGHT_MASTER:
                    dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.CENTERED_GRAY, DialogStringEnum.FLIGHTPOINTS, new ShowFlyMapAction(player, dialog)));
                break;

                case BANK:
                    dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.CENTERED_GRAY, DialogStringEnum.BANK, new ShowBankAction(player, dialog)));
                break;
            }
        }

        // Add quest buttons to the dialog
        QuestService.getInstance().addQuestDialogButtons(npc, player, dialog);

        this.sendDialogToPlayer(player, dialog);
    }

    public void onDialogEvent(final Player player, final int page, final int dialogUid) {
        final Dialog dialog = (Dialog) ObjectIDService.getInstance().getObjectFromUId(ObjectFamilyEnum.DIALOG, dialogUid);
        if (dialog == null) {
            return;
        }
        
        final List<TeraServerPacket> packets = new FastList<>();
        if (page == 0) {
            packets.add(new SM_SOCIAL(player, EmoteEnum.TALK, 0));
        }
        
        packets.add(new SM_DIALOG_EVENT(dialog.getNpc(), player, page));
        
        VisibleService.getInstance().sendPacketsForVisible(player, packets);
    }

    public void onDialog(final Player player, final int dialogUid, final int choice) {
        final Dialog dialog = (Dialog) ObjectIDService.getInstance().getObjectFromUId(ObjectFamilyEnum.DIALOG, dialogUid);
        if (dialog == null) {
            return;
        }

        player.getConnection().sendPacket(new SM_NPC_MENU_SELECT(2));
        dialog.progress(choice);
    }

    public void onPlayerMove(final Player player) {
        final Dialog dialog = player.getController().getDialog();
        if (dialog == null) {
            return;
        }

        this.playerCloseDialog(player, dialog);
    }

    public final void sendDialogToPlayer(final Player player, final Dialog dialog) {
        player.getController().setDialog(dialog);
        player.getConnection().sendPacket(new SM_DIALOG(dialog));
    }
    
    private void playerCloseDialog(final Player player, final Dialog dialog) {
        final TeraGameConnection connection = player.getConnection();
        connection.sendPacket(new SM_PLAYER_DIALOG_CLOSE(dialog.getNpc()));
        connection.sendPacket(new SM_DIALOG_EVENT(dialog.getNpc(), player, dialog.getPage()));
        player.getController().setDialog(null);

        ObjectIDService.getInstance().releaseId(ObjectFamilyEnum.DIALOG, dialog.getUid());
    }

    /** SINGLETON */
    public static DialogService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final DialogService instance = new DialogService();
    }
}
