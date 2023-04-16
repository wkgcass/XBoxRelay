package net.cassite.xboxrelay.uimain;

import io.vproxy.base.util.LogType;
import io.vproxy.base.util.Logger;
import io.vproxy.vfx.control.globalscreen.GlobalScreenUtils;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        var dllPath = "/net/cassite/xboxrelay/uimain/dll/JNativeHook_x64.dll";
        var dllStream = Main.class.getResourceAsStream(dllPath);
        if (dllStream == null) {
            Logger.warn(LogType.SYS_ERROR, dllPath + " not found, program might not work");
        } else {
            GlobalScreenUtils.releaseJNativeHookNativeToLibraryPath(dllStream);
        }

        Application.launch(FXMain.class);
    }
}
