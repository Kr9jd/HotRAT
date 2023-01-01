package me.client.send;

import me.client.Client;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RemoteChat {
    JDialog jWindow;
    JTextArea jta;
    Socket socket;
    public RemoteChat(Socket socket)throws Exception{
        this.socket = socket;
        SendMessage.SendHead(MessageFlags.SHOW_REMOTECHAT,socket);
        jWindow = new JDialog();
        jta = new JTextArea();
        jWindow.setAlwaysOnTop(true);
        jWindow.setTitle("聊天");
        jWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        InputStream inputStream = Client.class.getClassLoader().getResourceAsStream("me/client/resources/Chat.png");
        Image image = ImageIO.read(inputStream);
        jWindow.setIconImage(image);
        jta.setEditable(false);
        jta.setBackground(Color.BLACK);
        jta.setForeground(Color.green);
        JScrollPane jsp=new JScrollPane(jta);
        JPanel jp=new JPanel();
        JTextField jtf=new JTextField(15);
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
                String chat =date + "\n"+ "肉鸡: " + jtf.getText() + "\n";
                jta.append(date + "\n");
                jta.append("我: " + jtf.getText() + "\n");
                jtf.setText("");
                SendMessage.Send(MessageFlags.SEND_REMOTECHAT,chat.getBytes(),socket);
            }catch (Exception e) {
            }
        });
    }
    public void closeWindows() {
        jWindow.dispose();
    }
    public void update(byte[] bytes) throws UnsupportedEncodingException {
        String context = new String(bytes, StandardCharsets.UTF_8);
        jta.append(context + "\n");
        System.out.println(context);
    }
}