package me.server.receive;

import com.sun.jna.platform.WindowUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.InputStreamReader;
import java.net.Socket;

public class GetSystemInfo{
    DefaultTableModel defaultTableModel;
    Socket socket;
    public GetSystemInfo(Socket socket,String IP) {
        this.socket = socket;
        System.setProperty("sun.java2d.noddraw", "true");
        JDialog frame1 = new JDialog();
        WindowUtils.setWindowAlpha(frame1,0.8f);
        JTable table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel = new DefaultTableModel(null,new String[]{"硬件信息"});
        table.setModel(defaultTableModel);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        frame1.setTitle("\\\\" + IP + "-" + "系统信息");
        JScrollPane scrollPane = new JScrollPane(table);
        frame1.add(scrollPane);
        frame1.setLocationRelativeTo(null);
        frame1.setSize(500,300);
        frame1.setVisible(true);
    }
    public void update(byte[] bytes) {
        String context = new String(bytes);
        String[] strs = context.split("\\|");
        String[] s1 = {strs[0]};
        String[] s2 = {strs[1]};
        String[] s3 = {strs[2]};
        defaultTableModel.addRow(s1);
        defaultTableModel.addRow(s2);
        defaultTableModel.addRow(s3);
    }
}
