package ru.ruzavin.rmrproshivkamessenger.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruzavin.rmrproshivkamessenger.dto.model.ChatModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.CreateGroupChatRequest;
import ru.ruzavin.rmrproshivkamessenger.entity.ChatEntity;
import ru.ruzavin.rmrproshivkamessenger.entity.UserEntity;
import ru.ruzavin.rmrproshivkamessenger.entity.enums.ChatType;
import ru.ruzavin.rmrproshivkamessenger.exception.FriendNotExistsException;
import ru.ruzavin.rmrproshivkamessenger.exception.UserNotExistsException;
import ru.ruzavin.rmrproshivkamessenger.mapper.ChatMapper;
import ru.ruzavin.rmrproshivkamessenger.repository.ChatRepository;
import ru.ruzavin.rmrproshivkamessenger.repository.UserRepository;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;
import ru.ruzavin.rmrproshivkamessenger.service.ChatService;

import java.util.*;


@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatRepository chatRepository;

	private final UserRepository userRepository;

	private final ChatMapper chatMapper;

	@Value("${default.page.number}")
	private int pageNumber;

	@Value("${default.page.size}")
	private int pageSize;

	@Transactional
	@Override
	public ChatModel createChat(UUID userId, UserDetailsImpl userDetails) {
		UserEntity friend = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotExistsException("user not exists"));
		UserEntity user = userDetails.getUser();

		Optional<ChatEntity> chatFromDB = chatRepository.findChatEntityByUsersIn(Set.of(friend, user));
		if (chatFromDB.isPresent()) {
			return chatMapper.fromEntity(chatFromDB.get());
		}

		if (user.getFriends().contains(friend)) {
			ChatEntity chat = ChatEntity.builder()
					.messages(Collections.emptySet())
					.type(ChatType.PRIVATE)
					.users(Set.of(user, friend))
					.build();
			return chatMapper.fromEntity(chatRepository.save(chat));
		} else {
			throw new FriendNotExistsException("this user don't have such friend");
		}
	}

	@Transactional
	@Override
	public ChatModel createGroupChat(CreateGroupChatRequest request, UserDetailsImpl userDetails) {
		List<UserEntity> friends = userRepository.findAllByUserIdIn(request.getUserIds());
		UserEntity user = userDetails.getUser();
		Set<UserEntity> userFriends = user.getFriends();

		if (friends.isEmpty() || !userFriends.containsAll(friends)) {
			throw new FriendNotExistsException("user don't have such friends");
		} else {
			Set<UserEntity> chatUsers = new HashSet<>(friends);
			chatUsers.add(user);

			ChatEntity chat = ChatEntity.builder()
					.users(chatUsers)
					.type(ChatType.GROUP)
					.messages(Collections.emptySet())
					.build();

			return chatMapper.fromEntity(chatRepository.save(chat));
		}
	}

	@Transactional
	@Override
	public Page<ChatModel> getLatestChats(UserDetailsImpl userDetails) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

		UserEntity user = userDetails.getUser();
		Page<ChatEntity> latestChats =
				chatRepository.findAllByUsersInOrderByLatestMessageDesc(pageRequest, Set.of(user));

		return latestChats.map(chatMapper::fromEntity);
	}
}
