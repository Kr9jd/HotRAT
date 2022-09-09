package me.client.send;

import me.client.Client;

import java.io.*;
import java.net.Socket;

public class SendFileContent extends Thread{
    Socket socket;
    OutputStreamWriter writer;
    String path;
    public SendFileContent(Socket socket, OutputStreamWriter writer,String path) {
        this.socket = socket;
        this.writer = writer;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String str = "";
            while ((str = bufferedReader.readLine())!=null) {
                writer.write(str + "\n");
                writer.flush();
            }
            writer.write("end");
            writer.flush();
        }catch (Exception E) {
        }
    }
}
