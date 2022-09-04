package ru.ruzavin.rmrproshivkamessenger.service;

import org.springframework.data.domain.Page;
import ru.ruzavin.rmrproshivkamessenger.dto.model.MessageModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.SendMessageRequest;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;

import java.util.UUID;

public interface MessageService {
	MessageModel sendMessage(UUID chatId, SendMessageRequest request, UserDetailsImpl userDetails);
	Page<MessageModel> getMessagesFromChat(UUID chatId, int pageSize, int pageNumber, UserDetailsImpl userDetails);
}
