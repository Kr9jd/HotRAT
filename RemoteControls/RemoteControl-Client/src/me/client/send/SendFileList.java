package me.client.send;

import me.client.Client;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendFileList extends Thread {
    OutputStreamWriter writer;
    String path;
    Socket socket;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");

    public SendFileList(OutputStreamWriter writer, String path, Socket socket) {
        this.writer = writer;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            File file = new File(path);
            if (file.listFiles() != null) {
                for (File file1 : file.listFiles()) {
                    if(file1.isDirectory()) {
                        Date date = new Date(file1.lastModified());
                        writer.write("(文件夹)" + FilenameUtils.getExtension(file1.toString()) + "|" + file1.getName() + "|"+ dateFormat.format(date) + "|null" + "\n");
                        writer.flush();
                    }else {
                        Date date = new Date(file1.lastModified());
                        writer.write("(文件)" + FilenameUtils.getExtension(file1.toString()) + "|" + file1.getName() + "|"+ dateFormat.format(date) + "|" + file1.length() + "kb" + "\n");
                        writer.flush();
                    }
                }
                writer.write("end");
                writer.flush();
            }else {
                writer.write("end");
                writer.flush();
            }
            }catch(Exception e){
        }
    }
    public void sendDiskInfo() {
        try {
            File[] files = File.listRoots();
            for(File file:files) {
             writer.write(file.getPath() + "\n");
             writer.flush();
            }
            writer.write("end");
            writer.flush();
        }catch (Exception e) {
        }
    }
}