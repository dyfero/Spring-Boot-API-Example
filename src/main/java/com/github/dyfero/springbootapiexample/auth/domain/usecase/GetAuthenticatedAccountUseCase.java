package com.github.dyfero.springbootapiexample.auth.domain.usecase;

import com.github.dyfero.springbootapiexample.auth.domain.model.Authentication;
import com.github.dyfero.springbootapiexample.auth.domain.support.AuthenticationProvider;
import com.github.dyfero.springbootapiexample.auth.domain.model.User;
import com.github.dyfero.springbootapiexample.auth.domain.repository.UserRepository;
import com.github.dyfero.springbootapiexample.core.Error;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAuthenticatedAccountUseCase {

    private final AuthenticationProvider authenticationProvider;

    private final UserRepository userRepository;

    public Either<Error, User> execute() {
        return getAuthentication()
                .flatMap(auth -> getUser(auth.getSubject()));
    }

    private Either<Error, Authentication<Long>> getAuthentication() {
        return authenticationProvider.authentication()
                .toEither(Error.AUTHENTICATION_ERROR);
    }

    private Either<Error, User> getUser(Long userId) {
        return userRepository.findUserById(userId)
                .toEither(Error.AUTHENTICATION_ERROR);
    }
}
