package auth.integration;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.bayerl.auth.repository.UserRepository;
import org.bayerl.auth.security.PasswordEncrypter;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class AuthResourceIT {
  @Inject
  UserRepository repository;

  @Inject
  PasswordEncrypter passwordEncrypter;

  @TestHTTPResource("/auth/login")
  URL loginEndpoint;

  @Test
  public void successfulLoginTest() {
    String testPayload = """
        {
          "email": "test@user.com",
          "password": "password123"
        }
        """;

    given().
        contentType(ContentType.JSON).
        body(testPayload).
    when().
        post(loginEndpoint).
    then().
        statusCode(200).
        body("token", notNullValue());
  }

  @Test
  void invalidCredentialsLogin() {
    String testPayload = """
        {
          "email": "test@user.com",
          "password": "password321"
        }
        """;

    given().
        contentType(ContentType.JSON).
        body(testPayload).
    when().
        post(loginEndpoint).
    then().
        statusCode(401).
        body("error", notNullValue());
  }
}
