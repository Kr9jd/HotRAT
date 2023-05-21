package me.client.utils;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

public class ShutdownChecker extends Thread{
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
                int b = User32.INSTANCE.GetSystemMetrics(WinUser.SM_SHUTTINGDOWN);
                if(b == 1) {
                    LoadNtdll.instance.RtlSetProcessIsCritical(new WinDef.BOOL(false),null,new WinDef.BOOL(false));
                }
            } catch (InterruptedException e) {
            }
        }
    }
}
