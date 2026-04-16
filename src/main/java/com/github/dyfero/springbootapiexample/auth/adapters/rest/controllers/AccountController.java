package com.github.dyfero.springbootapiexample.auth.adapters.rest.controllers;

import com.github.dyfero.springbootapiexample.auth.adapters.rest.controllers.data.AccountResponse;
import com.github.dyfero.springbootapiexample.auth.domain.usecase.GetAuthenticatedAccountUseCase;
import com.github.dyfero.springbootapiexample.auth.infrastructure.configuration.OpenApiConfig;
import com.github.dyfero.springbootapiexample.core.DomainException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
@Tag(name = "Account")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class AccountController {

    private final GetAuthenticatedAccountUseCase getAuthenticatedAccountUseCase;

    @GetMapping
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountResponse.class)
                    )
            )
    })
    public AccountResponse getAccount() {
        return getAuthenticatedAccountUseCase.execute()
                .map(user -> new AccountResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail().getValue()
                ))
                .getOrElseThrow(DomainException::new);
    }
}
