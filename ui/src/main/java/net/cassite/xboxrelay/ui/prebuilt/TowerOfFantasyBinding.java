package net.cassite.xboxrelay.ui.prebuilt;

import io.vproxy.vfx.entity.input.InputData;
import io.vproxy.vfx.entity.input.Key;
import io.vproxy.vfx.entity.input.KeyCode;
import javafx.scene.input.MouseButton;
import net.cassite.xboxrelay.ui.Binding;
import net.cassite.xboxrelay.ui.Action;
import net.cassite.xboxrelay.ui.MouseMove;

public class TowerOfFantasyBinding extends Binding {
    public TowerOfFantasyBinding() {
        b = new Action(new Key(MouseButton.PRIMARY)).setFnInput(new InputData(false, false, false, new Key(KeyCode.F)));
        y = new Action(new Key(KeyCode.KEY_1)).setFnInput(new InputData(false, false, false, new Key(KeyCode.X)));
        a = new Action(new Key(KeyCode.SPACE)).setFnInput(new InputData(false, false, false, new Key(KeyCode.CONTROL, true)));
        x = new Action(new Key(MouseButton.SECONDARY));
        rtMin = new Action(new Key(KeyCode.KEY_2));
        rtMax = rtMin;
        rb = new Action(new Key(KeyCode.KEY_3));
        du = new Action(new Key(KeyCode.G)).setFnInput(new InputData(false, true, false, new Key(KeyCode.KEY_1)));
        dl = new Action(new Key(KeyCode.E)).setFnInput(new InputData(false, true, false, new Key(KeyCode.KEY_2)));
        dr = new Action(new Key(KeyCode.Q)).setFnInput(new InputData(false, true, false, new Key(KeyCode.KEY_3)));
        dd = new Action(new Key(KeyCode.R)).setFnInput(new InputData(false, true, false, new Key(KeyCode.KEY_4)));
        tl = new Action(new Key(KeyCode.V)).setFnInput(new InputData(false, false, false, new Key(KeyCode.M)));
        tr = new Action(new Key(KeyCode.F2));
        ltMin = new Action(new Key(MouseButton.PRIMARY));
        ltMax = ltMin;
        start = new Action(new Key(KeyCode.ESCAPE));

        lsbXMin = new Action(new Key(KeyCode.D));
        lsbXMax = lsbXMin;
        lsbYMin = new Action(new Key(KeyCode.W));
        lsbYMax = lsbYMin;
        lsbXBMin = new Action(new Key(KeyCode.A));
        lsbXBMax = lsbXBMin;
        lsbYBMin = new Action(new Key(KeyCode.S));
        lsbYBMax = lsbYBMin;

        rsbXMin = new Action(new MouseMove(500, 0));
        rsbXMax = new Action(new MouseMove(1500, 0));
        rsbYMin = new Action(new MouseMove(0, -400));
        rsbYMax = new Action(new MouseMove(0, -1200));
        rsbXBMin = new Action(new MouseMove(-500, 0));
        rsbXBMax = new Action(new MouseMove(-1500, 0));
        rsbYBMin = new Action(new MouseMove(0, 400));
        rsbYBMax = new Action(new MouseMove(0, 1200));

        // customize
        lb = Action.newFn();
        back = new Action(new Key(KeyCode.ALT, true));
    }
}
