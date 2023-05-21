package me.client.send;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import me.client.utils.LoadDLL;
import me.client.utils.LoadUser32;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.UsbDevice;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;

public class SystemManager {
    Socket socket;
        public SystemManager(Socket socket){
            this.socket = socket;
        }
        public void showTaskList() {
            SendMessage.sendHead(MessageFlags.SHOW_TASKLIST,socket);
        }
    public void updateTaskList() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem os = systemInfo.getOperatingSystem();
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        List<OSProcess> list = os.getProcesses();
        try {
            for (OSProcess p : list) {
                String context = String.format(" %s|%5.1f|%4.1f|%9s|%9s|%5d|%s", p.getName(),
                        100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                        100d * p.getResidentSetSize() / hal.getMemory().getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
                        FormatUtil.formatBytes(p.getResidentSetSize()), p.getProcessID(),p.getPath()) + "\n";
                SendMessage.send(MessageFlags.UPDATE_TASKLIST,context.getBytes( "GBK"),socket);
            }
        }catch (Exception e) {
        }
    }
    public void enumWindows() {
        IntByReference intByReference = new IntByReference();
        Pointer pointer = Pointer.createConstant(0);
        User32.INSTANCE.EnumWindows((hWnd, p) -> {
            int length = User32.INSTANCE.GetWindowTextLength(hWnd);
            if (length == 0 || !User32.INSTANCE.IsWindowVisible(hWnd)) {
            } else {
                char[] lpString = new char[260];
                User32.INSTANCE.GetWindowText(hWnd, lpString, 260);
                String str =User32.INSTANCE.GetWindowThreadProcessId(hWnd,intByReference) +"|" + LoadDLL.instance.GetHWND(hWnd) + "|" + String.valueOf(lpString);
                try {
                    SendMessage.send(MessageFlags.ENUM_WINDOWS,str.getBytes("GBK"),socket);
                } catch (UnsupportedEncodingException e) {
                }
            }
            return true;
        }, pointer);
    }
    public String getCPUName(){
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        String str = hardwareAbstractionLayer.getProcessor().toString();
        return str.substring(0,str.indexOf("Hz") + 2);
    }
    public String getSystem() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem os = systemInfo.getOperatingSystem();
        return os.toString();
    }
    public String getDisk() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        HWDiskStore[] stores = hardwareAbstractionLayer.getDiskStores().toArray(new HWDiskStore[0]);
        String str = "";
        for(HWDiskStore store : stores) {
            str += store.getModel() + FormatUtil.formatBytesDecimal(store.getSize()) + "/" + FormatUtil.formatBytesDecimal(store.getReadBytes()) + "\n";
        }
        return str;
    }
    public String getManufacturer() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        return hardwareAbstractionLayer.getComputerSystem().getManufacturer();
    }
    public static List<UsbDevice> getUSB() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        return hardwareAbstractionLayer.getUsbDevices(true);
    }
    public void sendSystemInfo() throws UnsupportedEncodingException {
        SendMessage.send(MessageFlags.UPDATE_SYSTEMINFO,("操作系统|"+getSystem()).getBytes("GBK" ),socket);
        SendMessage.send(MessageFlags.UPDATE_SYSTEMINFO,("CPU|" + getCPUName()).getBytes("GBK" ),socket);
        SendMessage.send(MessageFlags.UPDATE_SYSTEMINFO,("硬盘|" + getDisk()).getBytes( "GBK"),socket);
        SendMessage.send(MessageFlags.UPDATE_SYSTEMINFO,("设备制造商|" + getManufacturer()).getBytes("GBK" ),socket);
        for(UsbDevice device:getUSB()) {
            SendMessage.send(MessageFlags.UPDATE_SYSTEMINFO,("USB|" + device.getName()).getBytes( "GBK"),socket);
        }
    }
    public static void stopProcess(int PID) {
        WinNT.HANDLE handle = Kernel32.INSTANCE.OpenProcess(WinNT.PROCESS_ALL_ACCESS,false,PID);
        Kernel32.INSTANCE.TerminateProcess(handle,0);
    }
    }