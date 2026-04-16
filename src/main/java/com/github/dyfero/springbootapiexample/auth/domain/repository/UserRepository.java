package com.github.dyfero.springbootapiexample.auth.domain.repository;

import com.github.dyfero.springbootapiexample.auth.domain.model.EmailAddress;
import com.github.dyfero.springbootapiexample.auth.domain.model.User;
import io.vavr.control.Option;

public interface UserRepository {

    Option<User> findByEmail(EmailAddress email);

    Option<User> findUserById(Long userId);
}
