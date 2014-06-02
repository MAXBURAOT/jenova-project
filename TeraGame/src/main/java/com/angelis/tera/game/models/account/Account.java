package com.angelis.tera.game.models.account;

import java.util.List;
import java.util.Locale;

import javolution.util.FastList;

import com.angelis.tera.common.model.AbstractModel;
import com.angelis.tera.game.models.account.enums.AccountTypeEnum;
import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.network.connection.TeraGameConnection;

public class Account extends AbstractModel {

    private String login;
    private String password;
    private boolean banned;
    private int access;
    private AccountTypeEnum accountType;
    private TeraGameConnection connection;
    private List<Player> players;
    private Options options;
    private Locale locale;

    public Account(final Integer id) {
        super(id);
    }

    public Account() {
        super(null);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(final boolean banned) {
        this.banned = banned;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(final int access) {
        this.access = access;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(final AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    public TeraGameConnection getConnection() {
        return connection;
    }

    public void setConnection(final TeraGameConnection connection) {
        this.connection = connection;
    }

    public List<Player> getPlayers() {
        if (players == null) {
            players = new FastList<>();
        }
        return players;
    }
    
    public Player getPlayerById(final int playerId) {
        for (final Player player : this.getPlayers()) {
            if (player.getId() == playerId) {
                return player;
            }
        }
        
        return null;
    }

    public void setPlayers(final List<Player> players) {
        this.players = players;
    }
    
    public void addPlayer(final Player player) {
        this.getPlayers().add(player);
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(final Options option) {
        this.options = option;
    }
    
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public final boolean haveCharacterWithLevel(final int level) {
        for (final Player player : this.getPlayers()) {
            if (player.getLevel() >= level) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + access;
        result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
        result = prime * result + (banned ? 1231 : 1237);
        result = prime * result + ((connection == null) ? 0 : connection.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((players == null) ? 0 : players.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Account)) {
            return false;
        }
        final Account other = (Account) obj;
        if (access != other.access) {
            return false;
        }
        if (accountType != other.accountType) {
            return false;
        }
        if (banned != other.banned) {
            return false;
        }
        if (connection == null) {
            if (other.connection != null) {
                return false;
            }
        }
        else if (!connection.equals(other.connection)) {
            return false;
        }
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        }
        else if (!login.equals(other.login)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        }
        else if (!password.equals(other.password)) {
            return false;
        }
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
