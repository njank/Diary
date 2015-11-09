package utils;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {

    public static String md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static byte[] encrypt(final String key, final String message) {
        final byte[] rawData = key.getBytes(Charset.forName("US-ASCII"));
        if (rawData.length != 16)
            return null;

        final SecretKeySpec seckeySpec = new SecretKeySpec(rawData, "AES");
        
        try{
            final Cipher ciph = Cipher.getInstance("AES/CBC/PKCS5Padding");
            ciph.init(Cipher.ENCRYPT_MODE, seckeySpec, new IvParameterSpec(new byte[16]));
            return ciph.doFinal(message.getBytes(Charset.forName("US-ASCII")));
        } catch (GeneralSecurityException e) {
            return null;
        }
    }

    public static String decrypt(String key, byte[] encrypted) {
        final byte[] rawData = key.getBytes(Charset.forName("US-ASCII"));
        if (rawData.length != 16)
            return null;

        final SecretKeySpec seckeySpec = new SecretKeySpec(rawData, "AES");
        
        try{
            final Cipher ciph = Cipher.getInstance("AES/CBC/PKCS5Padding");
            ciph.init(Cipher.DECRYPT_MODE, seckeySpec, new IvParameterSpec(new byte[16]));
            final byte[] decryptedmess = ciph.doFinal(encrypted);
            return new String(decryptedmess, Charset.forName("US-ASCII"));
        } catch (GeneralSecurityException e) {
            return null;
        }
    }
}