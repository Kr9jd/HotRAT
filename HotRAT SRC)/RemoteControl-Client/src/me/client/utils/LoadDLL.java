package me.client.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

public interface LoadDLL extends Library {
    LoadDLL instance = (LoadDLL) Native.loadLibrary("me/client/dll/CppUtils.dll", LoadDLL.class);
    WString GetKey();
    int GetWidth();
    int GetHeight();
    int GetWindowsID(WinDef.HWND hwnd);
    void EnableDebugPriv();
    void SetProcessIsCritical();
    void RemoveProcessIsCritical();
    int KeyState();
}