package net.cassite.xboxrelay.ui;

import net.cassite.xboxrelay.base.TriggerLevel;
import net.cassite.xboxrelay.base.XBoxEvent;
import net.cassite.xboxrelay.base.XBoxKey;

import static net.cassite.xboxrelay.base.TriggerLevel.OFF;

public class State {
    public TriggerLevel lsbX = OFF;
    public TriggerLevel lsbY = OFF;
    public TriggerLevel rsbX = OFF;
    public TriggerLevel rsbY = OFF;
    public TriggerLevel du = OFF;
    public TriggerLevel dd = OFF;
    public TriggerLevel dl = OFF;
    public TriggerLevel dr = OFF;
    public TriggerLevel back = OFF;
    public TriggerLevel guide = OFF;
    public TriggerLevel start = OFF;
    public TriggerLevel tl = OFF;
    public TriggerLevel tr = OFF;
    public TriggerLevel a = OFF;
    public TriggerLevel b = OFF;
    public TriggerLevel x = OFF;
    public TriggerLevel y = OFF;
    public TriggerLevel lb = OFF;
    public TriggerLevel rb = OFF;
    public TriggerLevel lt = OFF;
    public TriggerLevel rt = OFF;

    private boolean paused;

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void lsbX(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.lsbX = level;
    }

    public void lsbY(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.lsbY = level;
    }

    public void rsbX(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.rsbX = level;
    }

    public void rsbY(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.rsbY = level;
    }

    public void du(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.du = level;
    }

    public void dd(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.dd = level;
    }

    public void dl(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.dl = level;
    }

    public void dr(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.dr = level;
    }

    public void back(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.back = level;
    }

    public void guide(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.guide = level;
    }

    public void start(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.start = level;
    }

    public void tl(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.tl = level;
    }

    public void tr(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.tr = level;
    }

    public void a(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.a = level;
    }

    public void b(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.b = level;
    }

    public void x(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.x = level;
    }

    public void y(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.y = level;
    }

    public void lb(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.lb = level;
    }

    public void rb(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.rb = level;
    }

    public void lt(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.lt = level;
    }

    public void rt(TriggerLevel level) {
        if (paused && level != OFF) {
            return;
        }
        this.rt = level;
    }

    public void apply(AutoRobot robot) {
        apply(robot, false);
    }

    public void apply(AutoRobot robot, boolean triggerOff) {
        if (lsbX != TriggerLevel.OFF || triggerOff) {
            robot.lsbX(new XBoxEvent(XBoxKey.lsbX, lsbX));
        }
        if (lsbY != TriggerLevel.OFF || triggerOff) {
            robot.lsbY(new XBoxEvent(XBoxKey.lsbY, lsbY));
        }
        if (rsbX != TriggerLevel.OFF || triggerOff) {
            robot.rsbX(new XBoxEvent(XBoxKey.rsbX, rsbX));
        }
        if (rsbY != TriggerLevel.OFF || triggerOff) {
            robot.rsbY(new XBoxEvent(XBoxKey.rsbY, rsbY));
        }
        if (du != TriggerLevel.OFF || triggerOff) {
            robot.du(new XBoxEvent(XBoxKey.du, du));
        }
        if (dd != TriggerLevel.OFF || triggerOff) {
            robot.dd(new XBoxEvent(XBoxKey.dd, dd));
        }
        if (dl != TriggerLevel.OFF || triggerOff) {
            robot.dl(new XBoxEvent(XBoxKey.dl, dl));
        }
        if (dr != TriggerLevel.OFF || triggerOff) {
            robot.dr(new XBoxEvent(XBoxKey.dr, dr));
        }
        if (back != TriggerLevel.OFF || triggerOff) {
            robot.back(new XBoxEvent(XBoxKey.back, back));
        }
        if (guide != TriggerLevel.OFF || triggerOff) {
            robot.guide(new XBoxEvent(XBoxKey.guide, guide));
        }
        if (start != TriggerLevel.OFF || triggerOff) {
            robot.start(new XBoxEvent(XBoxKey.start, start));
        }
        if (tl != TriggerLevel.OFF || triggerOff) {
            robot.tl(new XBoxEvent(XBoxKey.tl, tl));
        }
        if (tr != TriggerLevel.OFF || triggerOff) {
            robot.tr(new XBoxEvent(XBoxKey.tr, tr));
        }
        if (a != TriggerLevel.OFF || triggerOff) {
            robot.a(new XBoxEvent(XBoxKey.a, a));
        }
        if (b != TriggerLevel.OFF || triggerOff) {
            robot.b(new XBoxEvent(XBoxKey.b, b));
        }
        if (x != TriggerLevel.OFF || triggerOff) {
            robot.x(new XBoxEvent(XBoxKey.x, x));
        }
        if (y != TriggerLevel.OFF || triggerOff) {
            robot.y(new XBoxEvent(XBoxKey.y, y));
        }
        if (lb != TriggerLevel.OFF || triggerOff) {
            robot.lb(new XBoxEvent(XBoxKey.lb, lb));
        }
        if (rb != TriggerLevel.OFF || triggerOff) {
            robot.rb(new XBoxEvent(XBoxKey.rb, rb));
        }
        if (lt != TriggerLevel.OFF || triggerOff) {
            robot.lt(new XBoxEvent(XBoxKey.lt, lt));
        }
        if (rt != TriggerLevel.OFF || triggerOff) {
            robot.rt(new XBoxEvent(XBoxKey.rt, rt));
        }
    }
}
