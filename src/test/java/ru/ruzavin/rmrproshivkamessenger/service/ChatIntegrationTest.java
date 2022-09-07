package ru.ruzavin.rmrproshivkamessenger.service;

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
import ru.ruzavin.rmrproshivkamessenger.dto.request.CreateGroupChatRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.request.LoginRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.PageResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SuccessResponse;
import ru.ruzavin.rmrproshivkamessenger.entity.enums.ChatType;
import ru.ruzavin.rmrproshivkamessenger.initializer.PostgresInitializer;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = PostgresInitializer.class)
@Import({TestConfig.class})
@TestPropertySource(properties = "spring.security.enabled=true")
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/scripts/insert-data-for-chat-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ChatIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String HTTP_LOCALHOST_PREFIX = "http://localhost:";

	private static final String API_CHAT = "/api/v1/chat";

	private static final String API_LOGIN = "/api/v1/login";

	private static final String TOKEN_PREFIX = "Bearer ";

	@Test
	void testChatSuccessfullyCreated() throws Exception {
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

		SuccessResponse<ChatModel> response = objectMapper.readValue(mockMvc.perform(post(
						HTTP_LOCALHOST_PREFIX + port + API_CHAT + "/b43a2e70-b12c-44b7-882f-4c7f57174029")
						.header("Authorization", TOKEN_PREFIX + tokenPairModel.getAccessToken()))
				.andReturn().getResponse().getContentAsString(), new TypeReference<>() {
		});

		Assertions.assertEquals(ChatType.PRIVATE, response.getData().getType());
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
		Assertions.assertEquals("chat successfully created", response.getMessage());
	}

	@Test
	void testGroupChatSuccessfullyCreated() throws Exception {
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

		CreateGroupChatRequest request = CreateGroupChatRequest.builder()
				.userIds(List.of(UUID.fromString("b43a2e70-b12c-44b7-882f-4c7f57174027"),
						UUID.fromString("b43a2e70-b12c-44b7-882f-4c7f57174028")))
				.build();
		SuccessResponse<ChatModel> response = objectMapper.readValue(mockMvc.perform(post(
						HTTP_LOCALHOST_PREFIX + port + API_CHAT)
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(request))
						.header("Authorization", TOKEN_PREFIX + tokenPairModel.getAccessToken()))
				.andReturn().getResponse().getContentAsString(), new TypeReference<>() {
		});

		Assertions.assertEquals(ChatType.GROUP, response.getData().getType());
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
		Assertions.assertEquals("chat successfully created", response.getMessage());
	}

	@Test
	void testGetLatestChatsSuccessfullyPerformed() throws Exception {
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

		PageResponse<List<ChatModel>> response = objectMapper.readValue(mockMvc.perform(get(
						HTTP_LOCALHOST_PREFIX + port + API_CHAT)
						.header("Authorization", TOKEN_PREFIX + tokenPairModel.getAccessToken()))
				.andReturn().getResponse().getContentAsString(), new TypeReference<>() {
		});

		Assertions.assertEquals(ChatType.PRIVATE, response.getData().get(0).getType());
		Assertions.assertEquals(UUID.fromString("eb75ef46-6890-47fd-bc10-6caf046b6c5c"), response.getData().get(0).getChatId());
		Assertions.assertEquals(HttpStatus.OK, response.getStatus());
		Assertions.assertEquals("latest chats received", response.getMessage());
	}
}
