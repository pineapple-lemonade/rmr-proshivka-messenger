package ru.ruzavin.rmrproshivkamessenger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.ruzavin.rmrproshivkamessenger.api.ChatApi;
import ru.ruzavin.rmrproshivkamessenger.dto.model.ChatModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.CreateGroupChatRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.PageResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SuccessResponse;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;
import ru.ruzavin.rmrproshivkamessenger.service.ChatService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ChatController implements ChatApi {

	private final ChatService chatService;

	@Override
	public SuccessResponse<ChatModel> createChat(UUID userId, UserDetailsImpl userDetails) {
		return SuccessResponse.<ChatModel>successResponseBuilder()
				.data(chatService.createChat(userId, userDetails))
				.message("chat successfully created")
				.status(HttpStatus.CREATED)
				.timestamp(OffsetDateTime.now())
				.build();
	}

	@Override
	public SuccessResponse<ChatModel> createGroupChat(CreateGroupChatRequest request, UserDetailsImpl userDetails) {
		return SuccessResponse.<ChatModel>successResponseBuilder()
				.data(chatService.createGroupChat(request, userDetails))
				.message("chat successfully created")
				.status(HttpStatus.CREATED)
				.timestamp(OffsetDateTime.now())
				.build();
	}

	@Override
	public PageResponse<List<ChatModel>> getLatestChats(UserDetailsImpl userDetails) {
		Page<ChatModel> latestChats = chatService.getLatestChats(userDetails);

		return PageResponse.<List<ChatModel>>pageResponseBuilder()
				.data(latestChats.getContent())
				.message("latest chats received")
				.status(HttpStatus.OK)
				.timestamp(OffsetDateTime.now())
				.page(latestChats.getNumber())
				.size(latestChats.getSize())
				.totalElements(latestChats.getTotalElements())
				.totalPages(latestChats.getTotalPages())
				.build();
	}
}
