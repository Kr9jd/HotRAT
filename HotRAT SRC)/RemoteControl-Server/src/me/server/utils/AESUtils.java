package me.server.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESUtils {
    public static byte[] encryption(String str) throws Exception{
        String KeyString = LoadDLL.instance.GetKey().toString();
        byte[] key = KeyString.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec spec = new SecretKeySpec(key,"AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, spec);
        byte[] context = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encode(context);
    }
}