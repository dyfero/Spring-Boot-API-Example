package com.github.dyfero.springbootapiexample.auth.domain.support;

import com.github.dyfero.springbootapiexample.auth.domain.model.PlainPassword;

public interface PasswordVerifier {

    boolean matches(PlainPassword plain, String hashedPassword);
}
