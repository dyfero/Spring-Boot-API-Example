package com.github.dyfero.springbootapiexample;

import com.github.dyfero.springbootapiexample.auth.infrastructure.configuration.JwtTokenProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({JwtTokenProperties.class})
@SpringBootApplication
public class SpringBootApiExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApiExampleApplication.class, args);
    }
}
