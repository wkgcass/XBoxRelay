package net.cassite.xboxrelay.ui.testing;

import io.vproxy.vfx.entity.input.Key;
import io.vproxy.vfx.entity.input.KeyCode;
import javafx.scene.input.MouseButton;
import net.cassite.xboxrelay.ui.Binding;
import net.cassite.xboxrelay.ui.Action;
import net.cassite.xboxrelay.ui.MouseMove;

public class TempTestBinding extends Binding {
    public TempTestBinding() {
        lsbXMin = new Action(new Key(KeyCode.D));
        lsbXMax = lsbXMin;
        lsbYMin = new Action(new Key(KeyCode.W));
        lsbYMax = lsbYMin;
        lsbXBMin = new Action(new Key(KeyCode.A));
        lsbXBMax = lsbXBMin;
        lsbYBMin = new Action(new Key(KeyCode.S));
        lsbYBMax = lsbYBMin;

        rsbXMin = new Action(new MouseMove(1000, 0));
        rsbXMax = new Action(new MouseMove(1500, 0));
        rsbYMin = new Action(new MouseMove(0, -800));
        rsbYMax = new Action(new MouseMove(0, -1200));
        rsbXBMin = new Action(new MouseMove(-1000, 0));
        rsbXBMax = new Action(new MouseMove(-1500, 0));
        rsbYBMin = new Action(new MouseMove(0, 800));
        rsbYBMax = new Action(new MouseMove(0, 1200));

        du = new Action(new Key(KeyCode.G));
        dd = new Action(new Key(KeyCode.KEY_1));
        dl = new Action(new Key(KeyCode.KEY_2));
        dr = new Action(new Key(KeyCode.KEY_3));
        back = new Action(new Key(KeyCode.F));
        guide = null;
        start = new Action(new Key(KeyCode.ESCAPE));
        tl = new Action(new Key(KeyCode.V));
        tr = new Action(new Key(KeyCode.F2));
        a = new Action(new Key(KeyCode.SPACE));
        b = new Action(new Key(MouseButton.PRIMARY));
        x = new Action(new Key(KeyCode.CONTROL, true));
        y = new Action(new Key(KeyCode.C));
        lb = new Action(new Key(KeyCode.Q));
        rb = new Action(new Key(KeyCode.E));
        ltMin = new Action(new Key(MouseButton.PRIMARY));
        ltMax = ltMin;
        rtMin = new Action(new Key(MouseButton.SECONDARY));
        rtMax = rtMin;
    }
}
