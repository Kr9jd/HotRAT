package me.server.receive;


import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class MessageBox {
    public MessageBox(Socket socket) {
        JDialog dialog = new JDialog();
        JComboBox<String> comboBox = new JComboBox<>();
        JComboBox<String> comboBox1 = new JComboBox<>();
        JTextArea textArea = new JTextArea();
        JTextField textField = new JTextField(20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JButton button = new JButton("发送");
        JLabel label = new JLabel("标题");
        JLabel label1 = new JLabel("正文");
        JLabel label2 = new JLabel("图标");
        JLabel label3 = new JLabel("按钮");
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel1 = new JPanel();
        comboBox.addItem("错误");
        comboBox.addItem("提示");
        comboBox.addItem("警告");
        comboBox.addItem("问题");
        comboBox1.addItem("是|否|取消");
        comboBox1.addItem("是|否");
        comboBox1.addItem("是");
        panel.add(label2);
        panel.add(comboBox);
        panel.add(label3);
        panel.add(comboBox1);
        panel1.add(label1);
        panel2.add(label);
        panel2.add(textField);
        panel2.add(button);
        dialog.setResizable(false);
        dialog.add(panel1, BorderLayout.WEST);
        dialog.add(scrollPane,BorderLayout.CENTER);
        dialog.add(panel,BorderLayout.NORTH);
        dialog.add(panel2,BorderLayout.SOUTH);
        dialog.setTitle("远程弹窗");
        dialog.setSize(500,300);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        button.addActionListener(a->{
            String context = "Title:" + textField.getText() + "Context:" + textArea.getText();
            switch (comboBox.getSelectedIndex()) {
                case 0:
                    switch (comboBox1.getSelectedIndex()) {
                        case 0:
                            try {
                                SendMessage.send(MessageFlags.MESSAGEBOX_ERROR_YNC, context.getBytes("GBK"), socket);
                            } catch (UnsupportedEncodingException e) {
                            }
                            break;
                        case 1:
                            try {
                                SendMessage.send(MessageFlags.MESSAGEBOX_ERROR_YN, context.getBytes("GBK"), socket);
                            } catch (UnsupportedEncodingException e) {
                            }
                            break;
                        case 2:
                            try {
                                SendMessage.send(MessageFlags.MESSAGEBOX_ERROR_Y, context.getBytes("GBK"), socket);
                            } catch (UnsupportedEncodingException e) {
                            }
                            break;
                    }
                        break;
                        case 1:
                            switch (comboBox1.getSelectedIndex()) {
                                case 0:
                                    try {
                                        SendMessage.send(MessageFlags.MESSAGEBOX_INFORMATION_YNC, context.getBytes("GBK"), socket);
                                    } catch (UnsupportedEncodingException e) {
                                    }
                                    break;
                                case 1:
                                    try {
                                        SendMessage.send(MessageFlags.MESSAGEBOX_INFORMATION_YN, context.getBytes("GBK"), socket);
                                    } catch (UnsupportedEncodingException e) {
                                    }
                                    break;
                                case 2:
                                    try {
                                        SendMessage.send(MessageFlags.MESSAGEBOX_INFORMATION_Y, context.getBytes("GBK"), socket);
                                    } catch (UnsupportedEncodingException e) {
                                    }
                                    break;
                            }
                            break;
                case 2:
                    switch (comboBox1.getSelectedIndex()) {
                        case 0:
                            try {
                                SendMessage.send(MessageFlags.MESSAGEBOX_WARNING_YNC, context.getBytes("GBK"), socket);
                            } catch (UnsupportedEncodingException e) {
                            }
                            break;
                        case 1:
                            try {
                                SendMessage.send(MessageFlags.MESSAGEBOX_WARNING_YN, context.getBytes("GBK"), socket);
                            } catch (UnsupportedEncodingException e) {
                            }
                            break;
                        case 2:
                            try {
                                SendMessage.send(MessageFlags.MESSAGEBOX_WARNING_Y, context.getBytes("GBK"), socket);
                            } catch (UnsupportedEncodingException e) {
                            }
                            break;
                    }
                    break;
                case 3:
                    switch (comboBox1.getSelectedIndex()) {
                        case 0:
                            try {
                                SendMessage.send(MessageFlags.MESSAGEBOX_QUESTION_YNC, context.getBytes("GBK"), socket);
                            } catch (UnsupportedEncodingException e) {
                            }
                            break;
                        case 1:
                            try {
                                SendMessage.send(MessageFlags.MESSAGEBOX_QUESTION_YN, context.getBytes("GBK"), socket);
                            } catch (UnsupportedEncodingException e) {
                            }
                            break;
                        case 2:
                            try {
                                SendMessage.send(MessageFlags.MESSAGEBOX_QUESTION_Y, context.getBytes("GBK"), socket);
                            } catch (UnsupportedEncodingException e) {
                            }
                            break;
                    }
                    break;
            }
        });
    }
}
