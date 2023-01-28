package me.client.send;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

public class PictureDisplay {
    JWindow window;
    public void show(String url) throws MalformedURLException {
        window = new JWindow();
        window.setAlwaysOnTop(true);
        JButton but = new JButton();
        but.setIcon(new ImageIcon(new URL(url)));
        window.getContentPane().add(but);
        window.setLocationRelativeTo(null);
        window.setSize(450,450);
        window.setVisible(true);
    }
    public void close() {
        window.dispose();
    }
}
