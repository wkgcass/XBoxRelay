package net.cassite.xboxrelay.ui;

import vjson.JSON;
import vjson.JSONObject;
import vjson.deserializer.rule.DoubleRule;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.util.ObjectBuilder;

public class MouseWheel implements JSONObject {
    public double wheelAmt;

    public static final Rule<MouseWheel> rule = ObjectRule.builder(MouseWheelBuilder::new, MouseWheelBuilder::build, builder -> builder
        .put("wheelAmt", (o, it) -> o.wheelAmt = it, DoubleRule.get())
    );

    private static class MouseWheelBuilder {
        double wheelAmt;

        MouseWheel build() {
            return new MouseWheel(wheelAmt);
        }
    }

    public MouseWheel(double wheelAmt) {
        this.wheelAmt = wheelAmt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MouseWheel that = (MouseWheel) o;

        return Double.compare(that.wheelAmt, wheelAmt) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(wheelAmt);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public JSON.Object toJson() {
        return new ObjectBuilder()
            .put("wheelAmt", wheelAmt)
            .build();
    }
}
