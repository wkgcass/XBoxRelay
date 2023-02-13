package net.cassite.xboxrelay.ui;

import io.vproxy.vfx.entity.input.Key;

import java.util.Objects;

public class KeyOrMouseMove {
    public final Key key;
    public final MouseMove mouseMove;

    public KeyOrMouseMoveDataGroup group;

    public KeyOrMouseMove(Key key) {
        this.key = key;
        this.mouseMove = null;
    }

    public KeyOrMouseMove(MouseMove mouseMove) {
        this.key = null;
        this.mouseMove = mouseMove;
    }

    private KeyOrMouseMove(Key key, MouseMove mouseMove) {
        this.key = key;
        this.mouseMove = mouseMove;
    }

    public boolean needToCancelForSwitchingTo(KeyOrMouseMove km) {
        return (key != null || km.key != null) && Objects.equals(key, km.key);
    }

    public static KeyOrMouseMove copyOf(KeyOrMouseMove o) {
        if (o == null)
            return null;
        return new KeyOrMouseMove(o.key, o.mouseMove);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyOrMouseMove that = (KeyOrMouseMove) o;

        if (!Objects.equals(key, that.key)) return false;
        return Objects.equals(mouseMove, that.mouseMove);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (mouseMove != null ? mouseMove.hashCode() : 0);
        return result;
    }
}
