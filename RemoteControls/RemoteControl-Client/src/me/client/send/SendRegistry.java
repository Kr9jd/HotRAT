package me.client.send;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import me.client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SendRegistry extends Thread{

    OutputStreamWriter writer;
    Socket socket;
    String path;

    public SendRegistry(OutputStreamWriter writer,Socket socket,String path) {
        this.writer = writer;
        this.socket = socket;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            Process process = Runtime.getRuntime().exec("cmd /c reg query " + path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String str = "";
            while ((str = reader.readLine())!=null) {
                if(str.indexOf("REG_SZ")!=-1) {
                    writer.write("<REG_SZ>:" + str + "\n");
                    writer.flush();
                }else if(str.indexOf("REG_MULTI_SZ")!=-1) {
                    writer.write("<REG_MULTI_SZ>:" + str + "\n");
                    writer.flush();
                }else if(str.indexOf("REG_BINARY")!=-1) {
                    writer.write("<REG_BINARY>:" + str + "\n");
                    writer.flush();
                }else if(str.indexOf("REG_DWORD")!=-1) {
                    writer.write("<REG_DWORD>:" + str + "\n");
                    writer.flush();
                }else {
                    writer.write(str + "\n");
                    writer.flush();
                }
            }
            writer.write("end");
            writer.flush();
        }catch (Exception e) {
        }
    }
    public void DeleteValue(String key) {
        if(path.indexOf("HKEY_CLASSES_ROOT")!=-1) {
            Advapi32Util.registryDeleteValue(WinReg.HKEY_CLASSES_ROOT,path.replace("HKEY_CLASSES_ROOT\\",""),key);
        }else if(path.indexOf("HKEY_CURRENT_USER")!=-1) {
            Advapi32Util.registryDeleteValue(WinReg.HKEY_CURRENT_USER,path.replace("HKEY_CURRENT_USER\\",""),key);
        }else if(path.indexOf("HKEY_LOCAL_MACHINE")!=-1) {
            Advapi32Util.registryDeleteValue(WinReg.HKEY_LOCAL_MACHINE,path.replace("HKEY_LOCAL_MACHINE\\",""),key);
        }else if(path.indexOf("HKEY_USERS")!=-1) {
            Advapi32Util.registryDeleteValue(WinReg.HKEY_USERS,path.replace("HKEY_USERS\\",""),key);
        }else if(path.indexOf("HKEY_CURRENT_CONFIG")!=-1) {
            Advapi32Util.registryDeleteValue(WinReg.HKEY_CURRENT_CONFIG,path.replace("HKEY_CURRENT_CONFIG\\",""),key);
        }
    }
    public void StringValue(String key,String value) {
        if(path.indexOf("HKEY_CLASSES_ROOT")!=-1) {
            System.out.println(key+value+path.replace("HKEY_CLASSES_ROOT\\",""));
        }else if(path.indexOf("HKEY_CURRENT_USER")!=-1) {
            Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER,path.replace("HKEY_CURRENT_USER\\",""),key,value);
        }else if(path.indexOf("HKEY_LOCAL_MACHINE")!=-1) {
            Advapi32Util.registrySetStringValue(WinReg.HKEY_LOCAL_MACHINE,path.replace("HKEY_LOCAL_MACHINE\\",""),key,value);
        }else if(path.indexOf("HKEY_USERS")!=-1) {
            Advapi32Util.registrySetStringValue(WinReg.HKEY_USERS,path.replace("HKEY_USERS\\",""),key,value);
        }else if(path.indexOf("HKEY_CURRENT_CONFIG")!=-1) {
            Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_CONFIG,path.replace("HKEY_CURRENT_CONFIG\\",""),key,value);
        }
    }
    public void ExpandableStringValue(String key,String value) {
        if(path.indexOf("HKEY_CLASSES_ROOT")!=-1) {
            Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_CLASSES_ROOT,path.replace("HKEY_CLASSES_ROOT\\",""),key,value);
        }else if(path.indexOf("HKEY_CURRENT_USER")!=-1) {
            Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_CURRENT_USER,path.replace("HKEY_CURRENT_USER\\",""),key,value);
        }else if(path.indexOf("HKEY_LOCAL_MACHINE")!=-1) {
            Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_LOCAL_MACHINE,path.replace("HKEY_LOCAL_MACHINE\\",""),key,value);
        }else if(path.indexOf("HKEY_USERS")!=-1) {
            Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_USERS,path.replace("HKEY_USERS\\",""),key,value);
        }else if(path.indexOf("HKEY_CURRENT_CONFIG")!=-1) {
            Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_CURRENT_CONFIG,path.replace("HKEY_CURRENT_CONFIG\\",""),key,value);
        }
    }
    public void LongValue(String key,String value) {
        if(path.indexOf("HKEY_CLASSES_ROOT")!=-1) {
            Advapi32Util.registrySetLongValue(WinReg.HKEY_CLASSES_ROOT,path.replace("HKEY_CLASSES_ROOT\\",""),key,Long.parseLong(value));
        }else if(path.indexOf("HKEY_CURRENT_USER")!=-1) {
            Advapi32Util.registrySetLongValue(WinReg.HKEY_CURRENT_USER,path.replace("HKEY_CURRENT_USER\\",""),key,Long.parseLong(value));
        }else if(path.indexOf("HKEY_LOCAL_MACHINE")!=-1) {
            Advapi32Util.registrySetLongValue(WinReg.HKEY_LOCAL_MACHINE,path.replace("HKEY_LOCAL_MACHINE\\",""),key,Long.parseLong(value));
        }else if(path.indexOf("HKEY_USERS")!=-1) {
            Advapi32Util.registrySetLongValue(WinReg.HKEY_USERS,path.replace("HKEY_USERS\\",""),key,Long.parseLong(value));
        }else if(path.indexOf("HKEY_CURRENT_CONFIG")!=-1) {
            Advapi32Util.registrySetLongValue(WinReg.HKEY_CURRENT_CONFIG,path.replace("HKEY_CURRENT_CONFIG\\",""),key,Long.parseLong(value));
        }
    }
}