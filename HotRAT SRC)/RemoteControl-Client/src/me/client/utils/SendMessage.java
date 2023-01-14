package me.client.utils;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SendMessage {
    public static byte[] ToByte(byte Head,int len,byte[] context) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        arrayOutputStream.write(Head);
        arrayOutputStream.write(IntToByte(len));
        arrayOutputStream.write(context);
        return arrayOutputStream.toByteArray();
    }
    public static void Send(byte Head,byte[] context,Socket socket){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            byte[] bytes = ZipUtils.compress(context);
            dataOutputStream.write(ToByte(Head,bytes.length,bytes));
        }catch (Exception e) {
        }
    }
    public static void SendHead(byte Head,Socket socket){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write(Head);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static byte[] receiveHead(DataInputStream dataInputStream) {
        byte[] bytes = new byte[1];
        try {
            dataInputStream.readFully(bytes);
        }catch (Exception e) {
        }
        return bytes;
    }
    public static int receiveLength(DataInputStream dataInputStream) {
        byte[] bytes = new byte[4];
        int len = 0;
        try {
            dataInputStream.readFully(bytes);
            len = ByteToInt(bytes);
        }catch (Exception e) {
        }
        return len;
    }
    public static byte[] receiveContext(DataInputStream dataInputStream,int len) {
        byte[] bytes = new byte[len];
        byte[] b = null;
        try {
            dataInputStream.readFully(bytes);
            b = ZipUtils.decompression(bytes);
        }catch (Exception e) {
        }
        return b;
    }
    public static byte[] IntToByte(int i) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.putInt(i);
        byteBuffer.flip();
        return byteBuffer.array();
    }
    public static int ByteToInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return buffer.getInt();
    }
}
