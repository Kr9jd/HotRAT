package me.client.send;

import me.client.Client;
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

public class SendTaskList extends Thread{
    OutputStreamWriter writer;
    Socket socket;
        public SendTaskList(OutputStreamWriter writer,Socket socket){
            this.writer = writer;
            this.socket = socket;
        }
        @Override
        public void run() {
            SystemInfo systemInfo = new SystemInfo();
            OperatingSystem os = systemInfo.getOperatingSystem();
            HardwareAbstractionLayer hal = systemInfo.getHardware();
            List<OSProcess> list = os.getProcesses();
            try {
                for (OSProcess p : list) {
                    writer.write(String.format(" %s|%5.1f|%4.1f|%9s|%9s|%5d", p.getName(),
                            100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                            100d * p.getResidentSetSize() / hal.getMemory().getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
                            FormatUtil.formatBytes(p.getResidentSetSize()), p.getProcessID()) + "\n");
                    writer.flush();
                }
                writer.write("end");
                writer.flush();
            }catch (Exception e) {
            }
        }
    }
