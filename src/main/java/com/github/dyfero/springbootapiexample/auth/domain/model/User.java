package com.github.dyfero.springbootapiexample.auth.domain.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {

    private final Long id;

    private final String name;

    private final EmailAddress email;

    private final String passwordHash;

    public User(Long id, String name, String email, String passwordHash) {
        this.id = id;
        this.name = name;
        this.email = EmailAddress.fromString(email);
        this.passwordHash = passwordHash;
    }
}
