package me.server.receive;

import javax.swing.*;
import java.io.InputStreamReader;

public class Get implements Runnable{
    InputStreamReader reader;
    JTextArea jta;
    public boolean run = false;
    public Get(InputStreamReader reader,JTextArea jta) {
        this.reader = reader;
        this.jta = jta;
    }

    @Override
    public void run() {
        try {
            String strs = "";
            char[] chars = new char[1024];
            int len  =0;
            while (run) {
                len = reader.read(chars);
                String str = new String(chars,0,len);
                strs += str;
                if(str.indexOf("end")!=-1) {
                    String s = strs.replace("end","");
                    jta.append(s);
                    break;
                }
            }
        }catch (Exception e) {
        }
    }
}