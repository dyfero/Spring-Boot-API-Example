package com.github.dyfero.springbootapiexample.auth.adapters.rest;

import com.github.dyfero.springbootapiexample.support.RestApiIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

class AccountApiTest extends RestApiIntegrationTest {

    @Test
    void getAccountShouldRequireAuthenticationWhenAuthorizationHeaderIsMissing() {
        given()
                .when()
                .get("/api/account")
                .then()
                .statusCode(401);
    }

    @Test
    void getAccountShouldRequireAuthenticationWhenTokenIsInvalid() {
        given()
                .auth()
                .oauth2("invalid-token")
                .when()
                .get("/api/account")
                .then()
                .statusCode(401);
    }

    @Test
    void getAccountShouldReturnAuthenticatedUserAfterLogin() {
        String accessToken = loginAndExtractAccessToken();

        given()
                .auth()
                .oauth2(accessToken)
                .when()
                .get("/api/account")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1001))
                .body("name", equalTo("John Doe"))
                .body("email", equalTo("john@example.com"));
    }

    private String loginAndExtractAccessToken() {
        return given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "email", "john@example.com",
                        "password", "Passw0rd!"
                ))
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getString("accessToken");
    }
}
