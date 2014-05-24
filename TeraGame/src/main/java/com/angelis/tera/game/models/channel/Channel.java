package com.angelis.tera.game.models.channel;

import java.util.Set;

import javolution.util.FastSet;

import com.angelis.tera.game.models.AbstractTeraModel;
import com.angelis.tera.game.models.player.Player;

public class Channel extends AbstractTeraModel {
    
    private final Set<Player> players = new FastSet<>();
    
    public Channel(final Integer id) {
        super(id);
    }

    public void addPlayer(final Player player) {
        this.players.add(player);
    }
    
    public void removePlayer(final Player player) {
        this.players.remove(player);
    }

    public Set<Player> getPlayers() {
        return players;
    }
}
