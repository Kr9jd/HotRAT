package me.server.utils;

import me.server.Server;

import javax.sound.sampled.*;
import java.io.File;
import java.io.InputStream;

public class PlayMusic {
    public static void online() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        AudioInputStream am;
                        am = AudioSystem.getAudioInputStream(new File(System.getProperty("user.dir") + "\\Music\\" + "online.wav"));
                        AudioFormat af = am.getFormat();
                        SourceDataLine sd;
                        sd = AudioSystem.getSourceDataLine(af);
                        sd.open();
                        sd.start();
                        int sumByteRead = 0;
                        byte[] b = new byte[320];
                        while (sumByteRead != -1) {
                            sumByteRead = am.read(b, 0, b.length);
                            if (sumByteRead >= 0) {
                                sd.write(b, 0, b.length);

                            }
                        }
                        sd.drain();
                        sd.close();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }
    public static void offline() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AudioInputStream am;
                    am = AudioSystem.getAudioInputStream(new File(System.getProperty("user.dir") + "\\Music\\" + "offline.wav"));
                    AudioFormat af = am.getFormat();
                    SourceDataLine sd;
                    sd = AudioSystem.getSourceDataLine(af);
                    sd.open();
                    sd.start();
                    int sumByteRead = 0;
                    byte[] b = new byte[320];
                    while (sumByteRead != -1) {
                        sumByteRead = am.read(b, 0, b.length);
                        if (sumByteRead >= 0) {
                            sd.write(b, 0, b.length);

                        }
                    }
                    sd.drain();
                    sd.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
