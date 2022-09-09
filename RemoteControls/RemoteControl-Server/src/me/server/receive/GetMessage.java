package me.server.receive;

import javax.swing.*;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class GetMessage extends Thread{
    OutputStreamWriter writer;
    InputStreamReader reader;
    JTextArea jta;
    public boolean run = false;
    public GetMessage(InputStreamReader reader,JTextArea jta,OutputStreamWriter writer) {
        this.reader = reader;
        this.jta = jta;
        this.writer = writer;
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
                jta.append(str);
                if(str.indexOf("end")!=-1) {
                    break;
                }
            }
        }catch (Exception e) {
        }
    }

}

