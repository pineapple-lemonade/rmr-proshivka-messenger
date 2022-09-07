package ru.ruzavin.rmrproshivkamessenger.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.ruzavin.rmrproshivkamessenger.config.MockSecurityTestConfig;
import ru.ruzavin.rmrproshivkamessenger.config.TestConfig;
import ru.ruzavin.rmrproshivkamessenger.dto.model.UserModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.CreateUserRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SuccessResponse;
import ru.ruzavin.rmrproshivkamessenger.initializer.PostgresInitializer;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = PostgresInitializer.class)
@Import({TestConfig.class, MockSecurityTestConfig.class})
@Transactional
class UserIntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int port;

	private static final String HTTP_LOCALHOST_PREFIX = "http://localhost:";

	private static final String API_SIGN_UP = "/api/v1/user/sign-up";

	@Test
	void testUserSuccessfullyCreated() {
		CreateUserRequest request = CreateUserRequest.builder()
				.name("gosha")
				.password("Abc1234!")
				.phone("79961073183")
				.build();

		ResponseEntity<SuccessResponse<UserModel>> response = testRestTemplate.exchange(
				HTTP_LOCALHOST_PREFIX + port + API_SIGN_UP,
				HttpMethod.POST, new HttpEntity<>(request),
				new ParameterizedTypeReference<>() {}, Collections.emptyMap());

		Assertions.assertEquals(request.getName(), response.getBody().getData().getName());
		Assertions.assertEquals(request.getPhone(), response.getBody().getData().getPhone());
	}

}
