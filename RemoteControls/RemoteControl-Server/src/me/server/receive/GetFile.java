package me.server.receive;

import javax.swing.table.DefaultTableModel;
import java.io.InputStreamReader;

public class GetFile implements Runnable{
    InputStreamReader reader;
    public boolean run = false;
    DefaultTableModel defaultListModel;
    public GetFile(InputStreamReader reader, DefaultTableModel defaultListModel) {
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
                        String[] s2 = s1.split("\\|");
                        defaultListModel.addRow(s2);
                    }
                    break;
                }
            }
        }catch (Exception e) {
        }
    }
}
