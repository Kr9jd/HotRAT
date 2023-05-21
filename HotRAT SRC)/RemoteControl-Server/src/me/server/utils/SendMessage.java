package me.server.utils;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SendMessage {
    public static byte[] toByte(byte Head, int len, byte[] context) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        arrayOutputStream.write(Head);
        arrayOutputStream.write(IntToByte(len));
        arrayOutputStream.write(context);
        return arrayOutputStream.toByteArray();
    }
    public static byte[] toByteWithToken(byte Head,int len,byte[] context,long Token) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        arrayOutputStream.write(Head);
        arrayOutputStream.write(IntToByte(len));
        arrayOutputStream.write(LongToByte(Token));
        arrayOutputStream.write(context);
        return arrayOutputStream.toByteArray();
    }

    public static void sendWithToken(byte Head,byte[] context,long Token,Socket socket) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            byte[] bytes = ZipUtils.compress(context);
            dataOutputStream.write(toByteWithToken(Head, bytes.length, bytes,Token));
        } catch (Exception var5) {
        }
    }
    public static void send(byte Head, byte[] context, Socket socket) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            byte[] bytes = ZipUtils.compress(context);
            dataOutputStream.write(toByte(Head, bytes.length, bytes));
        } catch (Exception var5) {
        }

    }
    public static void sendHead(byte Head, Socket socket){
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
    public static long receiveToken(DataInputStream dataInputStream) {
        byte[] bytes = new byte[8];
        long token = 0;
        try {
            dataInputStream.readFully(bytes);
            token = ByteToLong(bytes);
        }catch (Exception e) {
        }
        return token;
    }
    public static byte[] receiveContext(DataInputStream dataInputStream, int len) {
        byte[] bytes = new byte[len];
        byte[] b = null;
        try {
            dataInputStream.readFully(bytes);
            b = ZipUtils.decompression(bytes);
        } catch (Exception var5) {
        }
        return b;
    }
    public static byte[] IntToByte(int i) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.putInt(i);
        byteBuffer.flip();
        return byteBuffer.array();
    }
    public static byte[] LongToByte(long l) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        byteBuffer.putLong(l);
        byteBuffer.flip();
        return byteBuffer.array();
    }
    public static long ByteToLong(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        byteBuffer.put(bytes,0,bytes.length);
        byteBuffer.flip();
        return byteBuffer.getLong();
    }
    public static int ByteToInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return buffer.getInt();
    }
}
