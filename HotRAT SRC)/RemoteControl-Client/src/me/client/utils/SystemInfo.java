package me.client.utils;

import com.github.sarxos.webcam.Webcam;
import com.sun.jna.platform.win32.Kernel32;

public class SystemInfo {
    public static String getCurrentPID() {
        return Kernel32.INSTANCE.GetCurrentProcessId() + "";
    }
    public static String camera() {
        String cameraName = "";
        try {
            Webcam webcam = Webcam.getDefault();
            cameraName = webcam.getName();
        }catch (Exception e) {
            cameraName = "无";
        }
        return cameraName;
    }
}