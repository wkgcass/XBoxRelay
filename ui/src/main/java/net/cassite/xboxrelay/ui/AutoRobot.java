package net.cassite.xboxrelay.ui;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;
import io.vproxy.base.util.OS;
import io.vproxy.vfx.entity.input.Key;
import io.vproxy.vfx.entity.input.KeyCode;
import io.vproxy.vfx.robot.RobotWrapper;
import io.vproxy.vfx.util.FXUtils;
import javafx.animation.AnimationTimer;
import net.cassite.xboxrelay.base.TriggerLevel;
import net.cassite.xboxrelay.base.XBoxEvent;

import java.util.*;

public class AutoRobot {
    private final Binding binding;
    private final List<ActionDataGroup> groups;
    private boolean isRunning = false;
    private final Timer timer;
    private final RobotWrapper robot;
    private boolean fnEnabled = false;
    private boolean isHandlingFnInput = false;
    private final State state = new State();

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
                if (OS.isWindows()) {
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
        cancel(group, true);
    }

    private void cancel(ActionDataGroup group, boolean release) {
        var current = group.current;
        if (current == null) {
            return;
        }
        if (current.key != null && release) {
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
        if (fnEnabled) {
            group.current = action;
            return;
        }
        if (action.fn) {
            enableFn(group, action);
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

    private void enableFn(ActionDataGroup group, Action action) {
        if (fnEnabled) {
            return;
        }
        fnEnabled = true;
        state.setPaused(true);
        // cancel all
        for (var g : groups) {
            cancel(g);
        }
        group.current = action;
    }

    private void disableFn() {
        fnEnabled = false;
        state.setPaused(false);
        state.apply(this);
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
            case OFF -> {
                if (fnEnabled && group.current != null) {
                    if (group.current == min) {
                        triggerFnInput(min);
                    } else if (group.current == max) {
                        triggerFnInput(max);
                    } else if (group.current == bMin) {
                        triggerFnInput(bMin);
                    } else if (group.current == bMax) {
                        triggerFnInput(bMax);
                    }
                    cancel(group, false);
                } else {
                    cancel(group);
                }
            }
            case MIN -> {
                if (fnEnabled && max != null && group.current == max) {
                    triggerFnInput(max);
                    cancel(group, false);
                } else {
                    apply(group, min, level);
                }
            }
            case MAX -> apply(group, max, min, level);
            case B_MIN -> {
                if (fnEnabled && bMax != null && group.current == bMax) {
                    triggerFnInput(bMax);
                    cancel(group, false);
                } else {
                    apply(group, bMin, level);
                }
            }
            case B_MAX -> apply(group, bMax, level);
        }
    }

    private void handleSwitch(Action action, XBoxEvent event) {
        var level = event.level;
        var group = action.group;
        if (level == TriggerLevel.OFF) {
            if (fnEnabled) {
                triggerFnInput(action);
                cancel(group, false);
            } else {
                cancel(group);
            }
        } else {
            apply(group, action, level);
        }
    }

    private void triggerFnInput(Action action) {
        if (action.fn) {
            // the fn action triggers fn input means to cancel the fn state
            disableFn();
            return;
        }
        if (!fnEnabled) {
            return;
        }
        if (isHandlingFnInput) {
            return;
        }
        var input = action.fnInput;
        if (input == null) {
            return;
        }
        isHandlingFnInput = true;
        var pressed = new ArrayList<Key>(4);
        if (input.ctrl) {
            var ctrl = new Key(KeyCode.CONTROL);
            pressed.add(ctrl);
        }
        if (input.alt) {
            var alt = new Key(KeyCode.ALT);
            pressed.add(alt);
        }
        if (input.shift) {
            var shift = new Key(KeyCode.SHIFT);
            pressed.add(shift);
        }
        pressed.add(input.key);
        handleFnInputPress(pressed.listIterator());
    }

    private void handleFnInputPress(ListIterator<Key> ite) {
        if (!ite.hasNext()) {
            handleFnInputRelease(ite);
            return;
        }
        var key = ite.next();
        robot.press(key);
        FXUtils.runDelay(50, () -> handleFnInputPress(ite));
    }

    private void handleFnInputRelease(ListIterator<Key> ite) {
        if (!ite.hasPrevious()) {
            isHandlingFnInput = false;
            return;
        }
        var key = ite.previous();
        robot.release(key);
        handleFnInputRelease(ite);
    }

    public void lsbX(XBoxEvent event) {
        state.lsbX(event.level);
        handleGradient(binding.lsbXMin, binding.lsbXMax, binding.lsbXBMin, binding.lsbXBMax, event);
    }

    public void lsbY(XBoxEvent event) {
        state.lsbY(event.level);
        handleGradient(binding.lsbYMin, binding.lsbYMax, binding.lsbYBMin, binding.lsbYBMax, event);
    }

    public void rsbX(XBoxEvent event) {
        state.rsbX(event.level);
        handleGradient(binding.rsbXMin, binding.rsbXMax, binding.rsbXBMin, binding.rsbXBMax, event);
    }

    public void rsbY(XBoxEvent event) {
        state.rsbY(event.level);
        handleGradient(binding.rsbYMin, binding.rsbYMax, binding.rsbYBMin, binding.rsbYBMax, event);
    }

    public void du(XBoxEvent event) {
        state.du(event.level);
        handleSwitch(binding.du, event);
    }

    public void dd(XBoxEvent event) {
        state.dd(event.level);
        handleSwitch(binding.dd, event);
    }

    public void dl(XBoxEvent event) {
        state.dl(event.level);
        handleSwitch(binding.dl, event);
    }

    public void dr(XBoxEvent event) {
        state.dr(event.level);
        handleSwitch(binding.dr, event);
    }

    public void back(XBoxEvent event) {
        state.back(event.level);
        handleSwitch(binding.back, event);
    }

    public void guide(XBoxEvent event) {
        state.guide(event.level);
        handleSwitch(binding.guide, event);
    }

    public void start(XBoxEvent event) {
        state.start(event.level);
        handleSwitch(binding.start, event);
    }

    public void tl(XBoxEvent event) {
        state.tl(event.level);
        handleSwitch(binding.tl, event);
    }

    public void tr(XBoxEvent event) {
        state.tr(event.level);
        handleSwitch(binding.tr, event);
    }

    public void a(XBoxEvent event) {
        state.a(event.level);
        handleSwitch(binding.a, event);
    }

    public void b(XBoxEvent event) {
        state.b(event.level);
        handleSwitch(binding.b, event);
    }

    public void x(XBoxEvent event) {
        state.x(event.level);
        handleSwitch(binding.x, event);
    }

    public void y(XBoxEvent event) {
        state.y(event.level);
        handleSwitch(binding.y, event);
    }

    public void lb(XBoxEvent event) {
        state.lb(event.level);
        handleSwitch(binding.lb, event);
    }

    public void rb(XBoxEvent event) {
        state.rb(event.level);
        handleSwitch(binding.rb, event);
    }

    public void lt(XBoxEvent event) {
        state.lt(event.level);
        handleGradient(binding.ltMin, binding.ltMax, event);
    }

    public void rt(XBoxEvent event) {
        state.rt(event.level);
        handleGradient(binding.rtMin, binding.rtMax, event);
    }
}
