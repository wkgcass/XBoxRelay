package net.cassite.xboxrelay.agent;

import io.vproxy.base.util.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetSocketManager {
    private final List<AgentNetSocketHandler> handlers = new CopyOnWriteArrayList<>();

    public void register(AgentNetSocketHandler handler) {
        Logger.alert("handler registered: " + handler);
        handlers.add(handler);
    }

    public void deregister(AgentNetSocketHandler handler) {
        Logger.alert("handler exit: " + handler);
        handlers.remove(handler);
    }

    public void onXBoxDrvData(XBoxDrvData data) {
        for (var h : handlers) {
            h.onXBoxDrvData(data);
        }
    }
}
