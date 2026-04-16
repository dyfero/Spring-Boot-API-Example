package com.github.dyfero.springbootapiexample.auth.infrastructure.support;

import com.github.dyfero.springbootapiexample.auth.domain.support.AccessTokenIssuer;
import com.github.dyfero.springbootapiexample.auth.domain.support.TokenProperties;
import com.github.dyfero.springbootapiexample.auth.domain.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenIssuer implements AccessTokenIssuer {

    private static final String CLAIM_SCOPES = "scp";

    private final JwtEncoder jwtEncoder;

    private final TokenProperties tokenProperties;

    @Override
    public Token issue(Long subject, Set<String> scopes) {
        var issuedAt = Instant.now();
        var expiredAt = issuedAt.plus(tokenProperties.getAccessTokenTtl());

        var claims = JwtClaimsSet.builder()
                .subject(String.valueOf(subject))
                .issuer(tokenProperties.getIssuer())
                .issuedAt(issuedAt)
                .expiresAt(expiredAt)
                .claim(CLAIM_SCOPES, scopes)
                .id(UUID.randomUUID().toString())
                .build();

        var header = JwsHeader.with(MacAlgorithm.HS512).build();
        var jwt = jwtEncoder.encode(JwtEncoderParameters.from(header, claims));

        return new Token(jwt.getTokenValue());
    }
}
