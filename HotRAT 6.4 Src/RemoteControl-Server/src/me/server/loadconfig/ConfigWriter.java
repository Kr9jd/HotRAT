package me.server.loadconfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class ConfigWriter {
    String path;
    public ConfigWriter(String path) throws FileNotFoundException {
        this.path = path;
    }
    public void Write(String key,String value) {
        try {
            PrintWriter writer = new PrintWriter(path);
            writer.print(key);
            writer.print(":");
            writer.print(value);
            writer.print(":");
            writer.println(key);
            writer.flush();
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void CreateCFG(String key,String value) throws IOException {
        File file = new File(path);
        if(!file.exists()) {
            file.createNewFile();
            ConfigWriter configWriter = new ConfigWriter(path);
            configWriter.Write(key,value);
        }else {
            System.out.println("null");
        }
    }
}
