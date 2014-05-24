package com.angelis.tera.game.models.dialog.actions;


import com.angelis.tera.game.models.dialog.AbstractDialogAction;
import com.angelis.tera.game.models.dialog.Dialog;
import com.angelis.tera.game.models.dialog.DialogButton;
import com.angelis.tera.game.models.dialog.enums.DialogIconEnum;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.quest.Quest;
import com.angelis.tera.game.services.DialogService;

public class ReadQuestDialogAction extends AbstractDialogAction {

    private final Quest quest;
    private int count;

    public ReadQuestDialogAction(final Player player, final Dialog dialog, final Quest quest) {
        super(player, dialog);
        this.quest = quest;
    }

    @Override
    public void action() {
        final Dialog d = new Dialog(this.player, this.dialog.getNpc());
        d.setQuest(quest);
        d.setPage(this.dialog.getPage()+1);
        d.addDialogButton(new DialogButton(d, DialogIconEnum.DEFAULT_QUEST, "@quest:"+(++this.count), new QuestAcceptDialogAction(this.player, d, this.quest)));

        DialogService.getInstance().sendDialogToPlayer(this.player, d);
    }
}
