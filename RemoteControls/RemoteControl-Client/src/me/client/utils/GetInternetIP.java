package me.client.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetInternetIP {
    public static String getIP() {
        String str = "";
        try {
            URL url = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
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
        return str.substring(str.indexOf("cip") + 7,str.indexOf("cid") - 4);//解析html报文
    }
    public static String getRegion() {
        String str = "";
        try {
            URL url = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while((len = inputStream.read(bytes))!=-1) {
                str += new String(bytes,0,len,"UTF-8");
            }
        }catch (Exception e) {
            str = "未知";
        }
        return str.substring(str.indexOf("cname") + 9,str.indexOf("}") -1);
    }
}