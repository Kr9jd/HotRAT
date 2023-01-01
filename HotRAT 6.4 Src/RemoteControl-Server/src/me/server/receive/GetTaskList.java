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

public class GetTaskList{
    DefaultTableModel defaultTableModel;
    Socket socket;
    InputStream inputStream4 = GetTaskList.class.getClassLoader().getResourceAsStream("me/resources/tasklist.png");
    Image image = ImageIO.read(inputStream4);
    public GetTaskList(Socket socket,String IP) throws IOException {
        this.socket = socket;
        System.setProperty("sun.java2d.noddraw", "true");
        JDialog frame1 = new JDialog();
        WindowUtils.setWindowAlpha(frame1,0.8f);
        frame1.setIconImage(image);
        JPanel panel = new JPanel();
        JButton button = new JButton("刷新");
        JButton button1 = new JButton("结束进程");
        panel.add(button1);
        panel.add(button);
        frame1.add(panel,BorderLayout.NORTH);
        JTable table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel = new DefaultTableModel(null,new String[]{"","进程名","CPU","Memory","VSZ","RSS","PID","路径"});
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
        frame1.setTitle("\\\\" + IP + "-" + "进程管理");
        JScrollPane scrollPane = new JScrollPane(table);
        frame1.add(scrollPane);
        frame1.setLocationRelativeTo(null);
        frame1.setSize(1000,700);
        frame1.setVisible(true);
        button.addActionListener((a)->{
            flash(table);
        });
        button1.addActionListener((a)->{
            int i = table.getSelectedRow();
            String context = (String) table.getValueAt(i,6);
            SendMessage.Send(MessageFlags.STOP_PROCESS,context.trim().getBytes(),socket);
            flash(table);
        });
    }
    public void flash(JTable table) {
        for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
            defaultTableModel.removeRow(index);
        }
        SendMessage.SendHead(MessageFlags.UPDATE_TASKLIST,socket);
    }
    public void update(byte[] bytes) throws IOException {
        String context = new String(bytes);
        String[] strs = context.split("\\|");
        Object[] objects = {image,strs[0],strs[1],strs[2],strs[3],strs[4],strs[5],strs[6]};
        defaultTableModel.addRow(objects);
    }
}