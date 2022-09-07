package ru.ruzavin.rmrproshivkamessenger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.ruzavin.rmrproshivkamessenger.api.MessageApi;
import ru.ruzavin.rmrproshivkamessenger.dto.model.MessageModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.SendMessageRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.PageResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SuccessResponse;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;
import ru.ruzavin.rmrproshivkamessenger.service.MessageService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MessageController implements MessageApi {

	private final MessageService messageService;

	@Override
	public SuccessResponse<MessageModel> sendMessageToChat(UUID chatId, SendMessageRequest request, UserDetailsImpl userDetails) {
		return SuccessResponse.<MessageModel>successResponseBuilder()
				.message("message successfully sent")
				.timestamp(OffsetDateTime.now())
				.status(HttpStatus.CREATED)
				.data(messageService.sendMessage(chatId, request, userDetails))
				.build();
	}

	@Override
	public PageResponse<List<MessageModel>> getMessagesFromChat(UUID chatId, Integer pageSize, Integer pageNumber,
	                                                            UserDetailsImpl userDetails) {
		Page<MessageModel> messagesFromChat =
				messageService.getMessagesFromChat(chatId, pageSize, pageNumber, userDetails);

		return PageResponse.<List<MessageModel>>pageResponseBuilder()
				.message("messages successfully received")
				.timestamp(OffsetDateTime.now())
				.status(HttpStatus.OK)
				.data(messagesFromChat.getContent())
				.size(messagesFromChat.getSize())
				.page(messagesFromChat.getNumber())
				.totalElements(messagesFromChat.getTotalElements())
				.totalPages(messagesFromChat.getTotalPages())
				.build();
	}
}
