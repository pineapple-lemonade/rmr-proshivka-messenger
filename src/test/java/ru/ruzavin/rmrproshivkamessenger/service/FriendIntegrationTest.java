package ru.ruzavin.rmrproshivkamessenger.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.ruzavin.rmrproshivkamessenger.config.TestConfig;
import ru.ruzavin.rmrproshivkamessenger.dto.model.ChatModel;
import ru.ruzavin.rmrproshivkamessenger.dto.model.TokenPairModel;
import ru.ruzavin.rmrproshivkamessenger.dto.model.UserModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.AddFriendRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.request.LoginRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.PageResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SuccessResponse;
import ru.ruzavin.rmrproshivkamessenger.entity.enums.ChatType;
import ru.ruzavin.rmrproshivkamessenger.initializer.PostgresInitializer;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = PostgresInitializer.class)
@Import({TestConfig.class})
@TestPropertySource(properties = "spring.security.enabled=true")
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/scripts/insert-data-for-friend-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class FriendIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String HTTP_LOCALHOST_PREFIX = "http://localhost:";

	private static final String API_FRIEND = "/api/v1/friend";

	private static final String API_LOGIN = "/api/v1/login";

	private static final String TOKEN_PREFIX = "Bearer ";

	@Test
	void testFriendsListSuccessfullyReceived() throws Exception {
		LoginRequest loginRequest = LoginRequest.builder()
				.phone("79961073184")
				.password("123")
				.smsCode("1111")
				.name("gosha")
				.build();
		TokenPairModel tokenPairModel = objectMapper.readValue(mockMvc.perform(post(
						HTTP_LOCALHOST_PREFIX + port + API_LOGIN).contentType("application/json")
						.content(objectMapper.writeValueAsString(loginRequest))).andReturn().getResponse().getContentAsString(),
				TokenPairModel.class);

		PageResponse<List<UserModel>> response = objectMapper.readValue(mockMvc.perform(get(
						HTTP_LOCALHOST_PREFIX + port + API_FRIEND)
						.header("Authorization", TOKEN_PREFIX + tokenPairModel.getAccessToken()))
				.andReturn().getResponse().getContentAsString(), new TypeReference<>() {
		});

		Assertions.assertEquals("79961073185", response.getData().get(0).getPhone());
		Assertions.assertEquals(HttpStatus.OK, response.getStatus());
		Assertions.assertEquals("successfully received friends list", response.getMessage());
	}

	@Test
	void testFriendSuccessfullyAdded() throws Exception {
		LoginRequest loginRequest = LoginRequest.builder()
				.phone("79961073184")
				.password("123")
				.smsCode("1111")
				.name("gosha")
				.build();
		TokenPairModel tokenPairModel = objectMapper.readValue(mockMvc.perform(post(
						HTTP_LOCALHOST_PREFIX + port + API_LOGIN).contentType("application/json")
						.content(objectMapper.writeValueAsString(loginRequest))).andReturn().getResponse().getContentAsString(),
				TokenPairModel.class);

		AddFriendRequest addFriendRequest = AddFriendRequest.builder()
				.phone("79961073186")
				.build();
		SuccessResponse<UserModel> response = objectMapper.readValue(mockMvc.perform(post(
						HTTP_LOCALHOST_PREFIX + port + API_FRIEND)
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(addFriendRequest))
						.header("Authorization", TOKEN_PREFIX + tokenPairModel.getAccessToken()))
				.andReturn().getResponse().getContentAsString(), new TypeReference<>() {
		});

		Assertions.assertEquals(addFriendRequest.getPhone(), response.getData().getPhone());
		Assertions.assertEquals("gosha2", response.getData().getName());
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
		Assertions.assertEquals("friend successfully added", response.getMessage());
	}
}
