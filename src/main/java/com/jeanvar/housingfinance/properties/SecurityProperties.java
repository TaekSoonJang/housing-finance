package com.jeanvar.housingfinance.properties;

import com.jeanvar.housingfinance.util.SecurityUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@PropertySource("classpath:secret.properties")
@ConfigurationProperties("jwt")
public class SecurityProperties {
    private String secretKey = SecurityUtil.generateHS256KeyHexString();

    public SecretKey getSecret() {
        return SecurityUtil.hexStringToHS256Key(this.secretKey);
    }
}
