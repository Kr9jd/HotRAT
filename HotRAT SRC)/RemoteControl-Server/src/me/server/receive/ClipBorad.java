package me.server.receive;

import com.sun.jna.platform.WindowUtils;
import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class ClipBorad {
    Socket socket;
    JTextField textField;
    public ClipBorad(Socket socket,String IP) {
        this.socket = socket;
        JDialog dialog = new JDialog();
        dialog.setTitle("\\\\" + IP + "-" + "剪切板");
        textField = new JTextField(20);
        JTextField textField1 = new JTextField(20);
        textField.setEditable(false);
        JButton button1 = new JButton("发送");
        JButton button = new JButton("获取");
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        bottom.add(button1);
        bottom.add(textField1);
        top.add(button);
        top.add(textField);
        dialog.add(top, BorderLayout.NORTH);
        dialog.add(bottom,BorderLayout.SOUTH);
        dialog.setVisible(true);
        dialog.setSize(400,150);
        dialog.setLocationRelativeTo(null);
        button.addActionListener(a->{
            textField.setText("");
            SendMessage.SendHead(MessageFlags.GET_CLIPBORAD,socket);
        });
        button1.addActionListener(a->{
            String str = textField1.getText();
            SendMessage.Send(MessageFlags.CHANGE_CLIPBORAD,str.getBytes(),socket);
        });
    }
    public void update(byte[] bytes) {
        textField.setText(new String(bytes));
    }
}