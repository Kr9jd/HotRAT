package me.server.receive;

import javax.swing.*;
import java.io.DataInputStream;
import java.net.Socket;

public class GetScreen implements Runnable{
    Socket socket;
    JLabel label;
    JDialog dialog;
    public volatile boolean run = false;

    public GetScreen(Socket socket, JLabel label, JDialog dialog) {
        this.socket = socket;
        this.dialog = dialog;
        this.label = label;
    }

    public void run() {
        while(run) {
            try {
                DataInputStream dataInputStream = new DataInputStream(this.socket.getInputStream());
                if (dataInputStream.readInt() == 400){
                    byte[] b = new byte[dataInputStream.readInt()];
                    dataInputStream.readFully(b);
                    label.setIcon(new ImageIcon(b));
                }
            } catch (Exception var3) {
            }
        }
    }
}
