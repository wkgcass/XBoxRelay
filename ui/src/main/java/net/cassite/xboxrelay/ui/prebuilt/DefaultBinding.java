package net.cassite.xboxrelay.ui.prebuilt;

import io.vproxy.vfx.entity.input.Key;
import io.vproxy.vfx.entity.input.KeyCode;
import javafx.scene.input.MouseButton;
import net.cassite.xboxrelay.ui.Binding;
import net.cassite.xboxrelay.ui.KeyOrMouse;
import net.cassite.xboxrelay.ui.MouseMove;
import net.cassite.xboxrelay.ui.MouseWheel;

public class DefaultBinding extends Binding {
    public DefaultBinding() {
        lsbYMin = new KeyOrMouse(new MouseWheel(-5));
        lsbYMax = new KeyOrMouse(new MouseWheel(-10));
        lsbYBMin = new KeyOrMouse(new MouseWheel(5));
        lsbYBMax = new KeyOrMouse(new MouseWheel(10));

        rsbXMin = new KeyOrMouse(new MouseMove(300, 0));
        rsbXMax = new KeyOrMouse(new MouseMove(1500, 0));
        rsbYMin = new KeyOrMouse(new MouseMove(0, -240));
        rsbYMax = new KeyOrMouse(new MouseMove(0, -1200));
        rsbXBMin = new KeyOrMouse(new MouseMove(-300, 0));
        rsbXBMax = new KeyOrMouse(new MouseMove(-1500, 0));
        rsbYBMin = new KeyOrMouse(new MouseMove(0, 240));
        rsbYBMax = new KeyOrMouse(new MouseMove(0, 1200));

        back = new KeyOrMouse(new Key(KeyCode.ESCAPE));
        start = new KeyOrMouse(new Key(KeyCode.W));

        du = new KeyOrMouse(new Key(KeyCode.UP));
        dd = new KeyOrMouse(new Key(KeyCode.DOWN));
        dl = new KeyOrMouse(new Key(KeyCode.LEFT));
        dr = new KeyOrMouse(new Key(KeyCode.RIGHT));

        a = new KeyOrMouse(new Key(MouseButton.SECONDARY));
        b = new KeyOrMouse(new Key(MouseButton.PRIMARY));
        x = new KeyOrMouse(new Key(KeyCode.SPACE));
        y = new KeyOrMouse(new Key(KeyCode.ENTER));

        ltMin = new KeyOrMouse(new Key(KeyCode.ALT, true));
        ltMax = ltMin;
        rtMin = new KeyOrMouse(new Key(KeyCode.CONTROL, true));
        rtMax = rtMin;

        lb = new KeyOrMouse(new Key(KeyCode.TAB));
        rb = new KeyOrMouse(new Key(KeyCode.DELETE));
    }
}
