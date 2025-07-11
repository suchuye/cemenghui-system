package cn.neu.edu.cemenghuisystem.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class SHA256Util {
    private static final String FIXED_KEY = "0123456789ABCDEF0123456789ABCDEF";
    private static final String ALGORITHM = "AES";
    private SecretKey secretKey;

    public SHA256Util() throws Exception {
        try {
            byte[] keyBytes = FIXED_KEY.getBytes(StandardCharsets.UTF_8);
            this.secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException("密钥初始化失败", e);
        }
    }

    public SHA256Util(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        this.secretKey = new javax.crypto.spec.SecretKeySpec(keyBytes, ALGORITHM);
    }

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public String getKeyAsString() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) {
        try {
            // 示例：生成新密钥并加密
            SHA256Util encryptor = new SHA256Util();
            String secretKeyString = encryptor.getKeyAsString();
            System.out.println("生成的密钥: " + secretKeyString);

            String originalString = "111";
            String encryptedString = encryptor.encrypt(originalString);
            System.out.println("加密后的字符串: " + encryptedString);

            // 使用相同密钥解密
            SHA256Util decryptor = new SHA256Util(secretKeyString);
            String decryptedString = decryptor.decrypt(encryptedString);
            System.out.println("解密后的字符串: " + decryptedString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
