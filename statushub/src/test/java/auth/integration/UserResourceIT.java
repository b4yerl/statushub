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

@QuarkusTest
public class UserResourceIT {
  @Inject
  UserRepository repository;

  @Inject
  PasswordEncrypter encrypter;

  @TestHTTPResource("/user/register")
  URL registerUserEndpoint;

  @Test
  void shouldCreateUser() {
    String payload = """
      {
        "email": "success@test.com",
        "name": "Success",
        "password": "12345678"
      }
      """;

    given().
        contentType(ContentType.JSON).
        body(payload).
    when().
        post(registerUserEndpoint).
    then().
        statusCode(201);
  }
  @Test
  void shouldCreateUserWithDifferentEmail() {
    String payload = """
      {
        "email": "another_success@test.com",
        "name": "Success",
        "password": "12345678"
      }
      """;

    given().
        contentType(ContentType.JSON).
        body(payload).
        when().
        post(registerUserEndpoint).
        then().
        statusCode(201);
  }
  @Test
  void shouldThrowErrorWhenCreatingUser() {
    String payload = """
      {
        "email": "test@user.com",
        "name": "Success",
        "password": "12345678"
      }
      """;

    given().
        contentType(ContentType.JSON).
        body(payload).
        when().
        post(registerUserEndpoint).
        then().
        statusCode(400);
  }
}
