package ru.ruzavin.rmrproshivkamessenger.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruzavin.rmrproshivkamessenger.dto.model.MessageModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.SendMessageRequest;
import ru.ruzavin.rmrproshivkamessenger.entity.ChatEntity;
import ru.ruzavin.rmrproshivkamessenger.entity.MessageEntity;
import ru.ruzavin.rmrproshivkamessenger.entity.UserEntity;
import ru.ruzavin.rmrproshivkamessenger.exception.ChatMismatchException;
import ru.ruzavin.rmrproshivkamessenger.exception.ChatNotExistsException;
import ru.ruzavin.rmrproshivkamessenger.mapper.MessageMapper;
import ru.ruzavin.rmrproshivkamessenger.repository.ChatRepository;
import ru.ruzavin.rmrproshivkamessenger.repository.MessageRepository;
import ru.ruzavin.rmrproshivkamessenger.repository.UserRepository;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;
import ru.ruzavin.rmrproshivkamessenger.service.MessageService;
import ru.ruzavin.rmrproshivkamessenger.util.RequestParamUtil;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageRepository messageRepository;

	private final ChatRepository chatRepository;

	private final MessageMapper messageMapper;

	private final UserRepository userRepository;

	private final RequestParamUtil requestParamUtil;

	@Transactional
	@Override
	public MessageModel sendMessage(UUID chatId, SendMessageRequest request, UserDetailsImpl userDetails) {
		UserEntity user = userRepository.getReferenceById(userDetails.getUser().getUserId());

		ChatEntity chat = getChat(chatId, user);
		chat.setLatestMessage(OffsetDateTime.now());

		MessageEntity message = MessageEntity.builder()
				.text(request.getText())
				.chat(chat)
				.issuer(user)
				.build();

		return messageMapper.fromEntity(messageRepository.save(message));
	}



	@Transactional(readOnly = true)
	@Override
	public Page<MessageModel> getMessagesFromChat(UUID chatId, Integer pageSize, Integer pageNumber, UserDetailsImpl userDetails) {
		UserEntity user = userRepository.getReferenceById(userDetails.getUser().getUserId());
		ChatEntity chat = getChat(chatId, user);

		pageNumber = requestParamUtil.handlePageNumber(pageNumber);
		pageSize = requestParamUtil.handlePageSize(pageSize);
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		Page<MessageEntity> messagesFromChat = messageRepository.findAllByChat(pageRequest, chat);

		return messagesFromChat.map(messageMapper::fromEntity);
	}

	private ChatEntity getChat(UUID chatId, UserEntity user) {
		ChatEntity chat = chatRepository.findById(chatId).orElseThrow(ChatNotExistsException::new);

		if (!user.getChats().contains(chat)) {
			throw new ChatMismatchException();
		}

		return chat;
	}
}
