package me.client.send;

import me.client.Client;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;

public class SendSystemInfo{
    Socket socket;
    public SendSystemInfo(Socket socket) {
        this.socket = socket;
    }

    public String getCPUName(){
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        String str = hardwareAbstractionLayer.getProcessor().toString();
        return str.substring(0,str.indexOf("Hz") + 2);
    }

    public String getMemory() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        GlobalMemory memory = hardwareAbstractionLayer.getMemory();
        return FormatUtil.formatBytes(memory.getAvailable());
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
    public void sendSystemInfo() {
        SendMessage.SendHead(MessageFlags.SHOW_SYSTEMINFO,socket);
        String context = getCPUName() + "|" + getMemory() + "|" + getDisk();
        SendMessage.Send(MessageFlags.UPDATE_SYSTEMINFO,context.getBytes(),socket);
    }
}
