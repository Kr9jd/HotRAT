package me.client.utils;

import javax.swing.*;
import java.awt.*;

public class FlashBomb {
    JDialog frame;
    JPanel panel;
    Color colors[] = {  Color.GREEN,Color.red,Color.blue,Color.MAGENTA,Color.magenta};
    int i;
    Image image;
    public FlashBomb(String msg) {
        final int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        final int height=Toolkit.getDefaultToolkit().getScreenSize().height;
        frame = new JDialog();
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.setBounds(0,0,width,height);
        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                if(image == null){
                    image=this.createImage(width, height);
                }
                Graphics gg=image.getGraphics();
                gg.setFont(new Font(null, Font.PLAIN, 50));
                gg.setColor(colors[i]);
                gg.fillRect(0, 0, width, height);
                gg.setColor(Color.black);
                gg.drawString(msg, width/2, height/2);
                g.drawImage(image, 0, 0, null);
            }
        };
        frame.setContentPane(panel);
        frame.setVisible(true);
        new Thread(() -> {
            int time=0;
            while (i < colors.length) {
                FlashBomb.this.Update();
                try {
                    Thread.sleep(60);
                    time++;
                    if(time==100){
                        frame.dispose();
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void Update() {
        int len = colors.length -1;
        if (i == len) {
            i = 0;
        } else {
            i++;
        }
        panel.repaint();
    }
}
