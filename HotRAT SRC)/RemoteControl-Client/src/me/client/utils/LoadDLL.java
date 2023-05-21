package me.client.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WinDef;
import me.client.Client;

public interface LoadDLL extends Library {
    LoadDLL instance = (LoadDLL) Native.loadLibrary("me/client/dll/CppUtils.dll", LoadDLL.class);
    WString $Get$Key();
    int $Get$Width();
    int $Get$Height();
    int GetHWND(WinDef.HWND hwnd);
}