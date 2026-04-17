package com.github.dyfero.springbootapiexample.auth.domain.support;

import com.github.dyfero.springbootapiexample.auth.domain.model.Authentication;
import io.vavr.control.Option;

public interface AuthenticationProvider {

    Option<Authentication<Long>> authentication();
}
