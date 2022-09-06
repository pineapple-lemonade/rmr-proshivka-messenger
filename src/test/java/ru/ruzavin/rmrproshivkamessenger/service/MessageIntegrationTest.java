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
import ru.ruzavin.rmrproshivkamessenger.dto.model.MessageModel;
import ru.ruzavin.rmrproshivkamessenger.dto.model.TokenPairModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.LoginRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.request.SendMessageRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.PageResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SuccessResponse;
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
class MessageIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String HTTP_LOCALHOST_PREFIX = "http://localhost:";

	private static final String API_MESSAGE = "/api/v1/message";

	private static final String API_LOGIN = "/api/v1/login";

	private static final String TOKEN_PREFIX = "Bearer ";

	@Sql(scripts = "/scripts/insert-data-for-message-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void testMessageSuccessfullySentToChat() throws Exception {
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

		SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
				.text("hello")
				.build();

		SuccessResponse<MessageModel> response = objectMapper.readValue(mockMvc.perform(post(
						HTTP_LOCALHOST_PREFIX + port + API_MESSAGE + "/eb75ef46-6890-47fd-bc10-6caf046b6c5d")
						.contentType("application/json").content(
								objectMapper.writeValueAsString(sendMessageRequest))
						.header("Authorization", TOKEN_PREFIX + tokenPairModel.getAccessToken()))
				.andReturn().getResponse().getContentAsString(), new TypeReference<>() {
		});

		Assertions.assertEquals(sendMessageRequest.getText(), response.getData().getText());
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
		Assertions.assertEquals("message successfully sent", response.getMessage());
	}

	@Sql(scripts = "/scripts/insert-data-for-message-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void testMessagesSuccessfullyReceived() throws Exception {
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

		PageResponse<List<MessageModel>> response = objectMapper.readValue(mockMvc.perform(get(
				HTTP_LOCALHOST_PREFIX + port + API_MESSAGE + "/eb75ef46-6890-47fd-bc10-6caf046b6c5d")
				.header("Authorization", TOKEN_PREFIX + tokenPairModel.getAccessToken()))
				.andReturn().getResponse().getContentAsString(), new TypeReference<>() {
		});

		Assertions.assertEquals("hello", response.getData().get(0).getText());
		Assertions.assertEquals(UUID.fromString("eb75ef46-6890-47fd-bc10-6caf046b6c5d"),
				response.getData().get(0).getChatModel().getChatId());
		Assertions.assertEquals(HttpStatus.OK, response.getStatus());
		Assertions.assertEquals("messages successfully received", response.getMessage());
	}
}
