package me.client.send;

import me.client.Client;

import java.io.DataOutputStream;
import java.net.Socket;

public class SendHeartPack implements Runnable{
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private static final String PACK = "";
    public static boolean run = false;

    public SendHeartPack(Socket socket, DataOutputStream dataOutputStream) {
        this.socket = socket;
        this.dataOutputStream = dataOutputStream;
    }

    public void run() {
        try {
            while(run) {
                Thread.sleep(5000L);
                this.dataOutputStream.writeUTF(PACK);
                this.dataOutputStream.flush();
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
