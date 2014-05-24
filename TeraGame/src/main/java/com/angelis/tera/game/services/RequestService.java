package com.angelis.tera.game.services;

import java.util.Collections;
import java.util.Map;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.game.models.player.Player;
import com.angelis.tera.game.models.player.request.Request;
import com.angelis.tera.game.tasks.TaskTypeEnum;

public class RequestService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(RequestService.class.getName());

    private final Map<Integer, Request> requests = Collections.synchronizedMap(new FastMap<Integer, Request>());

    private RequestService() {
    }

    @Override
    public void onInit() {
        log.info("RequestService started");
    }

    @Override
    public void onDestroy() {
        log.info("RequestService stopped");
    }

    public void onPlayerRequest(final Player player, final Request request) {
        final Request currentRequest = player.getController().getRequest();
        if (currentRequest != null || request == null) {
            // TODO
            return;
        }

        requests.put(request.getUid(), request);
        request.doAction();
    }

    public void onPlayerRespond(final int requestUid, final boolean accepted) {
        final Request request = this.requests.get(requestUid);
        if (accepted) {
            request.onAccept();
        }
        else {
            request.onDecline();
        }
    }

    public void onPlayerCancel(final int requestUid) {
        final Request request = this.requests.get(requestUid);
        ThreadPoolService.getInstance().cancelTask(request, TaskTypeEnum.PLAYER_REQUEST);
        request.doCancel();
    }

    public void cancelRequest(final Request request) {
        request.doCancel();
    }

    /** SINGLETON */
    public static RequestService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final RequestService instance = new RequestService();
    }
}
