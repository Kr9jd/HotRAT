package me.server.receive;

import me.server.Server;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetQQNumber implements Runnable{
    InputStreamReader reader;
    public boolean run = false;
    DefaultTableModel defaultListModel;
    public GetQQNumber(InputStreamReader reader, DefaultTableModel defaultListModel) {
        this.reader = reader;
        this.defaultListModel = defaultListModel;
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
                    String[] strings = s.split("\n");
                    for(String s1: strings) {
                        Object[] s2 = {s1};
                        defaultListModel.addRow(s2);
                    }
                    break;
                }
            }
        }catch (Exception e) {
        }
    }
}