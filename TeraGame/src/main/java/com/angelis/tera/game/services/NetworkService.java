package com.angelis.tera.game.services;

import org.apache.log4j.Logger;

import com.angelis.tera.common.network.config.ServerConfig;
import com.angelis.tera.game.config.NetworkConfig;
import com.angelis.tera.game.network.TeraGameServer;
import com.angelis.tera.game.network.factory.TeraGameAcceptFactory;
import com.angelis.tera.game.network.packet.ClientPacketHandler;
import com.angelis.tera.game.network.packet.ServerPacketHandler;

public class NetworkService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(DatabaseService.class.getName());

    private TeraGameServer teraServer;

    private NetworkService() {
    }

    @Override
    public void onInit() {
        final ServerConfig serverConfig = new ServerConfig(NetworkConfig.GAME_BIND_ADDRESS, NetworkConfig.GAME_BIND_PORT, new TeraGameAcceptFactory(), NetworkConfig.GAME_READ_WRITE_PROCESSOR_COUNT);

        teraServer = new TeraGameServer(serverConfig);

        new Thread(teraServer).start();

        ServerPacketHandler.init();
        ClientPacketHandler.init();
        log.info("NetworkService started");
    }

    @Override
    public void onDestroy() {
        log.info("NetworkService stopped");
    }

    /** SINGLETON */
    public static NetworkService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final NetworkService instance = new NetworkService();
    }
}
