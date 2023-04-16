package me.client.utils;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;

public class ShutdownChecker extends Thread{
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
                int b = User32.INSTANCE.GetSystemMetrics(WinUser.SM_SHUTTINGDOWN);
                if(b == 1) {
                    LoadDLL.instance.RemoveProcessIsCritical();
                }
            } catch (InterruptedException e) {
            }
        }
    }
}
