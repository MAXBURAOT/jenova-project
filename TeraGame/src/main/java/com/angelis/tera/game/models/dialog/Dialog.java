package com.angelis.tera.game.models.dialog;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.game.models.AbstractUniqueTeraModel;
import com.angelis.tera.game.models.creature.Npc;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.quest.Quest;

public class Dialog extends AbstractUniqueTeraModel {

    private final Player player;
    private final Npc npc;
    private final List<DialogButton> dialogButtons;
    private final int special1;
    private Quest quest;
    private int page = 1;

    public Dialog(final Player player, final Npc npc, final int special1) {
        super(null);
        this.player = player;
        this.npc = npc;
        this.special1 = special1;
        this.dialogButtons = new FastList<>();
    }

    public Dialog(final Player player, final Npc npc) {
        this(player, npc, 0);
    }

    public final void progress(final int choice) {
        if (choice > this.dialogButtons.size()) {
            return;
        }

        final DialogButton button = this.dialogButtons.get(choice - 1);
        if (button != null) {
            button.action();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Npc getNpc() {
        return npc;
    }

    public List<DialogButton> getDialogButtons() {
        return this.dialogButtons;
    }

    public void addDialogButton(final DialogButton dialogButton) {
        this.dialogButtons.add(dialogButton);
    }

    public void clearDialogButtons() {
        this.dialogButtons.clear();
    }

    public Quest getQuest() {
        return this.quest;
    }

    public void setQuest(final Quest quest) {
        this.quest = quest;
    }

    public int getSpecial1() {
        return this.special1;
    }

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }
}
