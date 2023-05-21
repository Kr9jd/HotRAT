package me.server.receive;

import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class LANAccess {
    JTextArea area1;
    DefaultTableModel defaultTableModel;
    String string = "";
    public LANAccess(Socket socket,String IP) throws IOException {
        JDialog dialog = new JDialog();
        InputStream inputStream = LANAccess.class.getClassLoader().getResourceAsStream("me/resources/LAN.png");
        Image image = ImageIO.read(inputStream);
        dialog.setIconImage(image);
        JComboBox<String> comboBox = new JComboBox<>();
        JLabel label = new JLabel("请求模式");
        JLabel label1 = new JLabel("请求URL");
        JTextField textField3 = new JTextField(25);
        JButton button = new JButton("请求头/请求正文设置");
        JButton button5 = new JButton("发送请求");
        comboBox.addItem("GET");
        comboBox.addItem("POST");
        JPanel panel1 = new JPanel();
        area1 = new JTextArea();
        area1.append("消息正文将会返回到这里");
        JScrollPane scrollPane = new JScrollPane(area1);
        panel1.add(button);
        panel1.add(label);
        panel1.add(comboBox);
        panel1.add(button5);
        panel1.add(label1);
        panel1.add(textField3);
        dialog.setTitle("\\\\" + IP + "-" + "内网映射");
        dialog.add(scrollPane,BorderLayout.CENTER);
        dialog.add(panel1,BorderLayout.SOUTH);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setSize(800,600);
        dialog.setVisible(true);
        button5.addActionListener(a6->{
            switch (comboBox.getSelectedIndex()) {
                case 0:
                    try {
                        SendMessage.send(MessageFlags.LAN_ACCESS_GET,("URL:" + textField3.getText() + string).getBytes("GBK"),socket);
                    } catch (UnsupportedEncodingException e) {
                    }
                    break;
                case 1:
                    try {
                        SendMessage.send(MessageFlags.LAN_ACCESS_POST,("URL:" + textField3.getText() + string).getBytes("GBK"),socket);
                    } catch (UnsupportedEncodingException e) {
                    }
                    break;
            }
        });
        button.addActionListener(a->{
            JDialog dialog1 = new JDialog();
            defaultTableModel = new DefaultTableModel(null,new String[]{"请求头字段-键","请求头字段-值"});
            JTable table = new JTable(defaultTableModel);
            JButton button1 = new JButton("确定");
            JButton button2 = new JButton("添加请求头字段");
            JButton button3 = new JButton("移除请求头字段");
            JPanel panel = new JPanel();
            JTextArea textArea = new JTextArea(8, 30);
            JScrollPane jScrollPane = new JScrollPane(table);
            JScrollPane jScrollPane1 = new JScrollPane(textArea);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            panel.add(button1);
            panel.add(button2);
            panel.add(button3);
            dialog1.add(panel,BorderLayout.SOUTH);
            dialog1.add(jScrollPane,BorderLayout.NORTH);
            dialog1.add(jScrollPane1,BorderLayout.CENTER);
            dialog1.setResizable(false);
            dialog1.setLocationRelativeTo(null);
            dialog1.setSize(800,600);
            dialog1.setTitle("设置");
            dialog1.setVisible(true);
            button1.addActionListener(a5->{
                string += "head:";
                if(table.getRowCount() == 1) {
                    string += table.getValueAt(0,0);
                    string += "#";
                    string += table.getValueAt(0,1);
                }else {
                    for (int i = 0; i < table.getRowCount(); i++) {
                        string += table.getValueAt(i, 0);
                        string += "#";
                        string += table.getValueAt(i, 1);
                        string += "|";
                    }
                }
                string += "context:";
                string += textArea.getText();
                dialog1.dispose();
            });
            button3.addActionListener(a3->{
                defaultTableModel.removeRow(table.getSelectedRow());
            });
            button2.addActionListener(a1->{
                JDialog dialog2 = new JDialog();
                JTextField textField = new JTextField(20);
                JTextField textField1 = new JTextField(20);
                JButton button4 = new JButton("确定添加");
                JPanel panel2 = new JPanel();
                JPanel panel3 = new JPanel();
                JPanel panel4 = new JPanel();
                dialog2.add(panel2,BorderLayout.NORTH);
                dialog2.add(panel3,BorderLayout.CENTER);
                dialog2.add(panel4,BorderLayout.SOUTH);
                panel2.add(textField);
                panel3.add(textField1);
                panel4.add(button4);
                dialog2.setResizable(false);
                dialog2.setLocationRelativeTo(null);
                dialog2.setSize(400,150);
                dialog2.setTitle("设置值");
                dialog2.setVisible(true);
                button4.addActionListener(a2->{
                    defaultTableModel.addRow(new String[]{textField.getText(),textField1.getText()});
                });
            });
        });
    }
    public void update(byte[] context) {
        area1.setText(new String(context));
    }
}
