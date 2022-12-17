package me.server.receive;

import me.server.Server;
import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

public class GetCamera {
    Socket socket;
    JLabel label;
    public GetCamera(Socket socket,String IP) throws Exception{
        JDialog frame = new JDialog();
        frame.setTitle("\\\\" + IP + "-" + "视频监控");
        InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("me/resources/cam.png");
        Image image = ImageIO.read(inputStream);
        frame.setIconImage(image);
        JPanel jPanel = new JPanel();
        frame.add(jPanel,BorderLayout.NORTH);
        label = new JLabel();
        JScrollPane scrollPane = new JScrollPane(label);
        frame.add(scrollPane,BorderLayout.CENTER);
        frame.setSize(100,100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SendMessage.SendHead(MessageFlags.STOP_CAMERA,socket);
            }
        });
    }

    public void update(byte[] bytes) {
        label.setIcon(new ImageIcon(bytes));
    }
}
