package com.github.dyfero.springbootapiexample.auth.domain.support;

import com.github.dyfero.springbootapiexample.auth.domain.model.Token;

import java.util.Set;

public interface AccessTokenIssuer {

    Token issue(Long subject, Set<String> scopes);
}
