package me.server.utils;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

public interface LoadDLL extends Library {
    LoadDLL instance = (LoadDLL) Native.loadLibrary("me/server/dll/CppUtils.dll", LoadDLL.class);
    WString $Get$Key();
}