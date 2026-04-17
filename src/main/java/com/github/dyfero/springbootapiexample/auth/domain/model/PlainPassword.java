package com.github.dyfero.springbootapiexample.auth.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.regex.Pattern;

@Getter
@ToString
@EqualsAndHashCode(of = {"value"})
public final class PlainPassword {

    private static final Pattern REGEX = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$");

    private final String value;

    private PlainPassword(String value) {
        this.value = value;
    }

    public static PlainPassword fromPlainText(String plain) {
        if (!REGEX.matcher(plain).matches()) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character."
            );
        }
        return new PlainPassword(plain);
    }
}
