package me.server.receive;

import me.server.Server;
import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.net.Socket;

public class GetScreen {
    Socket socket;
    JLabel label;

    public GetScreen(Socket socket, String IP) throws Exception {
        this.socket = socket;
        KeyListener keyListener = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
                KeySendEvent(MessageFlags.KEY_RELEASED,e);
            }
            public void keyPressed(KeyEvent e) {
                KeySendEvent(MessageFlags.KEY_PRESSED,e);
            }
        };
        MouseWheelListener mouseWheelListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                MouseWheelSendEvent(MessageFlags.MOUSE_WHEEL,e);
            }
        };
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                MouseSendEvent(MessageFlags.MOUSE_DRAGGED,e);
                MouseMoveSendEvent(MessageFlags.MOUSE_MOVED,e);
            }
            public void mouseMoved(MouseEvent e) {
                MouseMoveSendEvent(MessageFlags.MOUSE_MOVED,e);
            }
        };
        MouseListener mouseListener = new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                MouseSendEvent(MessageFlags.MOUSE_RELEASED,e);
            }
            public void mousePressed(MouseEvent e) {
                MouseSendEvent(MessageFlags.MOUSE_PRESSED,e);
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseClicked(MouseEvent e) {
            }
        };
        JDialog frame = new JDialog();
        frame.setTitle("\\\\" + IP + "-" + "屏幕控制");
        InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("me/resources/screen.png");
        Image image = ImageIO.read(inputStream);
        frame.setIconImage(image);
        label = new JLabel();
        JScrollPane scrollPane = new JScrollPane(label);
        frame.addKeyListener(keyListener);
        label.addMouseWheelListener(mouseWheelListener);
        label.addMouseMotionListener(mouseMotionListener);
        label.addMouseListener(mouseListener);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SendMessage.SendHead(MessageFlags.STOP_SCREEN, socket);
            }
        });
    }
    public void update(byte[] bytes){
        label.setIcon(new ImageIcon(bytes));
    }
    public void KeySendEvent(byte Flag, KeyEvent event){
        SendMessage.Send(Flag, (event.getKeyCode() + "").getBytes(), socket);
    }
    public void MouseSendEvent(byte Flag, MouseEvent event){
        SendMessage.Send(Flag, (event.getButton() + "").getBytes(), socket);
    }
    public void MouseMoveSendEvent(byte Flag, MouseEvent event){
        SendMessage.Send(Flag, ("X:" + event.getX() + "Y:" + event.getY()).getBytes(), socket);
    }
    public void MouseWheelSendEvent(byte Flag, MouseWheelEvent e){
        SendMessage.Send(Flag,(e.getWheelRotation()+"").getBytes(), socket);
    }
}