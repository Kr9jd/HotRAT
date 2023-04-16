package me.server.loadconfig;

import jdk.nashorn.internal.scripts.JD;
import me.server.Server;

import javax.swing.*;
import java.awt.*;

public class SystemSettings extends JDialog {
    public SystemSettings() {
        setTitle("系统设置");
        JTextField textField = new JTextField(15);
        JTextField textField1 = new JTextField(15);
        JLabel label = new JLabel("监听端口");
        JLabel label1 = new JLabel("上线头");
        JButton button = new JButton("确定");
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        textField.setText(Server.port);
        textField1.setText(Server.head);
        panel.add(label);
        panel.add(textField);
        panel1.add(label1);
        panel1.add(textField1);
        panel2.add(button);
        add(panel, BorderLayout.NORTH);
        add(panel1,BorderLayout.CENTER);
        add(panel2,BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setSize(530,150);
        setVisible(true);
        button.addActionListener(a->{
            Server.configWriter.write(Server.CFGPATH,"lookandfeel",Server.lookandfeel);
            Server.configWriter.write(Server.CFGPATH,"port",textField.getText());
            Server.configWriter.write(Server.CFGPATH,"head",textField1.getText());
            Server.configWriter.write(Server.CFGPATH,"password","null:(");
            dispose();
            JOptionPane.showMessageDialog(null,"系统信息已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        });
    }
}