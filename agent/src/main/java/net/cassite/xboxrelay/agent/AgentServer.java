package net.cassite.xboxrelay.agent;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

public class AgentServer extends AbstractVerticle {
    private NetServer netServer;
    private final NetSocketManager manager;
    private final int listenPort;

    public AgentServer(NetSocketManager manager, int listenPort) {
        this.manager = manager;
        this.listenPort = listenPort;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        var netServer = vertx.createNetServer(new NetServerOptions()
            .setIdleTimeout(10_000));
        netServer.connectHandler(sock -> {
            var handler = new AgentNetSocketHandler(vertx.getOrCreateContext(), sock);
            manager.register(handler);
            sock.closeHandler(v -> manager.deregister(handler));
        });
        netServer.listen(listenPort).andThen(r -> {
            if (r.succeeded()) {
                startPromise.complete();
            } else {
                startPromise.fail(r.cause());
            }
        });
        this.netServer = netServer;
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        var s = this.netServer;
        this.netServer = null;
        if (s != null) {
            s.close().andThen(stopPromise);
        } else {
            stopPromise.complete();
        }
    }
}
