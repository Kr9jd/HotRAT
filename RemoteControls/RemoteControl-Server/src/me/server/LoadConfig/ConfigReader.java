package me.server.LoadConfig;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ConfigReader {
    public static String Read(String path,String ReadKey) {
        String value = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes))!=-1) {
                value += new String(bytes,0,len);
            }
            fileInputStream.close();
        }catch (Exception e) {
        }

        return value.substring(value.indexOf(ReadKey+":") + ReadKey.length() + 1,value.indexOf(":" + ReadKey));
    }
}