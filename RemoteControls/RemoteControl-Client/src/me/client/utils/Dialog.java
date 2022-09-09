package me.client.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Dialog {
    public static void dialog1(String str) {
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        JButton button = new JButton("确定");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        JPanel jPanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                InputStream inputStream = Dialog.class.getClassLoader().getResourceAsStream("me/client/resources/1.png");
                try {
                    Image image = ImageIO.read(inputStream);
                    g.drawImage(image,30,28,null);
                    Font font = new Font(Font.DIALOG,Font.BOLD,15);
                    g.setFont(font);
                    g.drawString(str,70,45);
                } catch (IOException e) {
                }
            }
        };
        dialog.setResizable(false);
        dialog.add(buttonPanel,BorderLayout.SOUTH);
        dialog.add(jPanel);
        dialog.setSize(300,150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        button.addActionListener(a->{
            dialog.dispose();
        });
    }
    public static void dialog2(String str) {
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        JButton button = new JButton("确定");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        JPanel jPanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                InputStream inputStream = Dialog.class.getClassLoader().getResourceAsStream("me/client/resources/2.png");
                try {
                    Image image = ImageIO.read(inputStream);
                    g.drawImage(image,30,28,null);
                    Font font = new Font(Font.DIALOG,Font.BOLD,15);
                    g.setFont(font);
                    g.drawString(str,70,45);
                } catch (IOException e) {
                }
            }
        };
        dialog.setResizable(false);
        dialog.add(buttonPanel,BorderLayout.SOUTH);
        dialog.add(jPanel);
        dialog.setSize(300,150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        button.addActionListener(a->{
            dialog.dispose();
        });
    }
    public static void dialog3(String str) {
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        JButton button = new JButton("确定");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        JPanel jPanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                InputStream inputStream = Dialog.class.getClassLoader().getResourceAsStream("me/client/resources/3.png");
                try {
                    Image image = ImageIO.read(inputStream);
                    g.drawImage(image,30,28,null);
                    Font font = new Font(Font.DIALOG,Font.BOLD,15);
                    g.setFont(font);
                    g.drawString(str,70,45);
                } catch (IOException e) {
                }
            }
        };
        dialog.setResizable(false);
        dialog.add(buttonPanel,BorderLayout.SOUTH);
        dialog.add(jPanel);
        dialog.setSize(300,150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        button.addActionListener(a->{
            dialog.dispose();
        });
    }
    public static void dialog4(String str) {
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        JButton button = new JButton("确定");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        JPanel jPanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                InputStream inputStream = Dialog.class.getClassLoader().getResourceAsStream("me/client/resources/4.png");
                try {
                    Image image = ImageIO.read(inputStream);
                    g.drawImage(image,30,28,null);
                    Font font = new Font(Font.DIALOG,Font.BOLD,15);
                    g.setFont(font);
                    g.drawString(str,70,45);
                } catch (IOException e) {
                }
            }
        };
        dialog.setResizable(false);
        dialog.add(buttonPanel,BorderLayout.SOUTH);
        dialog.add(jPanel);
        dialog.setSize(300,150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        button.addActionListener(a->{
            dialog.dispose();
        });
    }
    public static void dialog5(String str) {
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        JButton button = new JButton("确定");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        JPanel jPanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                InputStream inputStream = Dialog.class.getClassLoader().getResourceAsStream("me/client/resources/5.png");
                try {
                    Image image = ImageIO.read(inputStream);
                    g.drawImage(image,30,28,null);
                    Font font = new Font(Font.DIALOG,Font.BOLD,15);
                    g.setFont(font);
                    g.drawString(str,70,45);
                } catch (IOException e) {
                }
            }
        };
        dialog.setResizable(false);
        dialog.add(buttonPanel,BorderLayout.SOUTH);
        dialog.add(jPanel);
        dialog.setSize(300,150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        button.addActionListener(a->{
            dialog.dispose();
        });
    }
}
