package com.github.dyfero.springbootapiexample.auth.adapters.rest.controllers;

import com.github.dyfero.springbootapiexample.auth.adapters.rest.controllers.data.AccessTokenResponse;
import com.github.dyfero.springbootapiexample.auth.domain.usecase.EmailPasswordAuthenticationUseCase;
import com.github.dyfero.springbootapiexample.core.DomainException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final EmailPasswordAuthenticationUseCase emailPasswordAuthenticationUseCase;

    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccessTokenResponse.class)
                    )
            )
    })
    public AccessTokenResponse login(@Valid @RequestBody LoginRequest request) {
        return emailPasswordAuthenticationUseCase.execute(request.email(), request.password())
                .map(token -> new AccessTokenResponse(token.accessToken()))
                .getOrElseThrow(DomainException::new);
    }

    public record LoginRequest(
            @NotBlank @Email
            String email,
            @NotBlank
            String password
    ) {
    }
}
