package net.cassite.xboxrelay.ui;

import io.vertx.core.Context;
import io.vertx.core.net.NetSocket;
import io.vproxy.vfx.util.Logger;
import net.cassite.xboxrelay.base.BaseNetSocketHandler;
import net.cassite.xboxrelay.base.ConfigureMessage;
import net.cassite.xboxrelay.base.Message;
import net.cassite.xboxrelay.base.XBoxEvent;

public class ClientSocketHandler extends BaseNetSocketHandler {
    private final AutoRobot robot;

    public ClientSocketHandler(Context ctx, NetSocket sock, ConfigureMessage config, AutoRobot robot) {
        super(ctx, sock);
        this.robot = robot;
        sock.write(config.toBuffer());
    }

    @Override
    protected void handle(Message msg) {
        if (!(msg instanceof XBoxEvent event)) {
            Logger.warn("received unexpected message: " + msg);
            return;
        }
        Logger.debug("received message: " + msg);
        switch (event.key) {
            case lsbX -> robot.lsbX(event);
            case lsbY -> robot.lsbY(event);
            case rsbX -> robot.rsbX(event);
            case rsbY -> robot.rsbY(event);
            case du -> robot.du(event);
            case dd -> robot.dd(event);
            case dl -> robot.dl(event);
            case dr -> robot.dr(event);
            case back -> robot.back(event);
            case guide -> robot.guide(event);
            case start -> robot.start(event);
            case tl -> robot.tl(event);
            case tr -> robot.tr(event);
            case a -> robot.a(event);
            case b -> robot.b(event);
            case x -> robot.x(event);
            case y -> robot.y(event);
            case lb -> robot.lb(event);
            case rb -> robot.rb(event);
            case lt -> robot.lt(event);
            case rt -> robot.rt(event);
        }
    }
}
