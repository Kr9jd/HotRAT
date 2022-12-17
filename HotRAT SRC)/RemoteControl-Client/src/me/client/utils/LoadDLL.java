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
    void MessageErrorYNC(WString title,WString context);
    void MessageInformationYNC(WString title,WString context);
    void MessageWarningYNC(WString title,WString context);
    void MessageQuestionYNC(WString title,WString context);
    void MessageErrorYN(WString title,WString context);
    void MessageInformationYN(WString title,WString context);
    void MessageWarningYN(WString title,WString context);
    void MessageQuestionYN(WString title,WString context);
    void MessageErrorY(WString title,WString context);
    void MessageInformationY(WString title,WString context);
    void MessageWarningY(WString title,WString context);
    void MessageQuestionY(WString title,WString context);
    void beep(NativeLong d1,NativeLong d2);
    void killTasklist(NativeLong PID);
    void HideFile1(WString path);
    void StopWindowProcess(WinDef.HWND hwnd);
    void Lock();
    WString GetKey();
}