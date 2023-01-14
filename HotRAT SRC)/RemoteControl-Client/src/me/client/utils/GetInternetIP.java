package me.client.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetInternetIP {
    public static String getIP() {
        String str = "";
        try {
            URL url = new URL("http://txt.go.sohu.com/ip/soip");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while((len = inputStream.read(bytes))!=-1) {
                str += new String(bytes,0,len);
            }
        }catch (Exception e) {
            str = "127.0.0.1";
        }
        return str.substring(str.indexOf("window.sohu_user_ip") + 21,str.indexOf(";sohu_IP_Loc")-1);//解析html报文
    }
    public static String getRegion(String IP) {
        String str = "";
        try {
            URL url = new URL("http://opendata.baidu.com/api.php?query=" + IP + "&co=&resource_id=6006&oe=utf8");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while((len = inputStream.read(bytes))!=-1) {
                str += new String(bytes,0,len, StandardCharsets.UTF_8);
            }
        }catch (Exception e) {
            str = "未知";
        }
        return str.substring(str.indexOf("location") + 11,str.indexOf("origip") -3);
    }
}