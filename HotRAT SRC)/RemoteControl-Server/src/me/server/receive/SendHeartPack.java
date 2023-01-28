package me.server.receive;

import me.server.Server;
import me.server.utils.MessageFlags;
import me.server.utils.PlayMusic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendHeartPack extends Thread{
    private JFrame frame;
    private Socket socket;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private String computerName;
    private String contexts;
    public SendHeartPack(Socket socket, JFrame frame,JTable table, DefaultTableModel defaultTableModel,String contexts,String computerName) {
            this.frame = frame;
            this.socket = socket;
            this.table = table;
            this.defaultTableModel = defaultTableModel;
            this.contexts = contexts;
            this.computerName = computerName;
    }

    public void run() {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while (true) {
                Thread.sleep(5000L);
                dataOutputStream.write(MessageFlags.HEARTPACK);
            }
        } catch (Exception var4) {
            try {
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                PlayMusic.offline();
                Server.map.remove(contexts);
                Server.flashTable(table,defaultTableModel);
                Server.defaultTableModel.addRow(new String[]{format.format(date),"主机下线:" + computerName});
                frame.repaint();
                JOptionPane.showMessageDialog(null,computerName + ":主机已经下线..","提示",JOptionPane.ERROR_MESSAGE);
            } catch (Exception var3) {
            }
        }
    }
}
