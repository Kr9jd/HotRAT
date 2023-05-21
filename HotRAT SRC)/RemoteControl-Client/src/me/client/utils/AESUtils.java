package me.client.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESUtils {
    public static byte[] decrypt(String str) {
        byte[] context = null;
        try {
            String KeyString = LoadDLL.instance.$Get$Key().toString();
            byte[] bytes = KeyString.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec spec = new SecretKeySpec(bytes,"AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,spec);
            byte[] base64Context = Base64.getDecoder().decode(str);
            context = cipher.doFinal(base64Context);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }
}
