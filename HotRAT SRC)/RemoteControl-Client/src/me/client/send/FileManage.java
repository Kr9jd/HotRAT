package me.client.send;

import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;
import org.bridj.cpp.com.VARIANT;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManage extends Thread{
    public String fileName = "";
    Socket socket;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
    public FileManage(Socket socket) {
        this.socket = socket;
    }

    public void DiskQuery() {
        File[] files = File.listRoots();
        for (File file : files) {
            String info = "Disk" + "|" + file.getPath()+ "|" + " " + "|" + " ";
            SendMessage.Send(MessageFlags.FILE_QUERY, info.getBytes(), socket);
        }
    }

    public void DeleteFile(String filename) {
        File file = new File(filename);
        file.delete();
    }

    public void OpenFile(String filename) {
        try {
            File file = new File(filename);
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(file);
            }
        } catch (Exception e) {
        }
    }

    public void FileDownLoad(String path) {
        File file = new File(path);
        String filename = file.getName();
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
        synchronized (this) {
            FileDownLoad(fileName);
        }
    }

    private String getFileSizeCompany(Long size) {
        if (size < 1024) {
            return size + "B";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            return size + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            size = size * 100;
            return (size / 100) + "."
                    + (size % 100) + "MB";
        } else {
            size = size * 100 / 1024;
            return (size / 100) + "."
                    + (size % 100) + "GB";
        }
    }

    public void FileQuery(String path) {
        File file = new File(path);
        if (file.listFiles() != null) {
            for (File file1 : file.listFiles()) {
                if (file1.isDirectory()) {
                    Date date = new Date(file1.lastModified());
                    String info = "Directory" + "|" + file1.getName() + "|" + dateFormat.format(date) + "|" + " ";
                    SendMessage.Send(MessageFlags.FILE_QUERY, info.getBytes(), socket);
                } else {
                    Date date = new Date(file1.lastModified());
                    String info = "File" + "|" + file1.getName() + "|" + dateFormat.format(date) + "|" + getFileSizeCompany(file1.length());
                    SendMessage.Send(MessageFlags.FILE_QUERY, info.getBytes(), socket);
                }
            }
        }
    }
}