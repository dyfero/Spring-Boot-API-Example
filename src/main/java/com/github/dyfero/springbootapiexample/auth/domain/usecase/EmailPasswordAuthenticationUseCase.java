package com.github.dyfero.springbootapiexample.auth.domain.usecase;

import com.github.dyfero.springbootapiexample.auth.domain.model.*;
import com.github.dyfero.springbootapiexample.auth.domain.repository.UserRepository;
import com.github.dyfero.springbootapiexample.auth.domain.support.AccessTokenIssuer;
import com.github.dyfero.springbootapiexample.auth.domain.support.PasswordVerifier;
import com.github.dyfero.springbootapiexample.core.Error;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmailPasswordAuthenticationUseCase {

    private final UserRepository userRepository;

    private final PasswordVerifier passwordVerifier;

    private final AccessTokenIssuer accessTokenIssuer;

    public Either<Error, Token> execute(String email, String plainPassword) {
        return parseCredentials(email, plainPassword)
                .flatMap(credentials -> getUser(credentials.email())
                        .flatMap(user -> authenticate(user, credentials.password())))
                .flatMap(this::issueToken);
    }

    private Either<Error, Credentials> parseCredentials(String email, String plainPassword) {
        return Try.of(() -> new Credentials(
                        EmailAddress.fromString(email),
                        PlainPassword.fromPlainText(plainPassword)
                ))
                .toEither()
                .mapLeft(_ -> Error.INVALID_CREDENTIALS_ERROR);
    }

    private Either<Error, User> getUser(EmailAddress emailAddress) {
        return userRepository.findByEmail(emailAddress)
                .toEither(Error.INVALID_CREDENTIALS_ERROR);
    }

    private Either<Error, User> authenticate(User user, PlainPassword plainPassword) {
        if (!passwordVerifier.matches(plainPassword, user.getPasswordHash())) {
            return Either.left(Error.INVALID_CREDENTIALS_ERROR);
        }

        return Either.right(user);
    }

    private Either<Error, Token> issueToken(User user) {
        return Try.of(() -> accessTokenIssuer.issue(user.getId(), Set.of("user"))) // predefined user authority
                .toEither()
                .mapLeft(e -> Error.AUTHENTICATION_ERROR);
    }

    private record Credentials(EmailAddress email, PlainPassword password) {
    }
}
