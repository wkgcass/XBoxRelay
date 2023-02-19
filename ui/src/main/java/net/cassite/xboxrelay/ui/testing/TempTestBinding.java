package net.cassite.xboxrelay.ui.testing;

import io.vproxy.vfx.entity.input.Key;
import io.vproxy.vfx.entity.input.KeyCode;
import javafx.scene.input.MouseButton;
import net.cassite.xboxrelay.ui.Binding;
import net.cassite.xboxrelay.ui.KeyOrMouse;
import net.cassite.xboxrelay.ui.MouseMove;

public class TempTestBinding extends Binding {
    public TempTestBinding() {
        lsbXMin = new KeyOrMouse(new Key(KeyCode.D));
        lsbXMax = lsbXMin;
        lsbYMin = new KeyOrMouse(new Key(KeyCode.W));
        lsbYMax = lsbYMin;
        lsbXBMin = new KeyOrMouse(new Key(KeyCode.A));
        lsbXBMax = lsbXBMin;
        lsbYBMin = new KeyOrMouse(new Key(KeyCode.S));
        lsbYBMax = lsbYBMin;

        rsbXMin = new KeyOrMouse(new MouseMove(1000, 0));
        rsbXMax = new KeyOrMouse(new MouseMove(1500, 0));
        rsbYMin = new KeyOrMouse(new MouseMove(0, -800));
        rsbYMax = new KeyOrMouse(new MouseMove(0, -1200));
        rsbXBMin = new KeyOrMouse(new MouseMove(-1000, 0));
        rsbXBMax = new KeyOrMouse(new MouseMove(-1500, 0));
        rsbYBMin = new KeyOrMouse(new MouseMove(0, 800));
        rsbYBMax = new KeyOrMouse(new MouseMove(0, 1200));

        du = new KeyOrMouse(new Key(KeyCode.G));
        dd = new KeyOrMouse(new Key(KeyCode.KEY_1));
        dl = new KeyOrMouse(new Key(KeyCode.KEY_2));
        dr = new KeyOrMouse(new Key(KeyCode.KEY_3));
        back = new KeyOrMouse(new Key(KeyCode.F));
        guide = null;
        start = new KeyOrMouse(new Key(KeyCode.ESCAPE));
        tl = new KeyOrMouse(new Key(KeyCode.V));
        tr = new KeyOrMouse(new Key(KeyCode.F2));
        a = new KeyOrMouse(new Key(KeyCode.SPACE));
        b = new KeyOrMouse(new Key(MouseButton.PRIMARY));
        x = new KeyOrMouse(new Key(KeyCode.CONTROL, true));
        y = new KeyOrMouse(new Key(KeyCode.C));
        lb = new KeyOrMouse(new Key(KeyCode.Q));
        rb = new KeyOrMouse(new Key(KeyCode.E));
        ltMin = new KeyOrMouse(new Key(MouseButton.PRIMARY));
        ltMax = ltMin;
        rtMin = new KeyOrMouse(new Key(MouseButton.SECONDARY));
        rtMax = rtMin;
    }
}
