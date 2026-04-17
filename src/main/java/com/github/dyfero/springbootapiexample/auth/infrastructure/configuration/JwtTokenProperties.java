package com.github.dyfero.springbootapiexample.auth.infrastructure.configuration;

import com.github.dyfero.springbootapiexample.auth.domain.support.TokenProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@ConfigurationProperties(prefix = "jwt")
public class JwtTokenProperties implements TokenProperties {

    private final String secret;

    private final Duration accessTokenTtl;

    private final String issuer;

    public JwtTokenProperties(String secret, Duration accessTokenTtl, String issuer) {
        if (secret.getBytes(StandardCharsets.UTF_8).length < 64) {
            throw new IllegalArgumentException(
                    "JWT secret must be at least 64 bytes for HS512"
            );
        }
        this.secret = secret;
        this.accessTokenTtl = accessTokenTtl;
        this.issuer = issuer;
    }

    public SecretKey getSecretKey() {
        return new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8), "HmacSHA512"
        );
    }

    @Override
    public Duration getAccessTokenTtl() {
        return accessTokenTtl;
    }

    @Override
    public String getIssuer() {
        return issuer;
    }
}
