package me.server.receive;

import me.server.Server;
import me.server.utils.ImageRendererUtils;
import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RegisterManager {
    InputStream inputStream = FileManager.class.getClassLoader().getResourceAsStream("me/resources/directory.png");
    InputStream inputStream1 = FileManager.class.getClassLoader().getResourceAsStream("me/resources/reg.png");
    InputStream inputStream2 = FileManager.class.getClassLoader().getResourceAsStream("me/resources/back.png");
    InputStream inputStream3 = FileManager.class.getClassLoader().getResourceAsStream("me/resources/delete.png");
    InputStream inputStream4 = FileManager.class.getClassLoader().getResourceAsStream("me/resources/create.png");
    InputStream inputStream5 = FileManager.class.getClassLoader().getResourceAsStream("me/resources/dword.png");
    InputStream inputStream6 = FileManager.class.getClassLoader().getResourceAsStream("me/resources/5.png");
    Image fileimage = ImageIO.read(inputStream);
    Image fileimage1 = ImageIO.read(inputStream1);
    Image fileimage2 = ImageIO.read(inputStream2);
    Image fileimage3 = ImageIO.read(inputStream3);
    Image fileimage4 = ImageIO.read(inputStream4);
    Image fileimage5 = ImageIO.read(inputStream5);
    Image icon = ImageIO.read(inputStream6);
    Socket socket;
    public JTable table;
    public JButton button;
    JTable table1;
    DefaultTableModel defaultTableModel1;
    DefaultTableModel defaultTableModel;
    public RegisterManager(Socket socket,String IP) throws IOException {
        this.socket = socket;
        JDialog dialog = new JDialog();
        dialog.setIconImage(icon);
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        button = new JButton();
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JSplitPane splitPane = new JSplitPane();
        JSplitPane splitPane1 = new JSplitPane();
        JComboBox<String> jComboBox = new JComboBox<>();
        jComboBox.addItem("HKEY_CLASSES_ROOT");
        jComboBox.addItem("HKEY_CURRENT_USER");
        jComboBox.addItem("HKEY_LOCAL_MACHINE");
        jComboBox.addItem("HKEY_USERS");
        jComboBox.addItem("HKEY_CURRENT_CONFIG");
        button.setMargin(new Insets(0,0,0,0));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button1.setMargin(new Insets(0,0,0,0));
        button1.setBorderPainted(false);
        button1.setFocusPainted(false);
        button1.setContentAreaFilled(false);
        button2.setMargin(new Insets(0,0,0,0));
        button2.setBorderPainted(false);
        button2.setFocusPainted(false);
        button2.setContentAreaFilled(false);
        table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        defaultTableModel = new DefaultTableModel(null,new String[]{"","键名"});
        table.setModel(defaultTableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(0).setCellRenderer(new ImageRendererUtils());
        JScrollPane scrollPane = new JScrollPane(table);
        JTextField textField = new JTextField(50);
        textField.setEditable(false);
        panel.add(textField);
        panel.add(button1);
        panel.add(button2);
        dialog.add(scrollPane,BorderLayout.CENTER);
        dialog.add(panel,BorderLayout.NORTH);
        dialog.setVisible(true);
        dialog.setSize(700,500);
        dialog.setLocationRelativeTo(null);
        table1 = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setShowVerticalLines(false);
        table1.setShowHorizontalLines(false);
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        defaultTableModel1 = new DefaultTableModel(null,new String[]{"","名称","类型","值"});
        table1.setModel(defaultTableModel1);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table1.getColumnModel().getColumn(0).setPreferredWidth(20);
        table1.getColumnModel().getColumn(1).setPreferredWidth(250);
        table1.getColumnModel().getColumn(2).setPreferredWidth(120);
        table1.getColumnModel().getColumn(3).setPreferredWidth(400);
        table1.getColumnModel().getColumn(0).setCellRenderer(new ImageRendererUtils());
        JScrollPane scrollPane1 = new JScrollPane(table1);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(scrollPane);
        splitPane.setRightComponent(scrollPane1);
        splitPane.setDividerSize(3);
        splitPane.setDividerLocation(140);
        textField.setEditable(false);
        panel.add(jComboBox);
        panel.add(button);
        panel.add(textField);
        panel.add(button1);
        panel.add(button2);
        panel1.add(splitPane1);
        dialog.add(splitPane,BorderLayout.CENTER);
        dialog.add(panel,BorderLayout.NORTH);
        dialog.setTitle("\\\\" + IP + "-" + "注册表");
        dialog.setVisible(true);
        dialog.setSize(900,600);
        dialog.setLocationRelativeTo(null);
        button.setIcon(new ImageIcon(fileimage2));
        button.setBorderPainted(false);
        button1.setIcon(new ImageIcon(fileimage3));
        button1.setBorderPainted(false);
        button2.setIcon(new ImageIcon(fileimage4));
        button2.setBorderPainted(false);
        jComboBox.addActionListener(a->{
            flash();
            textField.setText("");
            switch (jComboBox.getSelectedIndex()) {
                case 0:
                    SendMessage.Send(MessageFlags.REGIDTER_QUERY_ROOT_KEY, (Integer.MIN_VALUE + "").getBytes(),socket);
                    break;
                case 1:
                    SendMessage.Send(MessageFlags.REGIDTER_QUERY_ROOT_KEY, (-2147483647 + "").getBytes(),socket);
                    break;
                case 2:
                    SendMessage.Send(MessageFlags.REGIDTER_QUERY_ROOT_KEY, (-2147483646+ "").getBytes(),socket);
                    break;
                case 3:
                    SendMessage.Send(MessageFlags.REGIDTER_QUERY_ROOT_KEY, (-2147483645 + "").getBytes(),socket);
                    break;
                case 4:
                    SendMessage.Send(MessageFlags.REGIDTER_QUERY_ROOT_KEY, (-2147483643 + "").getBytes(),socket);
                    break;
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    int i = table.getSelectedRow();
                    String str = (String) table.getValueAt(i,1);
                    String file;
                    if(!textField.getText().isEmpty()) {
                        file = textField.getText() + "\\" + str;
                    }else {
                        file = str;
                    }
                        textField.setText(file);
                        for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
                            defaultTableModel.removeRow(index);
                        }
                    for(int index = table1.getModel().getRowCount() - 1; index >= 0; index--) {
                        defaultTableModel1.removeRow(index);
                    }
                        SendMessage.Send(MessageFlags.REGIDTER_QUERY_KEY,(getValue(jComboBox.getSelectedIndex()) + ":" +textField.getText()).getBytes(),socket);
                    }
                }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    setValue(getValue(jComboBox.getSelectedIndex()),textField.getText(), (String) table1.getValueAt(table1.getSelectedRow(),1), (String) table1.getValueAt(table1.getSelectedRow(),2),(String) table1.getValueAt(table1.getSelectedRow(),3));
                }
            }
        });
        button.addActionListener(a->{
            for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
                defaultTableModel.removeRow(index);
            }
            for(int index = table1.getModel().getRowCount() - 1; index >= 0; index--) {
                defaultTableModel1.removeRow(index);
            }
            if(textField.getText().lastIndexOf("\\") == -1) {
                textField.setText("");
                SendMessage.Send(MessageFlags.REGIDTER_QUERY_ROOT_KEY, (getValue(jComboBox.getSelectedIndex())+"").getBytes(), socket);
            }else {
                String path = textField.getText().substring(0, textField.getText().lastIndexOf("\\"));
                textField.setText(path);
                SendMessage.Send(MessageFlags.REGIDTER_QUERY_KEY, (getValue(jComboBox.getSelectedIndex()) + ":" +textField.getText()).getBytes(), socket);
            }
        });
        button1.addActionListener(a->{
            SendMessage.Send(MessageFlags.REGIDTER_DELETE_VALUE,(getValue(jComboBox.getSelectedIndex()) + "!" + textField.getText() + "!" + (String) table1.getValueAt(table1.getSelectedRow(),1)).getBytes(StandardCharsets.UTF_8),socket);
            for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
                defaultTableModel.removeRow(index);
            }
            for(int index = table1.getModel().getRowCount() - 1; index >= 0; index--) {
                defaultTableModel1.removeRow(index);
            }
        });
        button2.addActionListener(a->{
            String name = JOptionPane.showInputDialog(null,"输入新值名","创建",JOptionPane.QUESTION_MESSAGE);
            String typename = null;
            String[] types = {"字符串型","长字符串型","长整型"};
            String type = (String) JOptionPane.showInputDialog(null,"更多功能","更多",JOptionPane.QUESTION_MESSAGE,null,types,types[0]);
            switch (type) {
                case "字符串型":
                    typename = "REG_SZ";
                    break;
                case "长字符串型":
                    typename = "REG_EXPAND_SZ";
                    break;
                case "长整型":
                    typename = "REG_DWORD";
                    break;
            }
            SendMessage.Send(MessageFlags.REGIDTER_CREATE_VALUE,(getValue(jComboBox.getSelectedIndex()) + "!" + textField.getText() + "!" + name + "!" + typename).getBytes(StandardCharsets.UTF_8),socket);
            for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
                defaultTableModel.removeRow(index);
            }
            for(int index = table1.getModel().getRowCount() - 1; index >= 0; index--) {
                defaultTableModel1.removeRow(index);
            }
        });
    }
    private int getValue(int value) {
        int i = 0;
        switch (value) {
            case 0:
                i = Integer.MIN_VALUE;
                break;
            case 1:
                i = -2147483647;
                break;
            case 2:
                i = -2147483646;
                break;
            case 3:
                i = -2147483645;
                break;
            case 4:
                i = -2147483644;
                break;
        }
        return i;
    }
    private void setValue(int hkey,String path,String value,String type,String context) {
        JDialog dialog = new JDialog();
        dialog.setTitle("编辑");
        JTextField textField = new JTextField(20);
        JTextField textField1 = new JTextField(20);
        textField.setText(value);
        textField1.setText(context);
        JLabel label = new JLabel("名称");
        JLabel label1 = new JLabel("编辑值");
        textField.setEditable(false);
        JButton button = new JButton("确定");
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        JPanel panel = new JPanel();
        top.add(label);
        bottom.add(label1);
        panel.add(button);
        bottom.add(textField1);
        top.add(textField);
        dialog.add(top, BorderLayout.NORTH);
        dialog.add(bottom,BorderLayout.CENTER);
        dialog.add(panel,BorderLayout.SOUTH);
        dialog.setVisible(true);
        dialog.setSize(400,150);
        dialog.setLocationRelativeTo(null);
        button.addActionListener(a->{
            SendMessage.Send(MessageFlags.REGIDTER_SET_VALUE,(hkey + "!" + path + "!" + value + "!" +type + "!" + textField1.getText()).getBytes(StandardCharsets.UTF_8),socket);
            dialog.dispose();
        });
    }
    public void update(byte[] bytes) throws IOException {
        String context = new String(bytes);
        Object[] filestrs = {fileimage,context};
        defaultTableModel.addRow(filestrs);
    }
    public void updateValueTable(byte[] bytes) {
        String context = new String(bytes);
        String[] strings = context.split("\\|");
        if(!strings[1].equals("REG_EXPAND_SZ") && !strings[1].equals("REG_SZ") && !strings[1].equals("REG_MULTI_SZ")) {
            Object[] obj = {fileimage5,strings[0],strings[1],strings[2]};
            defaultTableModel1.addRow(obj);
        }else {
            Object[] obj = {fileimage1,strings[0],strings[1],strings[2]};
            defaultTableModel1.addRow(obj);
        }
    }
    public void flash() {
        for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
            defaultTableModel.removeRow(index);
        }
    }
}