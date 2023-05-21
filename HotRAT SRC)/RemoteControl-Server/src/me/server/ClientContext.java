package me.server;

import me.server.utils.MessageFlags;
import me.server.utils.PlayMusic;
import me.server.utils.ReceiveMessage;
import me.server.utils.SendMessage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.DataInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ClientContext implements Runnable {
    static Socket mesocket;
    DefaultTableModel tableModel;
    JFrame frame;
    JTable table;

    public ClientContext(Socket socket, DefaultTableModel tableModel, JFrame frame, JTable table) {
        mesocket = socket;
        this.table = table;
        this.tableModel = tableModel;
        this.frame = frame;
    }

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(mesocket.getInputStream());
            byte[] head1 = SendMessage.receiveHead(dataInputStream);
            if(head1[0] != MessageFlags.CLIENT_HEAD) {
                mesocket.close();
                return;
            }

            int headlen = SendMessage.receiveLength(dataInputStream);
            byte[] headdata= SendMessage.receiveContext(dataInputStream,headlen);
            if(!new String(headdata,"GBK").equals(Server.head)) {
                mesocket.close();
                return;
            }

            byte[] head2 = SendMessage.receiveHead(dataInputStream);
            if(head2[0] != MessageFlags.CLIENT_LOGIN) {
                mesocket.close();
                return;
            }

            int len = SendMessage.receiveLength(dataInputStream);
            byte[] data = SendMessage.receiveContext(dataInputStream,len);

            String infos = new String(data,"GBK");
            String[] info = infos.split("\\$");
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            SystemDisplay(info[2], info[3]);
            Server.map.put(infos, mesocket);
            Server.flashTable(table, tableModel);
            PlayMusic.online();
            Server.defaultTableModel.addRow(new String[]{format.format(date), "主机上线:" + info[1]});
            frame.setSize(1101, 701);//强制刷新
            frame.setSize(1100, 700);
            frame.repaint();
            new ReceiveMessage(mesocket, info[3]).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SystemDisplay(String str, String str2) throws AWTException {
        SystemTray systemTray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image);
        systemTray.add(trayIcon);
        trayIcon.displayMessage("新主机上线", str + "\n" + str2, TrayIcon.MessageType.INFO);
    }
}