package com.github.dyfero.springbootapiexample.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class Authentication<T> {

    private final T subject;

    private final Boolean isAuthenticated;

    private final Set<String> authorities;
}
