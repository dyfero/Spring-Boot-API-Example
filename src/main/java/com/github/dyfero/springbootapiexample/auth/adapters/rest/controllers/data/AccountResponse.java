package com.github.dyfero.springbootapiexample.auth.adapters.rest.controllers.data;

public record AccountResponse(
        Long id,
        String name,
        String email
) {
}
