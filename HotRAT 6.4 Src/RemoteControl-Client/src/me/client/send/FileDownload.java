package me.client.send;

import me.client.utils.MessageFlags;
import me.client.utils.ReceiveMessage;
import me.client.utils.SendMessage;

import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class FileDownload extends Thread{
    Socket socket;
    String file;
    public FileDownload(Socket socket,String file) {
        this.socket = socket;
        this.file = file;
    }

    public void FileDownLoad(String path) {
        File file = new File(path);
        String filename = file.getName();
        System.out.println(filename);
        SendMessage.Send(MessageFlags.FILE_CREATEWITHNAME,file.getName().getBytes(),socket);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            int filelen = 8*1024;
            byte[] bytes;
            if(file.length() < filelen) {
                bytes = new byte[(int)file.length()];
                fileInputStream.read(bytes);
                SendMessage.SendHead(MessageFlags.FILE_PREPARE,socket);
                SendMessage.Send(MessageFlags.FILE_DOWNLOAD,bytes,socket);
                SendMessage.Send(MessageFlags.FILE_DOWNLOAD_END,filename.getBytes(),socket);
            }else {
                SendMessage.SendHead(MessageFlags.FILE_PREPARE,socket);
                int len = (int) (file.length()/filelen);
                int len1 = (int) (file.length()%filelen);
                for(;len > 0;len--) {
                    bytes = new byte[filelen];
                    fileInputStream.read(bytes);
                    Thread.sleep(70);
                    SendMessage.Send(MessageFlags.FILE_DOWNLOAD,bytes,socket);
                }
                bytes = new byte[len1];
                fileInputStream.read(bytes);
                SendMessage.Send(MessageFlags.FILE_DOWNLOAD,bytes,socket);
                SendMessage.Send(MessageFlags.FILE_DOWNLOAD_END,filename.getBytes(),socket);
            }
        }catch (Exception e) {
        }
    }
    @Override
    public void run() {
        synchronized (ReceiveMessage.lock) {
            FileDownLoad(file);
        }
    }
}
