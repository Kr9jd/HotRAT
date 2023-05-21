package me.client.send;

import me.client.utils.MessageFlags;
import me.client.utils.ReceiveMessage;
import me.client.utils.SendMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class FileDownload extends Thread{
    Socket socket;
    String file;
    long Token;
    public FileDownload(Socket socket,String file,long Token) {
        this.socket = socket;
        this.file = file;
        this.Token = Token;
    }

    public void FileDownLoad(String path) throws UnsupportedEncodingException {
        File file = new File(path);
        String filename = file.getName();
        System.out.println(filename);
        SendMessage.send(MessageFlags.FILE_CREATEWITHNAME,file.getName().getBytes("GBK"),socket);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            int filelen = 8*1024;
            byte[] bytes;
            if(file.length() < filelen) {
                bytes = new byte[(int)file.length()];
                fileInputStream.read(bytes);
                SendMessage.sendWithToken(MessageFlags.FILE_PREPARE,new byte[]{1},Token,socket);
                SendMessage.sendWithToken(MessageFlags.FILE_DOWNLOAD,bytes,Token,socket);
                SendMessage.send(MessageFlags.FILE_DOWNLOAD_END,filename.getBytes("GBK"),socket);
            }else {
                SendMessage.sendWithToken(MessageFlags.FILE_PREPARE,new byte[]{1},Token,socket);
                int len = (int) (file.length()/filelen);
                int len1 = (int) (file.length()%filelen);
                for(;len > 0;len--) {
                    bytes = new byte[filelen];
                    fileInputStream.read(bytes);
                    Thread.sleep(70);
                    SendMessage.sendWithToken(MessageFlags.FILE_DOWNLOAD,bytes,Token,socket);
                }
                bytes = new byte[len1];
                fileInputStream.read(bytes);
                SendMessage.sendWithToken(MessageFlags.FILE_DOWNLOAD,bytes,Token,socket);
                SendMessage.send(MessageFlags.FILE_DOWNLOAD_END,filename.getBytes("GBK"),socket);
            }
        }catch (Exception e) {
        }
    }
    @Override
    public void run() {
        synchronized (ReceiveMessage.lock) {
            try {
                FileDownLoad(file);
            } catch (UnsupportedEncodingException e) {
            }
        }
    }
}
