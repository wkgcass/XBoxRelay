package net.cassite.xboxrelay.ui.prebuilt;

import io.vproxy.vfx.entity.input.Key;
import io.vproxy.vfx.entity.input.KeyCode;
import javafx.scene.input.MouseButton;
import net.cassite.xboxrelay.ui.Binding;
import net.cassite.xboxrelay.ui.KeyOrMouse;
import net.cassite.xboxrelay.ui.MouseMove;

public class TowerOfFantasyBinding extends Binding {
    public TowerOfFantasyBinding() {
        b = new KeyOrMouse(new Key(MouseButton.PRIMARY));
        y = new KeyOrMouse(new Key(KeyCode.KEY_1));
        a = new KeyOrMouse(new Key(KeyCode.SPACE));
        x = new KeyOrMouse(new Key(KeyCode.SHIFT, true));
        rtMin = new KeyOrMouse(new Key(KeyCode.KEY_2));
        rtMax = rtMin;
        rb = new KeyOrMouse(new Key(KeyCode.KEY_3));
        du = new KeyOrMouse(new Key(KeyCode.G));
        dl = new KeyOrMouse(new Key(KeyCode.Q));
        dr = new KeyOrMouse(new Key(KeyCode.R));
        dd = new KeyOrMouse(new Key(KeyCode.R));
        tl = new KeyOrMouse(new Key(KeyCode.V));
        tr = new KeyOrMouse(new Key(KeyCode.F2));
        ltMin = new KeyOrMouse(new Key(MouseButton.PRIMARY));
        ltMax = ltMin;
        start = new KeyOrMouse(new Key(KeyCode.ESCAPE));

        lsbXMin = new KeyOrMouse(new Key(KeyCode.D));
        lsbXMax = lsbXMin;
        lsbYMin = new KeyOrMouse(new Key(KeyCode.W));
        lsbYMax = lsbYMin;
        lsbXBMin = new KeyOrMouse(new Key(KeyCode.A));
        lsbXBMax = lsbXBMin;
        lsbYBMin = new KeyOrMouse(new Key(KeyCode.S));
        lsbYBMax = lsbYBMin;

        rsbXMin = new KeyOrMouse(new MouseMove(500, 0));
        rsbXMax = new KeyOrMouse(new MouseMove(1500, 0));
        rsbYMin = new KeyOrMouse(new MouseMove(0, -400));
        rsbYMax = new KeyOrMouse(new MouseMove(0, -1200));
        rsbXBMin = new KeyOrMouse(new MouseMove(-500, 0));
        rsbXBMax = new KeyOrMouse(new MouseMove(-1500, 0));
        rsbYBMin = new KeyOrMouse(new MouseMove(0, 400));
        rsbYBMax = new KeyOrMouse(new MouseMove(0, 1200));

        // customize
        back = new KeyOrMouse(new Key(KeyCode.F));
        lb = new KeyOrMouse(new Key(KeyCode.CONTROL));
    }
}
