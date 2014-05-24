package com.angelis.tera.game.database.entity;

import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.angelis.tera.common.database.entity.AbstractDatabaseEntity;
import com.angelis.tera.game.models.account.enums.AccountTypeEnum;
import com.angelis.tera.game.models.account.enums.DisplayRangeEnum;

@Entity
@Table(name = "accounts")
public class AccountEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = -4034792019245322102L;

    @Column(unique = true, name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "banned")
    private boolean banned;

    @Column(name = "access")
    private int access;

    @Column(name = "displayRange")
    private DisplayRangeEnum displayRange;

    @Column(name = "accountType")
    private AccountTypeEnum accountType;

    @Column(name = "locale")
    private Locale locale;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.ALL)
    private Set<PlayerEntity> players;

    public AccountEntity(final Integer id) {
        super(id);
    }

    public AccountEntity() {
        super();
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

    public DisplayRangeEnum getDisplayRange() {
        return displayRange;
    }

    public void setDisplayRange(final DisplayRangeEnum displayRange) {
        this.displayRange = displayRange;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(final AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public Set<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(final Set<PlayerEntity> players) {
        this.players = players;
    }

    public void addPlayer(final PlayerEntity player) {
        this.getPlayers().add(player);
        player.setAccount(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + access;
        result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
        result = prime * result + (banned ? 1231 : 1237);
        result = prime * result + ((displayRange == null) ? 0 : displayRange.hashCode());
        result = prime * result + ((locale == null) ? 0 : locale.hashCode());
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
        if (!(obj instanceof AccountEntity)) {
            return false;
        }
        final AccountEntity other = (AccountEntity) obj;
        if (access != other.access) {
            return false;
        }
        if (accountType != other.accountType) {
            return false;
        }
        if (banned != other.banned) {
            return false;
        }
        if (displayRange != other.displayRange) {
            return false;
        }
        if (locale == null) {
            if (other.locale != null) {
                return false;
            }
        }
        else if (!locale.equals(other.locale)) {
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
