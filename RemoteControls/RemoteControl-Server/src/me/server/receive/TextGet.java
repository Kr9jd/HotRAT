package me.server.receive;

import javax.swing.*;
import java.io.InputStreamReader;

public class TextGet implements Runnable{
    InputStreamReader reader;
    JTextField textField;
    public TextGet(InputStreamReader reader,JTextField textField) {
        this.reader = reader;
        this.textField = textField;
    }

    @Override
    public void run() {
        try {
            char[] chars = new char[1024];
            int len = 0;
            len = reader.read(chars);
            String str = new String(chars,0,len);
            textField.setText(str);
        }catch (Exception e) {
        }
    }
}
