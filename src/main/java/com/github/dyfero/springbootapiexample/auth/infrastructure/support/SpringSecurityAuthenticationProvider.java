package com.github.dyfero.springbootapiexample.auth.infrastructure.support;

import com.github.dyfero.springbootapiexample.auth.domain.model.Authentication;
import com.github.dyfero.springbootapiexample.auth.domain.support.AuthenticationProvider;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SpringSecurityAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Option<Authentication<Long>> authentication() {
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Option.none();
        }

        if (authentication instanceof JwtAuthenticationToken jwtAuthentication) {
            return authentication(jwtAuthentication);
        }

        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return authentication(jwt);
        }

        return Option.none();
    }


    private Option<Long> parseUserId(String subject) {
        return Try.of(() -> Long.valueOf(subject)).toOption();
    }

    private Option<Authentication<Long>> authentication(JwtAuthenticationToken jwtAuthenticationToken) {
        return parseUserId(jwtAuthenticationToken.getToken().getSubject())
                .map(subject -> Authentication.<Long>builder()
                        .isAuthenticated(true)
                        .subject(subject)
                        .authorities(jwtAuthenticationToken.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                        .build()
                );
    }

    private Option<Authentication<Long>> authentication(Jwt jwt) {
        return parseUserId(jwt.getSubject())
                .map(subject -> Authentication.<Long>builder()
                        .isAuthenticated(true)
                        .subject(subject)
                        .authorities(jwt.getClaim("scp"))
                        .build()
                );
    }
}
