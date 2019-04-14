package com.jeanvar.housingfinance.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

public class SecurityUtil {
    public static String generateHS256KeyHexString() {
        return DatatypeConverter.printHexBinary(
            Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded()
        );
    }

    public static SecretKey hexStringToHS256Key(String secretKeyHexString) {
        return Keys.hmacShaKeyFor(DatatypeConverter.parseHexBinary(secretKeyHexString));
    }
}
