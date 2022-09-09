package me.server.receive;

import jdk.nashorn.internal.scripts.JD;
import me.server.Server;

import javax.swing.*;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendHeartPack extends Thread{
    private DataOutputStream dataOutputStream;
    private JPanel panel;
    private JFrame frame;
    private JTextArea area;
    private String str;
    private JButton button;
    private static final String PACK = "";
    private String osName;
    public SendHeartPack(DataOutputStream dataOutputStream,JPanel panel,JFrame frame,JTextArea area,String str,JButton button,String osName) {
        this.dataOutputStream = dataOutputStream;
        this.panel = panel;
        this.frame = frame;
        this.area = area;
        this.str = str;
        this.button = button;
        this.osName = osName;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(5000L);
                this.dataOutputStream.writeUTF(PACK);
                this.dataOutputStream.flush();
            }
        } catch (Exception var4) {
            try {
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                area.append("[" + format.format(date)  + "]" +"有主机下线请注意!" + " 主机名称:" + str + "\n");
                panel.remove(button);
                frame.repaint();
                JOptionPane.showMessageDialog(null,str + ":主机已经下线..","提示",JOptionPane.ERROR_MESSAGE);
                if(osName.toLowerCase().indexOf("xp")!=-1) {
                    Server.WINDOWSXP--;
                    Server.ALL--;
                    frame.setTitle("HotRat v6.0 免杀版 | 当前在线->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
                } else if (osName.toLowerCase().indexOf("7")!=-1) {
                    Server.WINDOWS7--;
                    Server.ALL--;
                    frame.setTitle("HotRat v6.0 免杀版 | 当前在线->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
                }else if (osName.toLowerCase().indexOf("10")!=-1 || osName.toLowerCase().indexOf("11")!=-1) {
                    Server.WINDOWS10--;
                    Server.ALL--;
                    frame.setTitle("HotRat v6.0 免杀版 | 当前在线->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
                }else {
                    Server.OTHER--;
                    Server.ALL--;
                    frame.setTitle("HotRat v6.0 免杀版 | 当前在线->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
                }
            } catch (Exception var3) {
            }
        }
    }
}
