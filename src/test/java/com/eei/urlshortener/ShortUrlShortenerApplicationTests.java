package com.eei.urlshortener;

import com.eei.urlshortener.data.response.AuthenticationResponse;
import com.eei.urlshortener.data.response.ShortenerResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@AutoConfigureWebTestClient(timeout = "36000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShortUrlShortenerApplicationTests {

  @Autowired private WebTestClient webTestClient;

  @LocalServerPort private int port;

  @Test
  @Order(1)
  void health() {
    final String url = "http://localhost:" + port + "/rest/health-check";

    webTestClient.get().uri(url).exchange().expectStatus().is2xxSuccessful();
  }

  @Test
  @Order(2)
  void signUpAndSignIn() {
    final String signUpUrl = "http://localhost:" + port + "/rest/auth/sign-up";
    final String body = "{\"username\": \"erincevrim@test.com\",\"password\": \"testtest\"}";
    webTestClient
        .post()
        .uri(signUpUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(body)
        .exchange()
        .expectStatus()
        .is2xxSuccessful();

    final String signInUrl = "http://localhost:" + port + "/rest/auth/sign-in";
    webTestClient
        .post()
        .uri(signInUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(body)
        .exchange()
        .expectStatus()
        .is2xxSuccessful();
  }

  @Test
  @Order(3)
  void checkUserName() {
    final String signUpUrl = "http://localhost:" + port + "/rest/auth/sign-up";
    final String body = "{\"username\": \"erincevrim@test.com\",\"password\": \"testtest\"}";
    webTestClient
            .post()
            .uri(signUpUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .exchange()
            .expectStatus()
            .is5xxServerError();
  }

  @Test
  @Order(4)
  void checkNotRegisteredUser() {
    final String signInUrl = "http://localhost:" + port + "/rest/auth/sign-in";
    final String body = "{\"username\": \"erincevrim2@test.com\",\"password\": \"testtest\"}";
    webTestClient
            .post()
            .uri(signInUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .exchange()
            .expectStatus()
            .is5xxServerError();
  }

  @Test
  @Order(5)
  void urlShortener() {
    final String signInUrl = "http://localhost:" + port + "/rest/auth/sign-in";
    final String body = "{\"username\": \"erincevrim@test.com\",\"password\": \"testtest\"}";
    EntityExchangeResult<AuthenticationResponse> response =
        webTestClient
            .post()
            .uri(signInUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody(AuthenticationResponse.class)
            .returnResult();

    String token = response.getResponseBody().getToken();

    final String shortenerUrl = "http://localhost:" + port + "/r";
    final String shortenerBody = "{   \"url\": \"https://www.google.com\"}";
    EntityExchangeResult<ShortenerResponse> shortenerResponse =
        webTestClient
            .post()
            .uri(shortenerUrl)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(shortenerBody)
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody(ShortenerResponse.class)
            .returnResult();

    String url = shortenerResponse.getResponseBody().getShortenUrl();

    webTestClient
        .get()
        .uri(url)
        .header("Authorization", "Bearer " + token)
        .exchange()
        .expectStatus()
        .is3xxRedirection();
  }

  @Test
  @Order(6)
  void checkNotInsertedUrl() {
    final String signInUrl = "http://localhost:" + port + "/rest/auth/sign-in";
    final String body = "{\"username\": \"erincevrim@test.com\",\"password\": \"testtest\"}";
    EntityExchangeResult<AuthenticationResponse> response =
            webTestClient
                    .post()
                    .uri(signInUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .exchange()
                    .expectStatus()
                    .is2xxSuccessful()
                    .expectBody(AuthenticationResponse.class)
                    .returnResult();

    String token = response.getResponseBody().getToken();

    final String shortenUrl = "http://localhost:" + port + "/r/test";

    webTestClient
            .get()
            .uri(shortenUrl)
            .header("Authorization", "Bearer " + token)
            .exchange()
            .expectStatus()
            .is5xxServerError();
  }


}
