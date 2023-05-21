package me.client.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WinDef;

public interface LoadUser32 extends Library {
    LoadUser32 instance= (LoadUser32) Native.loadLibrary("User32.dll", LoadUser32.class);
    int MessageBoxW(WinDef.HWND hwnd, WString context,WString title,int i);
    WinDef.BOOL SetWindowTextW(WinDef.HWND hwnd,WString wString);
    WinDef.SHORT GetKeyState(int i);
}