package onlineshop.spring.utils;

import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {

    private static final Logger hashUtilLogger = Logger.getLogger(HashUtil.class);

    public static String getSha256SecurePassword(String passwordToHash, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(hexToBytes(salt));
            byte[] encodedHash = digest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            hashUtilLogger.error("Problem with getting sha256 hash", e);
        }
        return passwordToHash;
    }

    public static String getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return bytesToHex(salt);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static byte[] hexToBytes(String hex) {
        char[] chars = hex.toCharArray();
        byte[] raw = new byte[chars.length / 2];
        for (int src = 0, dst = 0; dst < raw.length; ++dst) {
            int hi = Character.digit(chars[src++], 16);
            int lo = Character.digit(chars[src++], 16);
            if ((hi < 0) || (lo < 0))
                throw new IllegalArgumentException();
            raw[dst] = (byte) (hi << 4 | lo);
        }
        return raw;
    }
}
