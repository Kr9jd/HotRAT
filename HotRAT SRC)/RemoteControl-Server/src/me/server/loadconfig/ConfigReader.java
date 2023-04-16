package me.server.loadconfig;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    public String read(String path,String key) {
        String value = "";
        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            value = properties.getProperty(key);
            fileInputStream.close();
        }catch (Exception e) {
        }
        return value;
    }
}