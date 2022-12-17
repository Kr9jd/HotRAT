package me.client.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class HttpDownload {
    public static void download(String pathAndName,URL url) {
        try(InputStream inputStream = url.openStream(); FileOutputStream fileOutputStream = new FileOutputStream(pathAndName)) {
            int len = 0;
            byte[] bytes = new byte[8*1024];
            while ((len = inputStream.read(bytes))!=-1) {
                fileOutputStream.write(bytes,0,len);
            }
        }catch (Exception e) {
        }
    }
}