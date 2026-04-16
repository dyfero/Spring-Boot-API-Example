package com.github.dyfero.springbootapiexample.auth.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.regex.Pattern;

@Getter
@ToString
@EqualsAndHashCode(of = {"value"})
public final class EmailAddress {

    private static final Pattern REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);

    private final String value;

    private EmailAddress(String value) {
        this.value = value;
    }

    public static EmailAddress fromString(String email) {
        var trimmed = email.trim();

        if (trimmed.isEmpty() || !REGEX.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("Email address is not valid.");
        }

        return new EmailAddress(trimmed);
    }
}
