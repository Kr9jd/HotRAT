package me.server.receive;

import me.server.utils.MessageFlags;
import me.server.utils.SendMessage;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.Socket;

public class Audio {
    Socket socket;
    JTextArea jta;
    SourceDataLine auline;
    public Audio(Socket socket,String IP) throws FileNotFoundException {
        this.socket = socket;
        init();
        JDialog jDialog = new JDialog();
        jDialog.setTitle("\\\\" + IP + "-" + "语音监听");
        jta=new JTextArea();
        jta.setEditable(false);
        jta.setBackground(Color.BLACK);
        jta.setForeground(Color.green);
        jta.append("正在监听远程语音.. \n");
        JScrollPane jsp=new JScrollPane(jta);
        JPanel jp=new JPanel();
        Font font = new Font(Font.SERIF,Font.PLAIN,13);
        jta.setFont(font);
        jDialog.add(jsp,BorderLayout.CENTER);
        jDialog.add(jp,BorderLayout.SOUTH);
        jDialog.setSize(500,300);
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
        jDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SendMessage.SendHead(MessageFlags.AUDIO_CLOSE,socket);
                auline.close();
            }
        });
    }
    public void play(byte[] bytes) throws Exception {
        auline.write(bytes,0,bytes.length);
    }
    public void init() {
        try {
            AudioFormat.Encoding encoding = new AudioFormat.Encoding("PCM_SIGNED");
            AudioFormat format = new AudioFormat(encoding, 44100, 16, 2, 4, 44100, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
            auline.start();
        }catch (Exception e) {
        }
    }
}
