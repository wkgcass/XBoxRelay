package net.cassite.xboxrelay.ui;

public class MouseMove {
    public final double x; // pix per sec
    public final double y; // pix per sec

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
}
