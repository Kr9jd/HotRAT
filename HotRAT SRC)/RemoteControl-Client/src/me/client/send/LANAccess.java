package me.client.send;

import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LANAccess extends Thread{
    Socket socket;
    public LANAccess(Socket socket) {
        this.socket = socket;
    }
    public void get(String url,String head) {
        try {
            String message = "";
            URL url1 = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            byte[] bytes = new byte[1024];
            int len = 0;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            String[] strings = head.split("\\|");
            for(String str : strings) {
                String[] heads = str.split("#");
                httpURLConnection.setRequestProperty(heads[0],heads[1]);
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            while ((len = inputStream.read(bytes))!=-1) {
                message += new String(bytes,0,len, StandardCharsets.UTF_8);
            }
            SendMessage.Send(MessageFlags.LAN_ACCESS_GET,message.getBytes(),socket);
        }catch (Exception e) {
            SendMessage.SendHead(MessageFlags.LAN_ACCESS_ERROR,socket);
        }
    }
    public void post(String url,String head,String text) {
        try {
            String message = "";
            URL url1 = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            byte[] bytes = new byte[1024];
            int len = 0;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            String[] strings = head.split("\\|");
            for(String str : strings) {
                String[] heads = str.split("#");
                httpURLConnection.setRequestProperty(heads[0],heads[1]);
            }
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(text.getBytes());
            outputStream.flush();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            while ((len = inputStream.read(bytes))!=-1) {
                message += new String(bytes,0,len, StandardCharsets.UTF_8);
            }
            SendMessage.Send(MessageFlags.LAN_ACCESS_POST,message.getBytes(),socket);
        }catch (Exception e) {
            SendMessage.SendHead(MessageFlags.LAN_ACCESS_ERROR,socket);
        }
    }
}