package me.client.send;

import me.client.Client;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;

public class SendScreen extends Thread{
   public Socket socket;
   public volatile boolean run = false;
    public SendScreen(Socket socket) {
        this.socket = socket;
    }
    private byte[] getImage(Rectangle rectangle){
        Robot robot;
        try {
            robot = new Robot();
            BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
            byte[] bytes;
            Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = (ImageWriter) iter.next();
            ImageWriteParam iwp = writer.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(0.08f);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            IIOImage iIamge = new IIOImage(bufferedImage, null, null);
            writer.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));
            writer.write(null, iIamge, iwp);
            bytes = byteArrayOutputStream.toByteArray();
            return bytes;
        } catch (Exception e) {
        }
        return null;
    }
    @Override
    public void run() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        try {
            Rectangle rectangle = new Rectangle(dimension);
            SendMessage.SendHead(MessageFlags.SHOW_SCREEN,socket);
            while (run) {
                Thread.sleep(300);
                byte[] bytes2 = getImage(rectangle);
                SendMessage.Send(MessageFlags.UPDATE_SCREEN,bytes2,socket);
            }
        }catch (Exception e) {
        }
    }
    public void KeyPress(String s) {
        try {
                Robot robot = new Robot();
                robot.keyPress(Integer.parseInt(s));
        }catch (Exception e1) {
        }
    }
    public void KeyReleased(String s) {
        try {
            Robot robot = new Robot();
            robot.keyRelease(Integer.parseInt(s));
        }catch (Exception e1) {
        }
    }
    public void MousePress(String s) {
        try {
            Robot robot = new Robot();
            robot.mousePress(getMouseClick(Integer.parseInt(s)));
        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public void MouseReleased(String s) {
        try {
            Robot robot = new Robot();
            robot.mouseRelease(getMouseClick(Integer.parseInt(s)));
        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public void MouseMove(String s) {
        try {
            Robot robot = new Robot();
            int x = Integer.parseInt(s.substring(2,s.indexOf("Y")));
            int y = Integer.parseInt(s.substring(s.indexOf("Y:") + 2));
            robot.mouseMove(x,y);
        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    private int getMouseClick(int button) {
        if (button == MouseEvent.BUTTON1)
            return InputEvent.BUTTON1_MASK;
        if (button == MouseEvent.BUTTON3)
            return InputEvent.BUTTON3_MASK;
        return -100;
    }
    public void MouseWheel(String s) {
        try {
            Robot robot = new Robot();
            robot.mouseWheel(Integer.parseInt(s));
        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public void MouseDragged(String s) {
        try {
            Robot robot = new Robot();
            robot.mousePress(Integer.parseInt(s));
        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}