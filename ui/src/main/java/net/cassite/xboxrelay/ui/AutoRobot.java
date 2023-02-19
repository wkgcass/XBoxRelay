package net.cassite.xboxrelay.ui;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;
import io.vproxy.vfx.robot.RobotWrapper;
import io.vproxy.vfx.util.OSUtils;
import javafx.animation.AnimationTimer;
import net.cassite.xboxrelay.base.TriggerLevel;
import net.cassite.xboxrelay.base.XBoxEvent;

import java.util.List;
import java.util.Objects;

public class AutoRobot {
    private final Binding binding;
    private final List<ActionDataGroup> groups;
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
            var dxMouse = new int[]{0};
            var dyMouse = new int[]{0};
            var wheel = new int[]{0};
            for (var g : groups) {
                var current = g.current;
                if (current == null) {
                    continue;
                }
                if (current.mouseMove != null) {
                    handleMouseMove(l, g, dxMouse, dyMouse);
                }
                if (current.mouseWheel != null) {
                    handleMouseWheel(l, g, wheel);
                }
            }
            if (dxMouse[0] != 0 || dyMouse[0] != 0) {
                if (OSUtils.isWindows()) {
                    JNAMouseEvent.User32.INSTANCE.mouse_event(new WinDef.DWORD(JNAMouseEvent.User32.MOUSEEVENTF_MOVE),
                        new WinDef.DWORD(dxMouse[0]), new WinDef.DWORD(dyMouse[0]),
                        new WinDef.DWORD(0), new BaseTSD.ULONG_PTR(0));
                } else {
                    var mousePoint2D = robot.getMousePosition();
                    var mouseX = mousePoint2D.getX();
                    var mouseY = mousePoint2D.getY();
                    mouseX += dxMouse[0];
                    mouseY += dyMouse[0];
                    robot.mouseMove(mouseX, mouseY);
                }
            }
            if (wheel[0] != 0) {
                robot.mouseWheel(wheel[0]);
            }
        }

        private void handleMouseMove(long l, ActionDataGroup g, int[] dxMouse, int[] dyMouse) {
            if (g.lastMouseMovingTs == 0 || g.lastMouseMovingTs > l) {
                g.lastMouseMovingTs = l;
                return;
            }
            var current = g.current;
            var delta = l - g.lastMouseMovingTs;
            var dx = Math.round(current.mouseMove.x / 1000d * (delta / 1_000_000d));
            var dy = Math.round(current.mouseMove.y / 1000d * (delta / 1_000_000d));
            if ((dx == 0 && current.mouseMove.x != 0) || (dy == 0 && current.mouseMove.y != 0)) {
                return;
            }
            dxMouse[0] += dx;
            dyMouse[0] += dy;
            g.lastMouseMovingTs = l;
        }

        private void handleMouseWheel(long l, ActionDataGroup g, int[] wheel) {
            if (g.lastMouseWheelTs == 0 || g.lastMouseWheelTs > l) {
                g.lastMouseWheelTs = l;
                return;
            }
            var current = g.current;
            var delta = l - g.lastMouseWheelTs;
            var d = Math.round(current.mouseWheel.wheelAmt / 1000d * (delta / 1_000_000d));
            if (d == 0 && current.mouseWheel.wheelAmt != 0) {
                return;
            }
            wheel[0] += d;
            g.lastMouseWheelTs = l;
        }
    }

    private void cancel(ActionDataGroup group) {
        var current = group.current;
        if (current == null) {
            return;
        }
        if (current.key != null) {
            robot.release(current.key);
        }
        group.current = null;
        group.lastMouseMovingTs = 0;
        group.lastMouseWheelTs = 0;
        group.level = TriggerLevel.OFF;
    }

    private void apply(ActionDataGroup group, Action action, TriggerLevel level) {
        apply(group, action, null, level);
    }

    private void apply(ActionDataGroup group, Action action, Action backup, TriggerLevel level) {
        if (action == null) {
            action = backup;
        }
        if (action == null) {
            return;
        }
        if (group.current != null) {
            if (Objects.equals(group.current, action)) {
                group.level = level;
                return;
            }
            if (group.current.needToCancelForSwitchingTo(action)) {
                cancel(group);
            }
        }
        group.current = action;
        group.level = level;
        final var fkm = action;
        if (action.key != null) {
            robot.press(fkm.key);
        }
    }

    private void handleGradient(Action min, Action max, XBoxEvent event) {
        handleGradient(min, max, null, null, event);
    }

    private void handleGradient(Action min, Action max, Action bMin, Action bMax, XBoxEvent event) {
        ActionDataGroup group;
        if (min != null) {
            group = min.group;
        } else if (max != null) {
            group = max.group;
        } else if (bMin != null) {
            group = bMin.group;
        } else if (bMax != null) {
            group = bMax.group;
        } else { // nothing to be handled
            return;
        }
        var level = event.level;
        switch (level) {
            case OFF -> cancel(group);
            case MIN -> apply(group, min, level);
            case MAX -> apply(group, max, min, level);
            case B_MIN -> apply(group, bMin, level);
            case B_MAX -> apply(group, bMax, level);
        }
    }

    private void handleSwitch(Action action, XBoxEvent event) {
        var level = event.level;
        var group = action.group;
        if (level == TriggerLevel.OFF) {
            cancel(group);
        } else {
            apply(group, action, level);
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
