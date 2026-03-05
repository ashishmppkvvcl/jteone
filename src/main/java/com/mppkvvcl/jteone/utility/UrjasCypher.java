package com.mppkvvcl.jteone.utility;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * This Class should be used for encryption / decryption
 * of consumer number and token before passing to URJAS API's
 * <p>
 * Note: Shared by Vibhor Patidar
 */
public class UrjasCypher {
    private static final Logger log = LoggerFactory.getLogger(UrjasCypher.class);

    public static final String[] RANDOM_TOKENS = {"ngb", "mpwz", "urjas", "mis", "win", "pmr", "naljal", "sms", "sybase", "rms"};

    public static final String ENCRYPT_CYPHER = "encrypt_cypher";
    public static final String DECRYPT_CYPHER = "decrypt_cypher";
    private static String SALT = "SALT_VALUE";
    private static final String URJAS_IV = System.getenv("URJAS_CYPHER_IV");
    private static final String URJAS_PASSWORD = System.getenv("URJAS_CYPHER_PASSWORD");
    private static Map<String, Cipher> cyphers = new HashMap<>();

    public static String encryptAndEncode(String raw) {
        if (StringUtils.isEmpty(raw)) return null;
        if (StringUtils.isEmpty(URJAS_IV) || StringUtils.isEmpty(URJAS_PASSWORD)) {
            log.error("Cypher IV and Password is not configured");
            return null;
        }

        try {
            final String key = ENCRYPT_CYPHER;
            if (cyphers.get(key) == null) {
                final Cipher encryptCypher = getCipher();
                cyphers.put(key, encryptCypher);
                log.info("Env: {}, Enc: {}, {}", key, encryptCypher, cyphers.keySet());
            }
            byte[] encryptedVal = cyphers.get(key).doFinal(raw.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.encodeBase64(encryptedVal), StandardCharsets.UTF_8);
        } catch (Exception exception) {
            log.error("Exception:", exception);
            throw new RuntimeException(exception);
        }
    }

    private static Cipher getCipher() throws Exception {
        final Cipher cypher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        final byte[] iv = URJAS_IV.getBytes(StandardCharsets.UTF_8);
        cypher.init(Cipher.ENCRYPT_MODE, generateKey(), new IvParameterSpec(iv));
        return cypher;
    }

    private static Key generateKey() throws Exception {
        final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        final char[] password = URJAS_PASSWORD.toCharArray();
        final byte[] salt = SALT.getBytes(StandardCharsets.UTF_8);
        final KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
        final byte[] encoded = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(encoded, "AES");
    }
}
