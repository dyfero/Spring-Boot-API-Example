package com.github.dyfero.springbootapiexample.auth.domain.support;

import java.time.Duration;

public interface TokenProperties {

    Duration getAccessTokenTtl();

    String getIssuer();
}
