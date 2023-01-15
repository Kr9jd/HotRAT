package me.server.utils;

import javax.swing.*;
import java.awt.*;

public class MessageWindow {
    public static void myDialog(String message) {
        JDialog dialog = new JDialog();
        JPanel panel1 = new JPanel();
        JTextArea textArea = new JTextArea();
        JButton button = new JButton("我同意条款");
        JButton button1 = new JButton("我拒绝条款");
        JScrollPane jScrollPane = new JScrollPane(textArea);
        panel1.add(button);
        panel1.add(button1);
        textArea.setText(message);
        textArea.setEditable(false);
        textArea.setForeground(Color.green);
        textArea.setBackground(Color.black);
        dialog.add(jScrollPane);
        dialog.add(panel1,BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(null);
        dialog.setSize(500,300);
        dialog.setTitle("公告");
        dialog.setVisible(true);
        button.addActionListener(a->{
            dialog.dispose();
        });
        button1.addActionListener(a->{
            System.exit(0);
        });
    }
}