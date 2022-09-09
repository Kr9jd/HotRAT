package me.server.receive;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class GetFiles implements Runnable{
    Socket socket;
    String filename;
    FileOutputStream fileOutputStream;
    public GetFiles(Socket socket,String filename) {
        this.socket= socket;
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            int len2 = inputStream.readInt();
            byte[] bytes = new byte[len2];
            File file = new File("C:\\Users\\13823\\Desktop\\Test\\远控\\" + filename);
            fileOutputStream = new FileOutputStream(file);
                inputStream.readFully(bytes);
                fileOutputStream.write(bytes);
        }catch (Exception e) {
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
            }
        }
    }
}
