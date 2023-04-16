package me.server.receive;

import com.sun.jna.platform.WindowUtils;
import me.server.Server;
import me.server.utils.HttpUtils;
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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RemoteChat extends Thread{
    Socket socket;
    JTextArea jta;
    InputStream icon = RemoteChat.class.getClassLoader().getResourceAsStream("me/resources/4.png");
    Image icons = ImageIO.read(icon);
    JCheckBox checkBox;
    public RemoteChat(Socket socket,String IP) throws IOException {
        this.socket = socket;
        JDialog jDialog = new JDialog();
        checkBox = new JCheckBox("自动聊天");
        jDialog.setIconImage(icons);
        jDialog.setTitle("\\\\" + IP + "-" + "远程聊天");
        jta=new JTextArea();
        jta.setEditable(false);
        jta.setBackground(Color.BLACK);
        jta.setForeground(Color.green);
        JScrollPane jsp=new JScrollPane(jta);
        JPanel jp=new JPanel();
        JTextField jtf=new JTextField(20);
        JButton jb=new JButton("发送");
        jb.setBackground(new Color(0, 250, 0));
        jp.add(checkBox);
        jp.add(jtf);
        jp.add(jb);
        Font font = new Font(Font.SERIF,Font.PLAIN,13);
        jta.setFont(font);
        jtf.setFont(font);
        jDialog.add(jsp,BorderLayout.CENTER);
        jDialog.add(jp,BorderLayout.SOUTH);
        jDialog.setSize(500,300);
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
        jb.addActionListener(a->{
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd  hh:mm:ss");
                Date date = new Date();
                dateFormat.format(date);
                jta.append(date + "\n");
                String chat = date + "\n"+ "Hacker: " + jtf.getText() + "\n";
                jta.append("我: " + jtf.getText() + "\n");
                jtf.setText("");
                SendMessage.Send(MessageFlags.SEND_REMOTECHAT,chat.getBytes(),socket);
            }catch (Exception e) {
            }
        });
        jDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SendMessage.SendHead(MessageFlags.STOP_REMOTECHAT,socket);
            }
        });
    }
    public void update(byte[] bytes) {
        try {
            jta.append(new String(bytes));
            if(checkBox.isSelected()) {
                String str = new String(bytes);
                String httpget = HttpUtils.get(new URL("https://api.ownthink.com/bot?spoken=" + str.substring(str.indexOf("肉鸡:") + 4).trim()));
                String context = httpget.substring(httpget.indexOf("text") + 8,httpget.indexOf("}") - 2);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd  hh:mm:ss");
                Date date = new Date();
                dateFormat.format(date);
                String text = date + "\n"+ "Hacker: " + context + "\n";
                SendMessage.Send(MessageFlags.SEND_REMOTECHAT,text.getBytes(),socket);
                jta.append(text);
            }
        }catch (Exception e) {
        }
    }
}
