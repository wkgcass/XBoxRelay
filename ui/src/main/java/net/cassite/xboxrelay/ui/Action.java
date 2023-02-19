package net.cassite.xboxrelay.ui;

import io.vproxy.vfx.entity.input.Key;
import io.vproxy.vfx.util.MiscUtils;
import org.jetbrains.annotations.NotNull;
import vjson.JSON;
import vjson.JSONObject;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.util.ObjectBuilder;

import java.util.Objects;

public class Action implements JSONObject {
    public final Key key;
    public final MouseMove mouseMove;
    public final MouseWheel mouseWheel;

    public static final Rule<Action> rule = ObjectRule.builder(ActionBuilder::new, ActionBuilder::build, builder -> builder
        .put("key", (o, it) -> o.key = it, Key.rule)
        .put("mouseMove", (o, it) -> o.mouseMove = it, MouseMove.rule)
        .put("mouseWheel", (o, it) -> o.mouseWheel = it, MouseWheel.rule)
    );

    private static class ActionBuilder {
        Key key;
        MouseMove mouseMove;
        MouseWheel mouseWheel;

        Action build() {
            if (key != null) {
                return new Action(key);
            } else if (mouseMove != null) {
                return new Action(mouseMove);
            } else if (mouseWheel != null) {
                return new Action(mouseWheel);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public ActionDataGroup group;

    public Action(Key key) {
        this.key = key;
        this.mouseMove = null;
        this.mouseWheel = null;
    }

    public Action(MouseMove mouseMove) {
        this.key = null;
        this.mouseMove = mouseMove;
        this.mouseWheel = null;
    }

    public Action(MouseWheel mouseWheel) {
        this.key = null;
        this.mouseMove = null;
        this.mouseWheel = mouseWheel;
    }

    private Action(Key key, MouseMove mouseMove, MouseWheel mouseWheel) {
        this.key = key;
        this.mouseMove = mouseMove;
        this.mouseWheel = mouseWheel;
    }

    public boolean needToCancelForSwitchingTo(Action action) {
        return (key != null || action.key != null) && Objects.equals(key, action.key);
    }

    public static Action copyOf(Action o) {
        if (o == null)
            return null;
        return new Action(o.key, o.mouseMove, o.mouseWheel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action that = (Action) o;

        if (!Objects.equals(key, that.key)) return false;
        if (!Objects.equals(mouseMove, that.mouseMove)) return false;
        return Objects.equals(mouseWheel, that.mouseWheel);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (mouseMove != null ? mouseMove.hashCode() : 0);
        result = 31 * result + (mouseWheel != null ? mouseWheel.hashCode() : 0);
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
        return ob.build();
    }

    @Override
    public String toString() {
        if (key != null) {
            return "key:" + key;
        } else if (mouseMove != null) {
            return "move:" + MiscUtils.roughFloatValueFormat.format(mouseMove.x) + ", " + MiscUtils.roughFloatValueFormat.format(mouseMove.y);
        } else if (mouseWheel != null) {
            return "wheel:" + MiscUtils.roughFloatValueFormat.format(mouseWheel.wheelAmt);
        } else {
            return "Unknown";
        }
    }
}
