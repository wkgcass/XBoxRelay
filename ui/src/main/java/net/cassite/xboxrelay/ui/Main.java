package net.cassite.xboxrelay.ui;

import javafx.application.Application;
import net.cassite.xboxrelay.base._Logger;

public class Main {
    public static void main(String[] args) {
        _Logger.setLoggerAdaptor(new VFXLoggerAdaptor());
        Application.launch(FXMain.class);
    }
}
