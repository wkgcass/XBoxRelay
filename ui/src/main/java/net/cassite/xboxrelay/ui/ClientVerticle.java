package net.cassite.xboxrelay.ui;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import net.cassite.xboxrelay.base.ConfigureMessage;
import net.cassite.xboxrelay.base.HeartBeatMessage;
import io.vproxy.base.util.Logger;

public class ClientVerticle extends AbstractVerticle {
    private final String host;
    private final int port;
    private final ConfigureMessage config;
    private final AutoRobot robot;
    private final Runnable closeCallback;
    private NetClient client;
    private NetSocket socket;
    private long heartbeatPeriodicHandle = 0;

    public ClientVerticle(String addr, ConfigureMessage config, AutoRobot robot, Runnable closeCallback) {
        int index = addr.lastIndexOf(":");
        if (index == -1) {
            host = addr;
            port = 15992;
        } else {
            host = addr.substring(0, index);
            try {
                port = Integer.parseInt(addr.substring(index + 1));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("invalid address: " + addr);
            }
        }
        this.config = config;
        this.robot = robot;
        this.closeCallback = closeCallback;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        var client = vertx.createNetClient(new NetClientOptions()
            .setConnectTimeout(5_000)
            .setIdleTimeout(10_000));
        client.connect(port, host).andThen(r -> {
            if (r.succeeded()) {
                startPromise.complete();
                var sock = r.result();
                sock.closeHandler(v -> closeCallback.run());
                new ClientSocketHandler(vertx.getOrCreateContext(), sock, config, robot);
                this.socket = sock;
            } else {
                startPromise.fail(r.cause());
            }
        });
        this.client = client;
        this.heartbeatPeriodicHandle = vertx.setPeriodic(4_000, l -> heartbeat());
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        var client = this.client;
        this.client = null;
        if (client != null) {
            client.close().andThen(stopPromise);
        } else {
            stopPromise.complete();
        }
        vertx.cancelTimer(heartbeatPeriodicHandle);
    }

    private void heartbeat() {
        if (socket != null) {
            assert Logger.lowLevelDebug("sending heartbeat msg");
            socket.write(new HeartBeatMessage(HeartBeatMessage.TYPE_PING).toBuffer());
        }
    }
}
