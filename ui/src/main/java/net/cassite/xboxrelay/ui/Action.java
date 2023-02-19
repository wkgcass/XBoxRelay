package net.cassite.xboxrelay.ui;

import io.vproxy.vfx.entity.input.InputData;
import io.vproxy.vfx.entity.input.Key;
import io.vproxy.vfx.util.MiscUtils;
import org.jetbrains.annotations.NotNull;
import vjson.JSON;
import vjson.JSONObject;
import vjson.deserializer.rule.BoolRule;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.util.ObjectBuilder;

import java.util.Objects;

public class Action implements JSONObject {
    public final Key key;
    public final MouseMove mouseMove;
    public final MouseWheel mouseWheel;
    public final boolean fn;
    public InputData fnInput;

    public static final Rule<Action> rule = ObjectRule.builder(ActionBuilder::new, ActionBuilder::build, builder -> builder
        .put("key", (o, it) -> o.key = it, Key.rule)
        .put("mouseMove", (o, it) -> o.mouseMove = it, MouseMove.rule)
        .put("mouseWheel", (o, it) -> o.mouseWheel = it, MouseWheel.rule)
        .put("fn", (o, it) -> o.fn = it, BoolRule.get())
        .put("fnInput", (o, it) -> o.fnInput = it, InputData.rule)
    );

    private static class ActionBuilder {
        Key key;
        MouseMove mouseMove;
        MouseWheel mouseWheel;
        boolean fn;
        InputData fnInput;

        Action build() {
            Action ret;
            if (key != null) {
                ret = new Action(key);
            } else if (mouseMove != null) {
                ret = new Action(mouseMove);
            } else if (mouseWheel != null) {
                ret = new Action(mouseWheel);
            } else if (fn) {
                ret = new Action(true);
            } else {
                throw new IllegalStateException();
            }
            ret.fnInput = fnInput;
            return ret;
        }
    }

    public ActionDataGroup group;

    public Action(Key key) {
        this.key = key;
        this.mouseMove = null;
        this.mouseWheel = null;
        this.fn = false;
    }

    public Action(MouseMove mouseMove) {
        this.key = null;
        this.mouseMove = mouseMove;
        this.mouseWheel = null;
        this.fn = false;
    }

    public Action(MouseWheel mouseWheel) {
        this.key = null;
        this.mouseMove = null;
        this.mouseWheel = mouseWheel;
        this.fn = false;
    }

    private Action(@SuppressWarnings("unused") boolean fn) {
        this.key = null;
        this.mouseMove = null;
        this.mouseWheel = null;
        this.fn = true;
    }

    public static Action newFn() {
        return new Action(true);
    }

    private Action(@SuppressWarnings("unused") Void v) {
        this.key = null;
        this.mouseMove = null;
        this.mouseWheel = null;
        this.fn = false;
    }

    public static Action newEmpty() {
        return new Action((Void) null);
    }

    @SuppressWarnings("CopyConstructorMissesField")
    private Action(Action action) {
        this.key = action.key;
        this.mouseMove = action.mouseMove;
        this.mouseWheel = action.mouseWheel;
        this.fn = action.fn;
        this.fnInput = action.fnInput;
    }

    public boolean needToCancelForSwitchingTo(Action action) {
        return (key != null || action.key != null) && Objects.equals(key, action.key);
    }

    public static Action copyOf(Action o) {
        if (o == null)
            return null;
        return new Action(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action = (Action) o;

        if (fn != action.fn) return false;
        if (!Objects.equals(key, action.key)) return false;
        if (!Objects.equals(mouseMove, action.mouseMove)) return false;
        if (!Objects.equals(mouseWheel, action.mouseWheel)) return false;
        return Objects.equals(fnInput, action.fnInput);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (mouseMove != null ? mouseMove.hashCode() : 0);
        result = 31 * result + (mouseWheel != null ? mouseWheel.hashCode() : 0);
        result = 31 * result + (fn ? 1 : 0);
        result = 31 * result + (fnInput != null ? fnInput.hashCode() : 0);
        return result;
    }

    @NotNull
    @Override
    public JSON.Object toJson() {
        var ob = new ObjectBuilder();
        if (key != null) {
            ob.putInst("key", key.toJson());
        }
        if (mouseMove != null) {
            ob.putInst("mouseMove", mouseMove.toJson());
        }
        if (mouseWheel != null) {
            ob.putInst("mouseWheel", mouseWheel.toJson());
        }
        if (fn) {
            ob.put("fn", true);
        }
        if (fnInput != null) {
            ob.putInst("fnInput", fnInput.toJson());
        }
        return ob.build();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        if (key != null) {
            sb.append("key: ").append(key);
        } else if (mouseMove != null) {
            sb.append("move: ")
                .append(MiscUtils.roughFloatValueFormat.format(mouseMove.x))
                .append(", ")
                .append(MiscUtils.roughFloatValueFormat.format(mouseMove.y));
        } else if (mouseWheel != null) {
            sb.append("wheel:")
                .append(MiscUtils.roughFloatValueFormat.format(mouseWheel.wheelAmt));
        } else if (fn) {
            sb.append("FN");
        }
        if (sb.isEmpty()) {
            if (fnInput == null) {
                sb.append("Unknown");
            } else {
                formatInput(sb);
            }
        } else {
            if (fnInput != null) {
                sb.append("\n");
                formatInput(sb);
            }
        }
        return sb.toString();
    }

    private void formatInput(StringBuilder sb) {
        sb.append("FN: ");
        if (fnInput.ctrl) {
            sb.append("c");
        }
        if (fnInput.alt) {
            sb.append("a");
        }
        if (fnInput.shift) {
            sb.append("s");
        }
        if (fnInput.ctrl || fnInput.alt || fnInput.shift) {
            sb.append("-");
        }
        sb.append(fnInput.key);
    }
}
