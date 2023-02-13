package net.cassite.xboxrelay.ui;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

public class JNAMouseEvent {
    private JNAMouseEvent() {
    }

    public interface User32 extends StdCallLibrary {
        long MOUSEEVENTF_MOVE = 0x0001L;

        User32 INSTANCE = Native.load("user32", User32.class);

        void mouse_event(WinDef.DWORD dwFlags, WinDef.DWORD dx, WinDef.DWORD dy, WinDef.DWORD dwData, BaseTSD.ULONG_PTR dwExtraInfo);
    }
}
