package me.server.receive;

import me.server.LoadConfig.ConfigReader;
import me.server.LoadConfig.ConfigWriter;
import oshi.SystemInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

public class GetCamera {
    public static BufferedImage trans(DataInputStream dataInputStream, ArrayList<Integer> arrayList) {
        BufferedImage image = null;
        try {
            final int QCIF = 176;
            final int CIF = 352;
            final int D1 = 704;
            final int _720P = 1280;
            final int _1080P = 1920;
            final int OTHER = 160;
                int w = 0;
                int h = 0;
                int i1 = dataInputStream.readInt();
                int i2 = dataInputStream.readInt();
                if (i1 == QCIF || i2 == QCIF) {
                    w = 176;
                    h = 144;
                }else if (i1 == OTHER|| i2 == OTHER) {
                    w = 160;
                    h = 120;
                } else if (i1 == CIF|| i2 == CIF) {
                    w = 352;
                    h = 288;
                } else if (i1 == D1|| i2 == D1) {
                    w = 704;
                    h = 576;
                }else if (i1 == _720P|| i2 == _720P) {
                    w = 1280;
                    h = 720;
                }else if (i1 == _1080P|| i2 == _1080P) {
                    w = 1920;
                    h = 1080;
                }

                if(arrayList.isEmpty()) {
                    arrayList.add(w);
                    arrayList.add(h);
                }
                if(arrayList.get(0) == QCIF && arrayList.get(0) == CIF &&
                        arrayList.get(0) == D1 && arrayList.get(0) == _720P
                        && arrayList.get(0) == _1080P && arrayList.get(0) == OTHER) {
                    JOptionPane.showMessageDialog(null,"未检测到摄像头或摄像头出现错误","错误",JOptionPane.ERROR_MESSAGE);
                }
            image = new BufferedImage(arrayList.get(0), arrayList.get(1), BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            for (int i = 0; i < arrayList.get(1); i++) {
                for (int j = 0; j < arrayList.get(0); j++) {
                    int a = dataInputStream.readInt();
                    Color color = new Color(a);
                    g.setColor(color);
                    g.drawLine(j, i, j, i);
                }
            }
        } catch (IOException e) {
        }
        return image;
    }
}
