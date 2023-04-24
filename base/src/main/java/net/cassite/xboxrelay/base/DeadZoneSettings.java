package net.cassite.xboxrelay.base;

import vjson.JSON;
import vjson.JSONObject;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.util.ObjectBuilder;

public class DeadZoneSettings implements JSONObject {
    public DeadZoneConfig min = null;
    public DeadZoneConfig max = null;

    public static final Rule<DeadZoneSettings> rule = new ObjectRule<>(DeadZoneSettings::new)
        .put("min", (o, it) -> o.min = it, DeadZoneConfig.rule)
        .put("max", (o, it) -> o.max = it, DeadZoneConfig.rule);

    public DeadZoneSettings() {
    }

    public DeadZoneSettings(DeadZoneSettings that) {
        min = DeadZoneConfig.copyOf(that.min);
        max = DeadZoneConfig.copyOf(that.max);
    }

    public boolean validForInitialControlMessage() {
        return min != null && !min.isZero() && max != null && !max.isZero();
    }

    @SuppressWarnings("RedundantIfStatement")
    public boolean valid() {
        if (min != null) {
            if (!min.valid()) {
                return false;
            }
        }
        if (max != null) {
            if (!max.valid()) {
                return false;
            }
        }
        return true;
    }

    public void from(DeadZoneSettings s) {
        if (min == null) {
            min = s.min;
        } else if (s.min != null) {
            min.from(s.min);
        }
        if (max == null) {
            max = s.max;
        } else if (s.max != null) {
            max.from(s.max);
        }
    }

    @Override
    public JSON.Object toJson() {
        var ob = new ObjectBuilder();
        if (min != null && !min.isZero())
            ob.putInst("min", min.toJson());
        if (max != null && !max.isZero())
            ob.putInst("max", max.toJson());
        return ob.build();
    }

    @Override
    public String toString() {
        return "DeadZoneSettings{" +
               "min=" + min +
               ", max=" + max +
               '}';
    }
}
