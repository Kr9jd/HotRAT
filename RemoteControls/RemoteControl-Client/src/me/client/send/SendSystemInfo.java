package me.client.send;

import me.client.Client;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SendSystemInfo extends Thread{
    OutputStreamWriter writer;
    Socket socket;
    public SendSystemInfo(OutputStreamWriter writer, Socket socket) {
        this.writer =writer;
        this.socket = socket;
    }

    public String getCPUName() throws Exception{
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

    @Override
    public void run() {
        try {
            writer.write("CPU: " + getCPUName() + "\n");
            writer.write("Memory: " + getMemory() + "\n");
            writer.write("Disk: " + getDisk() + "\n");
            writer.write("end");
            writer.flush();
            System.out.println(getCPUName() + getMemory() + getDisk());
        } catch (Exception e) {
        }
    }
}
