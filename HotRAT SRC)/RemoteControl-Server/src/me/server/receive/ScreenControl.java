package me.server.receive;

import me.server.Server;
import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ScreenControl {
    Socket socket;
    JLabel label;
    JTextField textField;
    JTextField textField1;
    public ScreenControl(Socket socket, String IP) throws Exception {
        this.socket = socket;
        KeyListener keyListener = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
                try {
                    KeySendEvent(MessageFlags.KEY_RELEASED,e);
                } catch (UnsupportedEncodingException ex) {
                }
            }
            public void keyPressed(KeyEvent e) {
                try {
                    KeySendEvent(MessageFlags.KEY_PRESSED,e);
                } catch (UnsupportedEncodingException ex) {
                }
            }
        };
        MouseWheelListener mouseWheelListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                try {
                    MouseWheelSendEvent(MessageFlags.MOUSE_WHEEL,e);
                } catch (UnsupportedEncodingException ex) {
                }
            }
        };
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                try {
                    MouseSendEvent(MessageFlags.MOUSE_DRAGGED,e);
                } catch (UnsupportedEncodingException ex) {
                }
                try {
                    MouseMoveSendEvent(MessageFlags.MOUSE_MOVED,e);
                } catch (UnsupportedEncodingException ex) {
                }
            }
            public void mouseMoved(MouseEvent e) {
                try {
                    MouseMoveSendEvent(MessageFlags.MOUSE_MOVED,e);
                } catch (UnsupportedEncodingException ex) {
                }
            }
        };
        MouseListener mouseListener = new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                try {
                    MouseSendEvent(MessageFlags.MOUSE_RELEASED,e);
                } catch (UnsupportedEncodingException ex) {
                }
            }
            public void mousePressed(MouseEvent e) {
                try {
                    MouseSendEvent(MessageFlags.MOUSE_PRESSED,e);
                } catch (UnsupportedEncodingException ex) {
                }
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseClicked(MouseEvent e) {
            }
        };
        JDialog frame = new JDialog();
        JPanel panel = new JPanel();
        JCheckBox checkBox = new JCheckBox("开启屏幕控制");
        JLabel label1 = new JLabel("附加X轴");
        JLabel label2 = new JLabel("附加Y轴");
        textField = new JTextField(5);
        textField1 = new JTextField(5);
        textField.setText("0");
        textField1.setText("0");
        panel.add(checkBox);
        panel.add(label1);
        panel.add(textField);
        panel.add(label2);
        panel.add(textField1);
        frame.setTitle("\\\\" + IP + "-" + "屏幕控制");
        InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("me/resources/screen.png");
        Image image = ImageIO.read(inputStream);
        frame.setIconImage(image);
        frame.add(panel,BorderLayout.NORTH);
        label = new JLabel();
        JScrollPane scrollPane = new JScrollPane(label);
        frame.add(scrollPane);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SendMessage.sendHead(MessageFlags.STOP_SCREEN, socket);
            }
        });
        checkBox.addActionListener(a->{
            if(checkBox.isSelected()) {
                checkBox.addKeyListener(keyListener);
                label.addMouseWheelListener(mouseWheelListener);
                label.addMouseMotionListener(mouseMotionListener);
                label.addMouseListener(mouseListener);
            }else {
                checkBox.removeKeyListener(keyListener);
                label.removeMouseListener(mouseListener);
                label.removeMouseMotionListener(mouseMotionListener);
                label.removeMouseWheelListener(mouseWheelListener);
            }
        });
    }
    public void update(byte[] bytes){
        label.setIcon(new ImageIcon(bytes));
    }
    public void KeySendEvent(byte Flag, KeyEvent event) throws UnsupportedEncodingException {
        SendMessage.send(Flag, (event.getKeyCode() + "").getBytes("GBK"), socket);
    }
    public void MouseSendEvent(byte Flag, MouseEvent event) throws UnsupportedEncodingException {
        SendMessage.send(Flag, (event.getButton() + "").getBytes("GBK"), socket);
    }
    public void MouseMoveSendEvent(byte Flag, MouseEvent event) throws UnsupportedEncodingException {
        SendMessage.send(Flag, ("X:" + (event.getX() + Integer.parseInt(textField.getText())) + "Y:" + (event.getY() + Integer.parseInt(textField.getText()))).getBytes("GBK"), socket);
    }
    public void MouseWheelSendEvent(byte Flag, MouseWheelEvent e) throws UnsupportedEncodingException {
        SendMessage.send(Flag,(e.getWheelRotation()+"").getBytes("GBK"), socket);
    }
}