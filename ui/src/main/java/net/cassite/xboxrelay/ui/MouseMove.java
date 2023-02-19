package net.cassite.xboxrelay.ui;

import org.jetbrains.annotations.NotNull;
import vjson.JSON;
import vjson.JSONObject;
import vjson.deserializer.rule.DoubleRule;
import vjson.deserializer.rule.ObjectRule;
import vjson.deserializer.rule.Rule;
import vjson.util.ObjectBuilder;

public class MouseMove implements JSONObject {
    public final double x; // pix per sec
    public final double y; // pix per sec

    public static final Rule<MouseMove> rule = ObjectRule.builder(MouseMoveBuilder::new, MouseMoveBuilder::build, builder -> builder
        .put("x", (o, it) -> o.x = it, DoubleRule.get())
        .put("y", (o, it) -> o.y = it, DoubleRule.get())
    );

    private static class MouseMoveBuilder {
        double x;
        double y;

        MouseMove build() {
            return new MouseMove(x, y);
        }
    }

    public MouseMove(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MouseMove mouseMove = (MouseMove) o;

        if (Double.compare(mouseMove.x, x) != 0) return false;
        return Double.compare(mouseMove.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @NotNull
    @Override
    public JSON.Object toJson() {
        return new ObjectBuilder()
            .put("x", x)
            .put("y", y)
            .build();
    }
}
