package me.server.utils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipUtils {
    public static byte[] compress(byte[] context) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(arrayOutputStream);
            gzipOutputStream.write(context);
            gzipOutputStream.close();
        }catch (Exception e) {
        }
        return arrayOutputStream.toByteArray();
    }
    public static byte[] decompression(byte[] context) {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(context);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(arrayInputStream);
            byte[] bytes = new byte[1024];
            int len =0;
            while ((len = gzipInputStream.read(bytes))!=-1) {
                byteArrayOutputStream.write(bytes,0,len);
            }
        }catch (Exception e) {
        }
        return byteArrayOutputStream.toByteArray();
    }
}
