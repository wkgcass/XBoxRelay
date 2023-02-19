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
    public Integer lsbXB = null;
    public Integer lsbY = null;
    public Integer lsbYB = null;
    public Integer rsbX = null;
    public Integer rsbXB = null;
    public Integer rsbY = null;
    public Integer rsbYB = null;
    public Integer lt = null;
    public Integer rt = null;

    public static final Rule<DeadZoneConfig> rule = new ObjectRule<>(DeadZoneConfig::new)
        .put("lsbX", (o, it) -> o.lsbX = it, IntRule.get())
        .put("lsbXB", (o, it) -> o.lsbXB = it, IntRule.get())
        .put("lsbY", (o, it) -> o.lsbY = it, IntRule.get())
        .put("lsbYB", (o, it) -> o.lsbYB = it, IntRule.get())
        .put("rsbX", (o, it) -> o.rsbX = it, IntRule.get())
        .put("rsbXB", (o, it) -> o.rsbXB = it, IntRule.get())
        .put("rsbY", (o, it) -> o.rsbY = it, IntRule.get())
        .put("rsbYB", (o, it) -> o.rsbYB = it, IntRule.get())
        .put("lt", (o, it) -> o.lt = it, IntRule.get())
        .put("rt", (o, it) -> o.rt = it, IntRule.get());

    public DeadZoneConfig() {
    }

    public static DeadZoneConfig copyOf(DeadZoneConfig config) {
        if (config == null) {
            return null;
        }
        var ret = new DeadZoneConfig();
        ret.lsbX = config.lsbX;
        ret.lsbXB = config.lsbXB;
        ret.lsbY = config.lsbY;
        ret.lsbYB = config.lsbYB;
        ret.rsbX = config.rsbX;
        ret.rsbXB = config.rsbXB;
        ret.rsbY = config.rsbY;
        ret.rsbYB = config.rsbYB;
        ret.lt = config.lt;
        ret.rt = config.rt;
        return ret;
    }

    public boolean isZero() {
        return lsbX == null &&
               lsbXB == null &&
               lsbY == null &&
               lsbYB == null &&
               rsbX == null &&
               rsbXB == null &&
               rsbY == null &&
               rsbYB == null &&
               lt == null &&
               rt == null;
    }

    public boolean valid() {
        return nullOrPositive(lsbX) &&
               nullOrPositive(lsbXB) &&
               nullOrPositive(lsbY) &&
               nullOrPositive(lsbYB) &&
               nullOrPositive(rsbX) &&
               nullOrPositive(rsbXB) &&
               nullOrPositive(rsbY) &&
               nullOrPositive(rsbYB) &&
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
        if (msg.lsbXB != null) {
            lsbXB = msg.lsbXB;
        }
        if (msg.lsbY != null) {
            lsbY = msg.lsbY;
        }
        if (msg.lsbYB != null) {
            lsbYB = msg.lsbYB;
        }
        if (msg.rsbX != null) {
            rsbX = msg.rsbX;
        }
        if (msg.rsbXB != null) {
            rsbXB = msg.rsbXB;
        }
        if (msg.rsbY != null) {
            rsbY = msg.rsbY;
        }
        if (msg.rsbYB != null) {
            rsbYB = msg.rsbYB;
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
        if (lsbXB != null)
            ob.put("lsbXB", lsbXB);
        if (lsbY != null)
            ob.put("lsbY", lsbY);
        if (lsbYB != null)
            ob.put("lsbYB", lsbYB);
        if (rsbX != null)
            ob.put("rsbX", rsbX);
        if (rsbXB != null)
            ob.put("rsbXB", rsbXB);
        if (rsbY != null)
            ob.put("rsbY", rsbY);
        if (rsbYB != null)
            ob.put("rsbYB", rsbYB);
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
               ", lsbXB=" + lsbXB +
               ", lsbY=" + lsbY +
               ", lsbYB=" + lsbYB +
               ", rsbX=" + rsbX +
               ", rsbXB=" + rsbXB +
               ", rsbY=" + rsbY +
               ", rsbYB=" + rsbYB +
               ", lt=" + lt +
               ", rt=" + rt +
               '}';
    }
}
