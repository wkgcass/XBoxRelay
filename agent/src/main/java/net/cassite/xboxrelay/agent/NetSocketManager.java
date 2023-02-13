package net.cassite.xboxrelay.agent;

import net.cassite.xboxrelay.base.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetSocketManager {
    private final List<AgentNetSocketHandler> handlers = new CopyOnWriteArrayList<>();

    public void register(AgentNetSocketHandler handler) {
        Logger.info("handler registered: " + handler);
        handlers.add(handler);
    }

    public void deregister(AgentNetSocketHandler handler) {
        Logger.info("handler exit: " + handler);
        handlers.remove(handler);
    }

    public void onXBoxDrvData(XBoxDrvData data) {
        for (var h : handlers) {
            h.onXBoxDrvData(data);
        }
    }
}
