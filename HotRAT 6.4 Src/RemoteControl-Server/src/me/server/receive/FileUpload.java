package me.server.receive;

import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class FileUpload extends Thread{
    Socket socket;
    String file;
    public FileUpload(Socket socket,String file) {
        this.socket = socket;
        this.file = file;
    }
    @Override
    public void run() {

    }
}
