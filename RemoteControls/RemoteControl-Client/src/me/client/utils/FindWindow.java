package me.client.utils;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import me.client.Client;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class FindWindow{
    String str;
    OutputStreamWriter writer;
    Socket socket;
    public FindWindow(String str,OutputStreamWriter writer,Socket socket) {
        this.str = str;
        this.writer = writer;
        this.socket = socket;
    }
    public void hide() {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null,str);
        if(hwnd != null) {
            try {
                User32.INSTANCE.CloseWindow(hwnd);
            }catch (Exception e) {
            }
        }
    }
    public void show() {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null,str);
        if(hwnd != null) {
                User32.INSTANCE.ShowWindow(hwnd,WinUser.SW_RESTORE);
        }
        }
    }
