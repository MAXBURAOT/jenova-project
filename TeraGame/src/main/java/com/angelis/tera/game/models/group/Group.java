package com.angelis.tera.game.models.group;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.common.utils.Function;
import com.angelis.tera.game.models.player.Player;

public final class Group {

    private final List<Player> players = new FastList<>();

    public final void addPlayer(final Player player) {
        this.players.add(player);
    }

    public final int size() {
        return this.players.size();
    }

    public void doOnEachPlayer(final Function<Player> function) {
        for (final Player player : this.players) {
            function.call(player);
        }
    }

    public final int getLowestLevel() {
        int lowestLevel = 60;
        for (final Player player : this.players) {
            if (player.getLevel() < lowestLevel) {
                lowestLevel = player.getLevel();
            }
        }
        
        return lowestLevel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((players == null) ? 0 : players.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Group)) {
            return false;
        }
        final Group other = (Group) obj;
        if (players == null) {
            if (other.players != null) {
                return false;
            }
        }
        else if (!players.equals(other.players)) {
            return false;
        }
        return true;
    }
}
