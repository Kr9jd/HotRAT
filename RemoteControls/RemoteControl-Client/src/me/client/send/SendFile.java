package me.client.send;

import java.io.*;
import java.net.Socket;

public class SendFile implements Runnable{
    Socket socket;
    String path;
    public SendFile(Socket socket,String path) throws IOException {
        this.socket = socket;
        this.path = path;
    }
    private byte[] fileToByte(File file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = fileInputStream.read(bytes))!=-1) {
            byteArrayOutputStream.write(bytes,0,len);
        }
        return byteArrayOutputStream.toByteArray();
    }
    @Override
    public void run() {
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            File file = new File(path);
            byte[] bytes = fileToByte(file);
            outputStream.writeInt(bytes.length);
            outputStream.write(bytes);
            outputStream.flush();
        }catch (Exception e) {
        }
    }
}
