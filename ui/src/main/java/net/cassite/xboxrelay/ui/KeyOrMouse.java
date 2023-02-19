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

public class KeyOrMouse implements JSONObject {
    public final Key key;
    public final MouseMove mouseMove;
    public final MouseWheel mouseWheel;

    public static final Rule<KeyOrMouse> rule = ObjectRule.builder(KeyOrMouseBuilder::new, KeyOrMouseBuilder::build, builder -> builder
        .put("key", (o, it) -> o.key = it, Key.rule)
        .put("mouseMove", (o, it) -> o.mouseMove = it, MouseMove.rule)
        .put("mouseWheel", (o, it) -> o.mouseWheel = it, MouseWheel.rule)
    );

    private static class KeyOrMouseBuilder {
        Key key;
        MouseMove mouseMove;
        MouseWheel mouseWheel;

        KeyOrMouse build() {
            if (key != null) {
                return new KeyOrMouse(key);
            } else if (mouseMove != null) {
                return new KeyOrMouse(mouseMove);
            } else if (mouseWheel != null) {
                return new KeyOrMouse(mouseWheel);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public KeyOrMouseDataGroup group;

    public KeyOrMouse(Key key) {
        this.key = key;
        this.mouseMove = null;
        this.mouseWheel = null;
    }

    public KeyOrMouse(MouseMove mouseMove) {
        this.key = null;
        this.mouseMove = mouseMove;
        this.mouseWheel = null;
    }

    public KeyOrMouse(MouseWheel mouseWheel) {
        this.key = null;
        this.mouseMove = null;
        this.mouseWheel = mouseWheel;
    }

    private KeyOrMouse(Key key, MouseMove mouseMove, MouseWheel mouseWheel) {
        this.key = key;
        this.mouseMove = mouseMove;
        this.mouseWheel = mouseWheel;
    }

    public boolean needToCancelForSwitchingTo(KeyOrMouse km) {
        return (key != null || km.key != null) && Objects.equals(key, km.key);
    }

    public static KeyOrMouse copyOf(KeyOrMouse o) {
        if (o == null)
            return null;
        return new KeyOrMouse(o.key, o.mouseMove, o.mouseWheel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyOrMouse that = (KeyOrMouse) o;

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
