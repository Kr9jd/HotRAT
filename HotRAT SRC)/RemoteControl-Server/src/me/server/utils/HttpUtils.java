package me.server.utils;

import javax.swing.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class HttpUtils {
    public static String get(URL url) {
        String context = "";
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            byte[] bytes = new byte[1024];
            int len = 0;
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.setRequestMethod("GET");
            while ((len = httpURLConnection.getInputStream().read(bytes))!=-1) {
                context += new String(bytes,0,len, StandardCharsets.UTF_8);
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,"网络连接出现异常!","错误",JOptionPane.ERROR_MESSAGE);
        }
        return context;
    }
}