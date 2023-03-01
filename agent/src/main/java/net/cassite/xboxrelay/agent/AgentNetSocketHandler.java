package net.cassite.xboxrelay.agent;

import io.vertx.core.Context;
import io.vertx.core.net.NetSocket;
import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import net.cassite.xboxrelay.base.*;

import java.util.ArrayList;
import java.util.function.Consumer;

public class AgentNetSocketHandler extends BaseNetSocketHandler {
    private ConfigureMessage lastConfiguration = null;

    private TriggerLevel lsbX = TriggerLevel.OFF;
    private TriggerLevel lsbY = TriggerLevel.OFF;
    private TriggerLevel rsbX = TriggerLevel.OFF;
    private TriggerLevel rsbY = TriggerLevel.OFF;
    private TriggerLevel lt = TriggerLevel.OFF;
    private TriggerLevel rt = TriggerLevel.OFF;
    private boolean du;
    private boolean dd;
    private boolean dl;
    private boolean dr;
    private boolean back;
    private boolean guide;
    private boolean start;
    private boolean tl;
    private boolean tr;
    private boolean a;
    private boolean b;
    private boolean x;
    private boolean y;
    private boolean lb;
    private boolean rb;

    public AgentNetSocketHandler(Context ctx, NetSocket sock) {
        super(ctx, sock);
    }

    public void onXBoxDrvData(XBoxDrvData data) {
        if (lastConfiguration == null)
            return;
        var events = new ArrayList<XBoxEvent>(1);
        var c = lastConfiguration;
        checkX1(data, events, c);
        checkY1(data, events, c);
        checkX2(data, events, c);
        checkY2(data, events, c);
        checkDU(data, events);
        checkDD(data, events);
        checkDL(data, events);
        checkDR(data, events);
        checkBack(data, events);
        checkGuide(data, events);
        checkStart(data, events);
        checkTL(data, events);
        checkTR(data, events);
        checkA(data, events);
        checkB(data, events);
        checkX(data, events);
        checkY(data, events);
        checkLB(data, events);
        checkRB(data, events);
        checkLT(data, c, events);
        checkRT(data, c, events);

        if (events.isEmpty()) {
            // Logger.debug("no events triggerred");
            return;
        }
        assert Logger.lowLevelDebug("sending events to " + remoteAddress() + ": " + events);
        for (var e : events) {
            send(e);
        }
    }

    private void checkX1(XBoxDrvData data, ArrayList<XBoxEvent> events, ConfigureMessage c) {
        checkInt(data.x1, lsbX, c.min().lsbX, c.max().lsbX, c.min().lsbXB, c.max().lsbXB, XBoxKey.lsbX, events, v -> lsbX = v);
    }

    private void checkY1(XBoxDrvData data, ArrayList<XBoxEvent> events, ConfigureMessage c) {
        checkInt(data.y1, lsbY, c.min().lsbY, c.max().lsbY, c.min().lsbYB, c.max().lsbYB, XBoxKey.lsbY, events, v -> lsbY = v);
    }

    private void checkX2(XBoxDrvData data, ArrayList<XBoxEvent> events, ConfigureMessage c) {
        checkInt(data.x2, rsbX, c.min().rsbX, c.max().rsbX, c.min().rsbXB, c.max().rsbXB, XBoxKey.rsbX, events, v -> rsbX = v);
    }

    private void checkY2(XBoxDrvData data, ArrayList<XBoxEvent> events, ConfigureMessage c) {
        checkInt(data.y2, rsbY, c.min().rsbY, c.max().rsbY, c.min().rsbYB, c.max().rsbYB, XBoxKey.rsbY, events, v -> rsbY = v);
    }

    private void checkDU(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.du, du, XBoxKey.du, events, v -> du = v);
    }

    private void checkDD(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.dd, dd, XBoxKey.dd, events, v -> dd = v);
    }

    private void checkDL(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.dl, dl, XBoxKey.dl, events, v -> dl = v);
    }

    private void checkDR(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.dr, dr, XBoxKey.dr, events, v -> dr = v);
    }

    private void checkBack(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.back, back, XBoxKey.back, events, v -> back = v);
    }

    private void checkGuide(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.guide, guide, XBoxKey.guide, events, v -> guide = v);
    }

    private void checkStart(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.start, start, XBoxKey.start, events, v -> start = v);
    }

    private void checkTL(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.tl, tl, XBoxKey.tl, events, v -> tl = v);
    }

    private void checkTR(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.tr, tr, XBoxKey.tr, events, v -> tr = v);
    }

    private void checkA(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.a, a, XBoxKey.a, events, v -> a = v);
    }

    private void checkB(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.b, b, XBoxKey.b, events, v -> b = v);
    }

    private void checkX(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.x, x, XBoxKey.x, events, v -> x = v);
    }

    private void checkY(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.y, y, XBoxKey.y, events, v -> y = v);
    }

    private void checkLB(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.lb, lb, XBoxKey.lb, events, v -> lb = v);
    }

    private void checkRB(XBoxDrvData data, ArrayList<XBoxEvent> events) {
        checkBool(data.rb, rb, XBoxKey.rb, events, v -> rb = v);
    }

    private void checkLT(XBoxDrvData data, ConfigureMessage c, ArrayList<XBoxEvent> events) {
        checkInt(data.lt, lt, c.min().lt, c.max().lt, 0, 0, XBoxKey.lt, events, v -> lt = v);
    }

    private void checkRT(XBoxDrvData data, ConfigureMessage c, ArrayList<XBoxEvent> events) {
        checkInt(data.rt, rt, c.min().rt, c.max().rt, 0, 0, XBoxKey.rt, events, v -> rt = v);
    }

    private void checkBool(boolean data, boolean current, XBoxKey key, ArrayList<XBoxEvent> events, BooleanConsumer setter) {
        if (data) {
            if (!current) {
                events.add(new XBoxEvent(key, TriggerLevel.MAX));
                setter.accept(true);
            }
        } else {
            if (current) {
                events.add(new XBoxEvent(key, TriggerLevel.OFF));
                setter.accept(false);
            }
        }
    }

    @FunctionalInterface
    private interface BooleanConsumer {
        void accept(boolean v);
    }

    private void checkInt(int dataInt, TriggerLevel current,
                          int configMinInt, int configMaxInt,
                          int configMinBInt, int configMaxBInt,
                          XBoxKey key,
                          ArrayList<XBoxEvent> events,
                          Consumer<TriggerLevel> setter) {
        if (dataInt == 0) {
            if (current != TriggerLevel.OFF) {
                events.add(new XBoxEvent(key, TriggerLevel.OFF));
                setter.accept(TriggerLevel.OFF);
            }
        } else if (dataInt > 0) {
            if (dataInt < configMinInt) {
                if (current != TriggerLevel.OFF) {
                    events.add(new XBoxEvent(key, TriggerLevel.OFF));
                    setter.accept(TriggerLevel.OFF);
                }
            } else if (dataInt > configMaxInt) {
                if (current != TriggerLevel.MAX) {
                    events.add(new XBoxEvent(key, TriggerLevel.MAX));
                    setter.accept(TriggerLevel.MAX);
                }
            } else {
                if (current != TriggerLevel.MIN) {
                    events.add(new XBoxEvent(key, TriggerLevel.MIN));
                    setter.accept(TriggerLevel.MIN);
                }
            }
        } else {
            if (dataInt > -configMinBInt) {
                if (current != TriggerLevel.OFF) {
                    events.add(new XBoxEvent(key, TriggerLevel.OFF));
                    setter.accept(TriggerLevel.OFF);
                }
            } else if (dataInt < -configMaxBInt) {
                if (current != TriggerLevel.B_MAX) {
                    events.add(new XBoxEvent(key, TriggerLevel.B_MAX));
                    setter.accept(TriggerLevel.B_MAX);
                }
            } else {
                if (current != TriggerLevel.B_MIN) {
                    events.add(new XBoxEvent(key, TriggerLevel.B_MIN));
                    setter.accept(TriggerLevel.B_MIN);
                }
            }
        }
    }

    @Override
    protected void handle(Message msg) {
        if (msg instanceof ConfigureMessage cmsg) {
            handle(cmsg);
        } else {
            Logger.warn(LogType.INVALID_EXTERNAL_DATA, "received unexpected message: " + msg);
        }
    }

    private void handle(ConfigureMessage msg) {
        Logger.alert("received configure message: " + msg + " from " + remoteAddress());
        if (!msg.valid()) {
            Logger.warn(LogType.INVALID_EXTERNAL_DATA, "received invalid configure message: " + msg + " from " + remoteAddress());
            close();
            return;
        }
        if (lastConfiguration == null) {
            // first configure message
            if (!msg.validForInitialControlMessage()) {
                Logger.warn(LogType.INVALID_EXTERNAL_DATA, "unexpected initial configure message: " + msg + "  from " + remoteAddress());
                close();
                return;
            }
            var c = new ConfigureMessage();
            c.from(msg);
            this.lastConfiguration = c;
        } else {
            lastConfiguration.from(msg);
        }
        Logger.alert("applied configuration: " + lastConfiguration);
    }
}
