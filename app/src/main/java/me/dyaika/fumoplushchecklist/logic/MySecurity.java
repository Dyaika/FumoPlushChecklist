package me.dyaika.fumoplushchecklist.logic;

import android.util.Base64;

import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MySecurity {
    public static String encrypt(String text, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] keyBytes = key.getBytes("UTF-8");
            byte[] keyBytes16 = Arrays.copyOf(keyBytes, 16);
            Key key1 = new SecretKeySpec(keyBytes16, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key1);
            byte[] plainText = text.getBytes("UTF-8");
            byte[] cipherText = cipher.doFinal(plainText);
            String res = Base64.encodeToString(cipherText, Base64.DEFAULT);
            /*
            Log.d("cipher", res + "|");
            Log.d("cipher", decrypt(res, key));
             */
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return "WATAF";
        }
    }

    public static String decrypt(String text, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] keyBytes = key.getBytes("UTF-8");
            byte[] keyBytes16 = Arrays.copyOf(keyBytes, 16);
            Key key1 = new SecretKeySpec(keyBytes16, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key1);
            byte[] cipherText = Base64.decode(text, Base64.DEFAULT);
            byte[] plainText = cipher.doFinal(cipherText);
            return new String(plainText, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "FATAW";
        }
    }
}
