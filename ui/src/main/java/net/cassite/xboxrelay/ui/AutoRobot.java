package net.cassite.xboxrelay.ui;

import io.vproxy.vfx.robot.RobotWrapper;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import net.cassite.xboxrelay.base.TriggerLevel;
import net.cassite.xboxrelay.base.XBoxEvent;

import java.util.Objects;
import java.util.Set;

public class AutoRobot {
    private final Binding binding;
    private final Set<KeyOrMouseMoveDataGroup> groups;
    private boolean isRunning = false;
    private final Timer timer;
    private final RobotWrapper robot;

    public AutoRobot(Binding binding) {
        this.binding = new Binding(binding);
        this.groups = this.binding.makeDataGroup();
        this.timer = new Timer();
        this.robot = new RobotWrapper();
    }

    public void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        timer.start();
    }

    public void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
        timer.stop();
        for (var g : groups) {
            cancel(g);
        }
    }

    private class Timer extends AnimationTimer {
        @Override
        public void handle(long l) {
            boolean hasMousePos = false;
            var mouseX = 0d;
            var mouseY = 0d;
            for (var g : groups) {
                var current = g.current;
                if (current == null) {
                    continue;
                }
                if (current.mouseMove == null) {
                    continue;
                }
                if (g.lastTs == 0 || g.lastTs > l) {
                    g.lastTs = l;
                    continue;
                }
                if (!hasMousePos) {
                    var mousePoint2D = robot.getMousePosition();
                    mouseX = mousePoint2D.getX();
                    mouseY = mousePoint2D.getY();
                    hasMousePos = true;
                }
                var delta = l - g.lastTs;
                g.lastTs = l;
                var dx = current.mouseMove.x / 1000d * (delta / 1_000_000d);
                var dy = current.mouseMove.y / 1000d * (delta / 1_000_000d);
                mouseX += dx;
                mouseY += dy;
            }
            if (hasMousePos) {
                robot.mouseMove(mouseX, mouseY);
            }
        }
    }

    private void cancel(KeyOrMouseMoveDataGroup group) {
        var current = group.current;
        if (current == null) {
            return;
        }
        if (current.key != null) {
            Platform.runLater(() ->
                robot.release(current.key));
        }
        group.current = null;
        group.lastTs = 0;
        group.level = TriggerLevel.OFF;
    }

    private void apply(KeyOrMouseMoveDataGroup group, KeyOrMouseMove km, TriggerLevel level) {
        if (km == null) {
            return;
        }
        if (group.current != null) {
            if (Objects.equals(group.current, km)) {
                group.level = level;
                return;
            }
            if (group.current.needToCancelForSwitchingTo(km)) {
                cancel(group);
            }
        }
        group.current = km;
        group.level = level;
        if (km.key != null) {
            Platform.runLater(() -> robot.press(km.key));
        }
    }

    private void handleGradient(KeyOrMouseMove min, KeyOrMouseMove max, XBoxEvent event) {
        handleGradient(min, max, null, null, event);
    }

    private void handleGradient(KeyOrMouseMove min, KeyOrMouseMove max, KeyOrMouseMove bMin, KeyOrMouseMove bMax, XBoxEvent event) {
        var level = event.level;
        var group = min.group; // max.group == min.group
        switch (level) {
            case OFF -> cancel(group);
            case MIN -> apply(group, min, level);
            case MAX -> apply(group, max, level);
            case B_MIN -> apply(group, bMin, level);
            case B_MAX -> apply(group, bMax, level);
        }
    }

    private void handleSwitch(KeyOrMouseMove km, XBoxEvent event) {
        var level = event.level;
        var group = km.group;
        if (level == TriggerLevel.OFF) {
            cancel(group);
        } else {
            apply(group, km, level);
        }
    }

    public void lsbX(XBoxEvent event) {
        handleGradient(binding.lsbXMin, binding.lsbXMax, binding.lsbXBMin, binding.lsbXBMax, event);
    }

    public void lsbY(XBoxEvent event) {
        handleGradient(binding.lsbYMin, binding.lsbYMax, binding.lsbYBMin, binding.lsbYBMax, event);
    }

    public void rsbX(XBoxEvent event) {
        handleGradient(binding.rsbXMin, binding.rsbXMax, binding.rsbXBMin, binding.rsbXBMax, event);
    }

    public void rsbY(XBoxEvent event) {
        handleGradient(binding.rsbYMin, binding.rsbYMax, binding.rsbYBMin, binding.rsbYBMax, event);
    }

    public void du(XBoxEvent event) {
        handleSwitch(binding.du, event);
    }

    public void dd(XBoxEvent event) {
        handleSwitch(binding.dd, event);
    }

    public void dl(XBoxEvent event) {
        handleSwitch(binding.dl, event);
    }

    public void dr(XBoxEvent event) {
        handleSwitch(binding.dr, event);
    }

    public void back(XBoxEvent event) {
        handleSwitch(binding.back, event);
    }

    public void guide(XBoxEvent event) {
        handleSwitch(binding.guide, event);
    }

    public void start(XBoxEvent event) {
        handleSwitch(binding.start, event);
    }

    public void tl(XBoxEvent event) {
        handleSwitch(binding.tl, event);
    }

    public void tr(XBoxEvent event) {
        handleSwitch(binding.tr, event);
    }

    public void a(XBoxEvent event) {
        handleSwitch(binding.a, event);
    }

    public void b(XBoxEvent event) {
        handleSwitch(binding.b, event);
    }

    public void x(XBoxEvent event) {
        handleSwitch(binding.x, event);
    }

    public void y(XBoxEvent event) {
        handleSwitch(binding.y, event);
    }

    public void lb(XBoxEvent event) {
        handleSwitch(binding.lb, event);
    }

    public void rb(XBoxEvent event) {
        handleSwitch(binding.rb, event);
    }

    public void lt(XBoxEvent event) {
        handleGradient(binding.ltMin, binding.ltMax, event);
    }

    public void rt(XBoxEvent event) {
        handleGradient(binding.rtMin, binding.rtMax, event);
    }
}
