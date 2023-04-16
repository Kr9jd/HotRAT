package me.server.receive;

import me.server.Server;
import me.server.utils.MessageFlags;
import me.server.utils.PlayMusic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SendHeartPack extends Thread{
    private JFrame frame;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    public SendHeartPack(JFrame frame,JTable table, DefaultTableModel defaultTableModel) {
            this.frame = frame;
            this.table = table;
            this.defaultTableModel = defaultTableModel;
    }

    public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                }
                if (!Server.map.isEmpty()) {
                    for (String str : Server.map.keySet()) {
                        Socket socket = Server.map.get(str);
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                            dataOutputStream.write(MessageFlags.HEARTPACK);
                        } catch (Exception e) {
                            try {
                                Date date = new Date();
                                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                                PlayMusic.offline();
                                Server.map.remove(str);
                                socket.close();
                                Server.flashTable(table, defaultTableModel);
                                Server.defaultTableModel.addRow(new String[]{format.format(date), "主机下线:" + "computerName"});
                                frame.repaint();
                                JOptionPane.showMessageDialog(null, "computerName" + ":主机已经下线..", "提示", JOptionPane.ERROR_MESSAGE);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
    }
}