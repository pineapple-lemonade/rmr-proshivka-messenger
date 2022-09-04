package ru.ruzavin.rmrproshivkamessenger.service;

import org.springframework.data.domain.Page;
import ru.ruzavin.rmrproshivkamessenger.dto.model.ChatModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.CreateGroupChatRequest;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;

import java.util.UUID;

public interface ChatService {
	ChatModel createChat(UUID userId, UserDetailsImpl userDetails);
	ChatModel createGroupChat(CreateGroupChatRequest request, UserDetailsImpl userDetails);
	Page<ChatModel> getLatestChats(UserDetailsImpl userDetails);
}
