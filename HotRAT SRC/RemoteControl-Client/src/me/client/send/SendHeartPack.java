package me.client.send;

import me.client.Client;
import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SendHeartPack extends Thread{
    private Socket socket;
    public SendHeartPack(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while(true) {
                Thread.sleep(5000);
                dataOutputStream.write(MessageFlags.HEARTPACK);
            }
        } catch (Exception var4) {
            try {
                this.socket.close();
                Client.isturn = false;
                Client.recon();
            } catch (Exception var3) {
            }
        }
    }
}