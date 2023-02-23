package net.cassite.xboxrelay.uimain;

import javafx.application.Application;
import net.cassite.xboxrelay.base._Logger;
import net.cassite.xboxrelay.ui.VFXLoggerAdaptor;

public class Main {
    public static void main(String[] args) {
        _Logger.setLoggerAdaptor(new VFXLoggerAdaptor());
        Application.launch(FXMain.class);
    }
}
