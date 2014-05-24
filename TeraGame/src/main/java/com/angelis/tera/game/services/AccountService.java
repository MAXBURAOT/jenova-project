package com.angelis.tera.game.services;

import org.apache.log4j.Logger;

import com.angelis.tera.game.config.AccountConfig;
import com.angelis.tera.game.delegate.database.AccountDelegate;
import com.angelis.tera.game.models.account.Account;
import com.angelis.tera.game.models.account.Options;
import com.angelis.tera.game.models.account.enums.AccountTypeEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.server.SM_LOADING_SCREEN_CONTROL_INFO;
import com.angelis.tera.game.network.packet.server.SM_LOGIN_ACCOUNT_INFO;
import com.angelis.tera.game.network.packet.server.SM_LOGIN_ARBITER;
import com.angelis.tera.game.network.packet.server.SM_REMAIN_PLAY_TIME;

public class AccountService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(AccountService.class.getName());

    private AccountService() {
    }

    @Override
    public void onInit() {
        log.info("AccountService started");
    }

    @Override
    public void onDestroy() {
        log.info("AccountService stopped");
    }

    public void onAccountConnect(final Account account) {
        final TeraGameConnection connection = account.getConnection();

        connection.sendPacket(new SM_LOADING_SCREEN_CONTROL_INFO());
        connection.sendPacket(new SM_REMAIN_PLAY_TIME());
        connection.sendPacket(new SM_LOGIN_ARBITER());
        connection.sendPacket(new SM_LOGIN_ACCOUNT_INFO());
    }

    public void onAccountDisconnect(final Account account) {
        AccountDelegate.updateAccountModel(account);
    }

    public void authorizeAccount(final TeraGameConnection connection, final String login, final String password) {
        log.info("Try authorizing " + login);
        Account account = AccountDelegate.readAccountModelByLogin(login);
        if (account == null && AccountConfig.ACCOUNT_AUTOCREATE) {
            account = new Account();
            account.setLogin(login);
            account.setPassword(password);
            account.setAccess(0);
            account.setAccountType(AccountTypeEnum.VETERAN);
            account.setOptions(new Options());
            account.setLocale(AccountConfig.ACCOUNT_DEFAULT_LOCALE);
            AccountDelegate.createAccountModel(account);
        }

        if (account == null) {
            log.info("Account " + login + " tryed to login but this account isn't registered. Closing connection.");
            connection.close();
            return;
        }

        if (!account.getPassword().equals(password)) {
            log.info("Account " + login + " tryed to login with incorrect password. Closing connection.");
            connection.close();
            return;
        }

        if (account.isBanned()) {
            log.info("Banned account tryed to login. Closing connection.");
            connection.close();
            return;
        }

        connection.setAccount(account);
        this.onAccountConnect(account);
    }

    /** SINGLETON */
    public static AccountService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final AccountService instance = new AccountService();
    }

    public void registerHardwareInfo(final String osName, final String cpuName, final String gpuName) {
        
    }
}
