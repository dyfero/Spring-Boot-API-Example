package com.github.dyfero.springbootapiexample.auth.adapters.rest;

import com.github.dyfero.springbootapiexample.support.RestApiIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.github.dyfero.springbootapiexample.core.Error.INVALID_CREDENTIALS_ERROR;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class AuthApiTest extends RestApiIntegrationTest {

    @Test
    void loginShouldReturnAccessToken() {
        given()
                .contentType(ContentType.JSON)
                .body(validCredentials())
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    @Test
    void loginShouldReturnProblemDetailForInvalidCredentials() {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "email", "john@example.com",
                        "password", "Invalid1!"
                ))
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(401)
                .body("title", equalTo("Unauthorized"))
                .body("status", equalTo(401))
                .body("detail", equalTo(INVALID_CREDENTIALS_ERROR.getMessage()))
                .body("code", equalTo(INVALID_CREDENTIALS_ERROR.getCode()));
    }

    private Map<String, String> validCredentials() {
        return Map.of(
                "email", "john@example.com",
                "password", "Passw0rd!"
        );
    }
}
