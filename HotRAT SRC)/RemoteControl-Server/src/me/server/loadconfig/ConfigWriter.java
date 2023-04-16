package me.server.loadconfig;

import java.io.*;
import java.util.Properties;

public class ConfigWriter {
    Properties properties = new Properties();
    public ConfigWriter() {
    }
    public void write(String path,String key,String value) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            properties.setProperty(key,value);
            properties.store(fileOutputStream,null);
            fileOutputStream.close();
        }catch (Exception e) {
        }
    }
}
