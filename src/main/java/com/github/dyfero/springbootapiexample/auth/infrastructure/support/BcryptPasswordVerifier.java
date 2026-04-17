package com.github.dyfero.springbootapiexample.auth.infrastructure.support;

import com.github.dyfero.springbootapiexample.auth.domain.support.PasswordVerifier;
import com.github.dyfero.springbootapiexample.auth.domain.model.PlainPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BcryptPasswordVerifier implements PasswordVerifier {

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean matches(PlainPassword plain, String hashedPassword) {
        return passwordEncoder.matches(plain.getValue(), hashedPassword);
    }
}
