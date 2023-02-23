package net.cassite.xboxrelay.uimain;

import io.vproxy.vfx.control.globalscreen.GlobalScreenUtils;
import io.vproxy.vfx.util.Logger;
import javafx.application.Application;
import net.cassite.xboxrelay.base._Logger;
import net.cassite.xboxrelay.ui.VFXLoggerAdaptor;

public class Main {
    public static void main(String[] args) {
        _Logger.setLoggerAdaptor(new VFXLoggerAdaptor());

        var dllPath = "/net/cassite/xboxrelay/uimain/dll/JNativeHook_x64.dll";
        var dllStream = Main.class.getResourceAsStream(dllPath);
        if (dllStream == null) {
            Logger.error(dllPath + " not found, program might not work");
        } else {
            GlobalScreenUtils.releaseJNativeHookNativeToTmpDir("dll", dllStream);
        }

        Application.launch(FXMain.class);
    }
}
