package ru.ruzavin.rmrproshivkamessenger.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruzavin.rmrproshivkamessenger.dto.model.UserModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.AddFriendRequest;
import ru.ruzavin.rmrproshivkamessenger.entity.UserEntity;
import ru.ruzavin.rmrproshivkamessenger.exception.FriendAlreadyAddedException;
import ru.ruzavin.rmrproshivkamessenger.exception.UserNotExistsException;
import ru.ruzavin.rmrproshivkamessenger.mapper.UserMapper;
import ru.ruzavin.rmrproshivkamessenger.repository.UserRepository;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;
import ru.ruzavin.rmrproshivkamessenger.service.FriendService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.ruzavin.rmrproshivkamessenger.util.RequestParamUtil.handlePageSizeAndPageNumber;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	@Transactional(readOnly = true)
	@Override
	public Page<UserModel> getFriendsList(int pageSize, int pageNumber, UserDetailsImpl userDetails) {
		handlePageSizeAndPageNumber(pageSize, pageNumber);
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

		UserEntity user = userDetails.getUser();
		List<UserModel> listOfFriends = user.getFriends().stream()
				.map(userMapper::fromEntity)
				.collect(Collectors.toList());

		return new PageImpl<>(listOfFriends, pageRequest, listOfFriends.size());
	}

	@Transactional
	@Override
	public UserModel addFriend(AddFriendRequest request, UserDetailsImpl userDetails) {
		UserEntity friend = userRepository.findUserEntityByPhone(request.getPhone())
				.orElseThrow(() -> new UserNotExistsException("user with such phone not exists"));
		UserEntity user = userDetails.getUser();

		if (user.getFriends().contains(friend)) {
			throw new FriendAlreadyAddedException("user already have such friend");
		}

		user.getFriends().add(friend);
		userRepository.save(user);

		return userMapper.fromEntity(friend);
	}
}
