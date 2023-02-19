package net.cassite.xboxrelay.ui.prebuilt;

import io.vproxy.vfx.entity.input.InputData;
import io.vproxy.vfx.entity.input.Key;
import io.vproxy.vfx.entity.input.KeyCode;
import javafx.scene.input.MouseButton;
import net.cassite.xboxrelay.ui.Action;
import net.cassite.xboxrelay.ui.Binding;
import net.cassite.xboxrelay.ui.MouseMove;
import net.cassite.xboxrelay.ui.MouseWheel;

public class DefaultBinding extends Binding {
    public DefaultBinding() {
        lsbYMin = new Action(new MouseWheel(-5));
        lsbYMax = new Action(new MouseWheel(-10));
        lsbYBMin = new Action(new MouseWheel(5));
        lsbYBMax = new Action(new MouseWheel(10));

        rsbXMin = new Action(new MouseMove(300, 0));
        rsbXMax = new Action(new MouseMove(1500, 0));
        rsbYMin = new Action(new MouseMove(0, -240));
        rsbYMax = new Action(new MouseMove(0, -1200));
        rsbXBMin = new Action(new MouseMove(-300, 0));
        rsbXBMax = new Action(new MouseMove(-1500, 0));
        rsbYBMin = new Action(new MouseMove(0, 240));
        rsbYBMax = new Action(new MouseMove(0, 1200));

        back = new Action(new Key(KeyCode.ESCAPE)).setFnInput(new InputData(false, true, false, new Key(KeyCode.F4)));
        start = new Action(new Key(KeyCode.F11)).setFnInput(new InputData(true, false, false, new Key(KeyCode.T)));

        du = new Action(new Key(KeyCode.UP));
        dd = new Action(new Key(KeyCode.DOWN));
        dl = new Action(new Key(KeyCode.LEFT));
        dr = new Action(new Key(KeyCode.RIGHT));

        a = new Action(new Key(MouseButton.SECONDARY));
        b = new Action(new Key(MouseButton.PRIMARY));
        x = new Action(new Key(KeyCode.SPACE)).setFnInput(new InputData(true, false, false, new Key(KeyCode.W)));
        y = new Action(new Key(KeyCode.ENTER));

        ltMin = new Action(new Key(KeyCode.ALT, true));
        ltMax = ltMin;
        rtMax = Action.newFn();

        lb = new Action(new Key(KeyCode.TAB));
        rb = new Action(new Key(KeyCode.DELETE)).setFnInput(new InputData(false, false, true, new Key(KeyCode.DELETE)));
    }
}
