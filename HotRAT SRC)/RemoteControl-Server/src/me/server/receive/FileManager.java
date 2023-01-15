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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class FileManager extends Thread{
    public String fileName = "";
    public ArrayList<String> arrayList = new ArrayList<>();
    Socket socket;
    JTable table1;
    DefaultTableModel defaultTableModel1;
    DefaultTableModel defaultTableModel;
    InputStream icon = Server.class.getClassLoader().getResourceAsStream("me/resources/2.png");
    InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("me/resources/back.png");
    InputStream delete = Server.class.getClassLoader().getResourceAsStream("me/resources/delete.png");
    InputStream more = Server.class.getClassLoader().getResourceAsStream("me/resources/more.png");
    InputStream create = Server.class.getClassLoader().getResourceAsStream("me/resources/create.png");
    InputStream inputStreams = FileManager.class.getClassLoader().getResourceAsStream("me/resources/files.png");
    InputStream inputStream1 = FileManager.class.getClassLoader().getResourceAsStream("me/resources/directory.png");
    InputStream inputStream2 = FileManager.class.getClassLoader().getResourceAsStream("me/resources/disks.png");
    Image icons = ImageIO.read(icon);
    Image fileimage = ImageIO.read(inputStreams);
    Image fileimage1 = ImageIO.read(inputStream1);
    Image fileimage2 = ImageIO.read(inputStream2);
    Image deleteimage = ImageIO.read(delete);
    Image createimage = ImageIO.read(create);
    Image moreimage = ImageIO.read(more);
    public FileManager(Socket socket, String IP) throws Exception{
        Image image = ImageIO.read(inputStream);
        this.socket = socket;
        JDialog dialog = new JDialog();
        dialog.setIconImage(icons);
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JButton button = new JButton();
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();
        JSplitPane splitPane=new JSplitPane();
        JSplitPane splitPane1=new JSplitPane();
        button.setMargin(new Insets(0,0,0,0));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button1.setMargin(new Insets(0,0,0,0));
        button1.setBorderPainted(false);
        button1.setFocusPainted(false);
        button1.setContentAreaFilled(false);
        button2.setMargin(new Insets(0,0,0,0));
        button2.setBorderPainted(false);
        button2.setFocusPainted(false);
        button2.setContentAreaFilled(false);
        button3.setMargin(new Insets(0,0,0,0));
        button3.setBorderPainted(false);
        button3.setFocusPainted(false);
        button3.setContentAreaFilled(false);
        panel.add(button);
        JTable table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        defaultTableModel = new DefaultTableModel(null,new String[]{"","类型","文件名","修改日期","文件大小"});
        table.setModel(defaultTableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(230);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(0).setCellRenderer(new ImageRendererUtils());
        JScrollPane scrollPane = new JScrollPane(table);
        JTextField textField = new JTextField(30);
        textField.setEditable(false);
        panel.add(textField);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        dialog.add(scrollPane,BorderLayout.CENTER);
        dialog.add(panel,BorderLayout.NORTH);
        dialog.setTitle("\\\\" + IP + "-" + "文件管理");
        dialog.setVisible(true);
        dialog.setSize(700,500);
        dialog.setLocationRelativeTo(null);
        button.setIcon(new ImageIcon(image));
        button.setBorderPainted(false);
        button1.setIcon(new ImageIcon(deleteimage));
        button1.setBorderPainted(false);
        button2.setIcon(new ImageIcon(createimage));
        button2.setBorderPainted(false);
        button3.setIcon(new ImageIcon(moreimage));
        button3.setBorderPainted(false);

        table1 = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setShowVerticalLines(false);
        table1.setShowHorizontalLines(false);
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        defaultTableModel1 = new DefaultTableModel(null,new String[]{"文件名","上传/下载"});
        table1.setModel(defaultTableModel1);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table1.getColumnModel().getColumn(0).setPreferredWidth(120);
        table1.getColumnModel().getColumn(1).setPreferredWidth(120);

        JScrollPane scrollPane1 = new JScrollPane(table1);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(scrollPane);
        splitPane.setRightComponent(scrollPane1);
        splitPane.setDividerSize(3);
        splitPane.setDividerLocation(670);
        textField.setEditable(false);
        panel.add(textField);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel1.add(splitPane1);
        dialog.add(splitPane,BorderLayout.CENTER);
        dialog.add(panel,BorderLayout.NORTH);
        dialog.setTitle("\\\\" + IP + "-" + "文件管理");
        dialog.setVisible(true);
        dialog.setSize(900,600);
        dialog.setLocationRelativeTo(null);
        button.setBorderPainted(false);
        button1.setBorderPainted(false);
        button2.setBorderPainted(false);
        button3.setBorderPainted(false);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SendMessage.SendHead(MessageFlags.CLOSE_FILE_MANAGER,socket);
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    int i = table.getSelectedRow();
                    String str = (String) table.getValueAt(i,1);
                    if(str.equals("Directory") || str.equals("Disk")) {
                        String file =textField.getText() + "\\" + (String) table.getValueAt(i,2);
                        if(table.getValueAt(i,2) == null) {
                            file = (String) table.getValueAt(i,2);
                        }
                        textField.setText(file);
                        for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
                            defaultTableModel.removeRow(index);
                        }
                        SendMessage.Send(MessageFlags.FILE_QUERY,textField.getText().getBytes(),socket);
                    }
                }
            }
        });
        button.addActionListener(a->{
            for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
                defaultTableModel.removeRow(index);
            }
            if(textField.getText().length() <= 4) {
                SendMessage.SendHead(MessageFlags.DISK_QUERT,socket);
                textField.setText("");
            }else {
                String path = textField.getText().substring(0, textField.getText().lastIndexOf("\\"));
                textField.setText(path);
                SendMessage.Send(MessageFlags.FILE_QUERY, path.getBytes(), socket);
            }
        });
        button1.addActionListener(a->{
                int i = table.getSelectedRow();
                String fileName = (String) table.getValueAt(i,2);
                String str =textField.getText() + "\\" + fileName;
                SendMessage.Send(MessageFlags.FILE_DELETE,str.getBytes(),socket);
                flash(table, textField.getText());
        });
        button2.addActionListener(a->{
            String str =textField.getText() + "\\" + JOptionPane.showInputDialog(null,"新建的文件名","新建",JOptionPane.QUESTION_MESSAGE);
            SendMessage.Send(MessageFlags.FILE_CREATE,str.getBytes(),socket);
            flash(table, textField.getText());
        });
        button3.addActionListener(a->{
            String[] modes = {"打开","HTTP下载","上传文件","下载文件"};
            String mode = (String) JOptionPane.showInputDialog(null,"选择","选择",JOptionPane.QUESTION_MESSAGE,null,modes,modes[0]);
            switch (mode) {
                case "打开":
                    int i = table.getSelectedRow();
                    String fileName = (String) table.getValueAt(i,2);
                        String str1 =textField.getText() + "\\" + fileName;
                        SendMessage.Send(MessageFlags.FILE_OPEN,str1.getBytes(),socket);
                    break;
                case "HTTP下载":
                        String str2 =textField.getText() + "\\" + JOptionPane.showInputDialog(null,"下载的文件名+后缀","Download",JOptionPane.QUESTION_MESSAGE) + "-path" + JOptionPane.showInputDialog(null,"输入文件URL","Download",JOptionPane.QUESTION_MESSAGE);
                        SendMessage.Send(MessageFlags.FILE_HTTPDOWNLOAD,str2.getBytes(),socket);
                    break;
                case "上传文件" :
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    FileUpLoad(textField.getText());
                                }catch (Exception e) {
                                }
                            }
                        }).start();
                    break;
                case "下载文件":
                    int i1 = table.getSelectedRow();
                    if(table.getValueAt(i1, 1).equals("File")) {
                        String fileName1 = (String) table.getValueAt(i1, 2);
                        String tableString = fileName1 + "$" + "下载";
                        arrayList.add(tableString);
                        flashTable();
                        FileDownLoad(textField.getText(), fileName1);
                    }
                    break;
            }
        });
    }
    public void FileDownLoad(String paths,String fileName) {
        String path = paths + "\\" + fileName;
        SendMessage.Send(MessageFlags.FILE_DOWNLOAD,path.getBytes(),socket);
    }
    public void flash(JTable table, String path) {
        for(int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
            defaultTableModel.removeRow(index);
        }
        SendMessage.Send(MessageFlags.FILE_QUERY, path.getBytes(), socket);
    }
    public void flashTable() {
        for(int index = table1.getModel().getRowCount() - 1; index >= 0; index--) {
            defaultTableModel1.removeRow(index);
        }
        for(String context:arrayList) {
            String[] strings = context.split("\\$");
            defaultTableModel1.addRow(strings);
        }
    }
    public void FileUpLoad(String path) throws Exception {
        JFileChooser dlg = new JFileChooser();
        int filelen = 8 * 1024;
        byte[] bytes;
        dlg.setDialogTitle("选择文件");
        dlg.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = dlg.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = dlg.getSelectedFile();
            String tableName = file.getName() + "$上传";
            arrayList.add(tableName);
            flashTable();
            String str =path + "\\" + file.getName();
            SendMessage.Send(MessageFlags.FILE_CREATEWITHNAME,str.getBytes(),socket);
            FileInputStream fileInputStream = new FileInputStream(file);
            if(file.length() < filelen) {
                bytes = new byte[(int)file.length()];
                fileInputStream.read(bytes);
                SendMessage.SendHead(MessageFlags.FILE_PREPARE,socket);
                SendMessage.Send(MessageFlags.FILE_UPLOAD,bytes,socket);
                SendMessage.Send(MessageFlags.FILE_UPLOAD_END,file.getName().getBytes(),socket);
                fileInputStream.close();
            }else {
                SendMessage.SendHead(MessageFlags.FILE_PREPARE,socket);
                int len = (int) (file.length()/filelen);
                int len1 = (int) (file.length()%filelen);
                for(;len > 0;len--) {
                    bytes = new byte[filelen];
                    fileInputStream.read(bytes);
                    Thread.sleep(70);
                    SendMessage.Send(MessageFlags.FILE_UPLOAD,bytes,socket);
                }
                bytes = new byte[len1];
                fileInputStream.read(bytes);
                SendMessage.Send(MessageFlags.FILE_UPLOAD,bytes,socket);
                SendMessage.Send(MessageFlags.FILE_UPLOAD_END,file.getName().getBytes(),socket);
                fileInputStream.close();
            }
        }
    }
    public void update(byte[] bytes) throws IOException {
        String context = new String(bytes);
        String[] strings = context.split("\\|");
        Object[] filestrs = {fileimage,strings[0],strings[1],strings[2],strings[3]};
        Object[] directorystrs = {fileimage1,strings[0],strings[1],strings[2],strings[3]};
        Object[] diskstrs = {fileimage2,strings[0],strings[1],strings[2],strings[3]};
        if(strings[0].equals("File")) {
            defaultTableModel.addRow(filestrs);
        } else if (strings[0].equals("Disk")) {
            defaultTableModel.addRow(diskstrs);
        } else if (strings[0].equals("Directory")) {
            defaultTableModel.addRow(directorystrs);
        }
    }
}