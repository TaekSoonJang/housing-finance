package com.jeanvar.housingfinance.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

@Slf4j
public class SecurityUtil {
    public static String generateHS256KeyHexString() {
        return DatatypeConverter.printHexBinary(
            Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded()
        );
    }

    public static SecretKey hexStringToHS256Key(String secretKeyHexString) {
        return Keys.hmacShaKeyFor(DatatypeConverter.parseHexBinary(secretKeyHexString));
    }

    public static boolean checkPassword(String plain, String encrypted) {
        try {
            return BCrypt.checkpw(plain, encrypted);
        } catch (IllegalArgumentException e) {
            log.info("Password check failed.", e);

            return false;
        }
    }

    public static String generatePassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
