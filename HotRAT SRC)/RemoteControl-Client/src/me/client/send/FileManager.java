package me.client.send;

import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import java.awt.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileManager {
    public String fileName = "";
    Socket socket;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
    Lock lock = new ReentrantLock();
    public FileManager(Socket socket) {
        this.socket = socket;
    }

    public void DiskQuery() throws UnsupportedEncodingException {
        File[] files = File.listRoots();
        for (File file : files) {
            String info = "Disk" + "|" + file.getPath()+ "|" + " " + "|" + " ";
            SendMessage.send(MessageFlags.FILE_QUERY, info.getBytes("GBK"), socket);
        }
    }

    public void DeleteFile(String filename) {
        File file = new File(filename);
        if(!file.delete()) {
            SendMessage.sendHead(MessageFlags.FILE_DELETE_ERROR,socket);
        }
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

    public void FileQuery(String path) throws UnsupportedEncodingException {
        File file = new File(path);
        if (file.listFiles() != null) {
            for (File file1 : file.listFiles()) {
                if (file1.isDirectory()) {
                    Date date = new Date(file1.lastModified());
                    String info = "Directory" + "|" + file1.getName() + "|" + dateFormat.format(date) + "|" + " ";
                    SendMessage.send(MessageFlags.FILE_QUERY, info.getBytes("GBK"), socket);
                } else {
                    Date date = new Date(file1.lastModified());
                    String info = "File" + "|" + file1.getName() + "|" + dateFormat.format(date) + "|" + getFileSizeCompany(file1.length());
                    SendMessage.send(MessageFlags.FILE_QUERY, info.getBytes("GBK"), socket);
                }
            }
        }
    }
}