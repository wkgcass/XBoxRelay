package net.cassite.xboxrelay.ui.testing;

import io.vproxy.vfx.entity.input.Key;
import io.vproxy.vfx.entity.input.KeyCode;
import javafx.scene.input.MouseButton;
import net.cassite.xboxrelay.ui.Binding;
import net.cassite.xboxrelay.ui.KeyOrMouseMove;
import net.cassite.xboxrelay.ui.MouseMove;

public class TempTestBinding extends Binding {
    public TempTestBinding() {
        lsbXMin = new KeyOrMouseMove(new Key(KeyCode.D));
        lsbXMax = lsbXMin;
        lsbYMin = new KeyOrMouseMove(new Key(KeyCode.W));
        lsbYMax = lsbYMin;
        lsbXBMin = new KeyOrMouseMove(new Key(KeyCode.A));
        lsbXBMax = lsbXBMin;
        lsbYBMin = new KeyOrMouseMove(new Key(KeyCode.S));
        lsbYBMax = lsbYBMin;

        rsbXMin = new KeyOrMouseMove(new MouseMove(1000, 0));
        rsbXMax = new KeyOrMouseMove(new MouseMove(1500, 0));
        rsbYMin = new KeyOrMouseMove(new MouseMove(0, -800));
        rsbYMax = new KeyOrMouseMove(new MouseMove(0, -1200));
        rsbXBMin = new KeyOrMouseMove(new MouseMove(-1000, 0));
        rsbXBMax = new KeyOrMouseMove(new MouseMove(-1500, 0));
        rsbYBMin = new KeyOrMouseMove(new MouseMove(0, 800));
        rsbYBMax = new KeyOrMouseMove(new MouseMove(0, 1200));

        du = new KeyOrMouseMove(new Key(KeyCode.G));
        dd = new KeyOrMouseMove(new Key(KeyCode.KEY_1));
        dl = new KeyOrMouseMove(new Key(KeyCode.KEY_2));
        dr = new KeyOrMouseMove(new Key(KeyCode.KEY_3));
        back = new KeyOrMouseMove(new Key(KeyCode.F));
        guide = null;
        start = new KeyOrMouseMove(new Key(KeyCode.ESCAPE));
        tl = new KeyOrMouseMove(new Key(KeyCode.V));
        tr = new KeyOrMouseMove(new Key(KeyCode.F2));
        a = new KeyOrMouseMove(new Key(KeyCode.SPACE));
        b = new KeyOrMouseMove(new Key(MouseButton.PRIMARY));
        x = new KeyOrMouseMove(new Key(KeyCode.CONTROL, true));
        y = new KeyOrMouseMove(new Key(KeyCode.C));
        lb = new KeyOrMouseMove(new Key(KeyCode.Q));
        rb = new KeyOrMouseMove(new Key(KeyCode.E));
        ltMin = new KeyOrMouseMove(new Key(MouseButton.PRIMARY));
        ltMax = ltMin;
        rtMin = new KeyOrMouseMove(new Key(MouseButton.SECONDARY));
        rtMax = rtMin;
    }
}
