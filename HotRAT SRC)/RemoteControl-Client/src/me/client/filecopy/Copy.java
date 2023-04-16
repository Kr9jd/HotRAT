package me.client.filecopy;

import me.client.Client;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Copy {
    String path;
    String endPath;
    public Copy(String path,String endPath) {
        this.path = path;
        this.endPath = endPath;
    }
    public void copy() {
        try(FileInputStream fileInputStream = new FileInputStream(path); FileOutputStream fileOutputStream = new FileOutputStream(endPath)) {
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes))!=-1) {
                fileOutputStream.write(bytes,0,len);
            }
        }catch (Exception e) {
        }
    }
}
