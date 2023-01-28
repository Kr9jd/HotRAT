package me.client.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinDef;

public interface LoadKernel32 extends Library {
    LoadKernel32 instance = (LoadKernel32) Native.loadLibrary("Kernel32.dll", LoadKernel32.class);
    WinDef.BOOL Beep(WinDef.DWORD d1, WinDef.DWORD d2);
    int GetWindowsDirectoryW(char[] chars,int size);
}