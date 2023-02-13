package net.cassite.xboxrelay.base;

import org.jetbrains.annotations.NotNull;
import vjson.JSON;
import vjson.JSONObject;
import vjson.deserializer.rule.IntRule;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.util.ObjectBuilder;

public class DeadZoneConfig implements JSONObject {
    public Integer lsbX = null;
    public Integer lsbY = null;
    public Integer rsbX = null;
    public Integer rsbY = null;
    public Integer lt = null;
    public Integer rt = null;

    public static final Rule<DeadZoneConfig> rule = new ObjectRule<>(DeadZoneConfig::new)
        .put("lsbX", (o, it) -> o.lsbX = it, IntRule.get())
        .put("lsbY", (o, it) -> o.lsbY = it, IntRule.get())
        .put("rsbX", (o, it) -> o.rsbX = it, IntRule.get())
        .put("rsbY", (o, it) -> o.rsbY = it, IntRule.get())
        .put("lt", (o, it) -> o.lt = it, IntRule.get())
        .put("rt", (o, it) -> o.rt = it, IntRule.get());

    public DeadZoneConfig() {
    }

    public boolean isZero() {
        return lsbX == null &&
               lsbY == null &&
               rsbX == null &&
               rsbY == null &&
               lt == null &&
               rt == null;
    }

    public boolean valid() {
        return nullOrPositive(lsbX) &&
               nullOrPositive(lsbY) &&
               nullOrPositive(rsbX) &&
               nullOrPositive(rsbY) &&
               nullOrPositive(lt) &&
               nullOrPositive(rt);
    }

    private boolean nullOrPositive(Integer v) {
        if (v == null) return true;
        return v > 0;
    }

    public void from(DeadZoneConfig msg) {
        if (msg.lsbX != null) {
            lsbX = msg.lsbX;
        }
        if (msg.lsbY != null) {
            lsbY = msg.lsbY;
        }
        if (msg.rsbX != null) {
            rsbX = msg.rsbX;
        }
        if (msg.rsbY != null) {
            rsbY = msg.rsbY;
        }
        if (msg.lt != null) {
            lt = msg.lt;
        }
        if (msg.rt != null) {
            rt = msg.rt;
        }
    }

    @NotNull
    @Override
    public JSON.Object toJson() {
        var ob = new ObjectBuilder();
        if (lsbX != null)
            ob.put("lsbX", lsbX);
        if (lsbY != null)
            ob.put("lsbY", lsbY);
        if (rsbX != null)
            ob.put("rsbX", rsbX);
        if (rsbY != null)
            ob.put("rsbY", rsbY);
        if (lt != null)
            ob.put("lt", lt);
        if (rt != null)
            ob.put("rt", rt);
        return ob.build();
    }

    @Override
    public String toString() {
        return "DeadZoneConfig{" +
               "lsbX=" + lsbX +
               ", lsbY=" + lsbY +
               ", rsbX=" + rsbX +
               ", rsbY=" + rsbY +
               ", lt=" + lt +
               ", rt=" + rt +
               '}';
    }
}
