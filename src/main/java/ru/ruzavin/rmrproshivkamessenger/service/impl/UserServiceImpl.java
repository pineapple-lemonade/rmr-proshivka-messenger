package ru.ruzavin.rmrproshivkamessenger.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruzavin.rmrproshivkamessenger.dto.model.UserModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.CreateUserRequest;
import ru.ruzavin.rmrproshivkamessenger.entity.UserEntity;
import ru.ruzavin.rmrproshivkamessenger.entity.enums.UserState;
import ru.ruzavin.rmrproshivkamessenger.exception.UserAlreadyExistsException;
import ru.ruzavin.rmrproshivkamessenger.mapper.UserMapper;
import ru.ruzavin.rmrproshivkamessenger.repository.UserRepository;
import ru.ruzavin.rmrproshivkamessenger.service.UserService;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;

	@Transactional
	@Override
	public UserModel createUser(CreateUserRequest request) {
		userRepository.findUserEntityByPhone(request.getPhone()).orElseThrow(UserAlreadyExistsException::new);

		UserEntity newUser = UserEntity.builder()
				.chats(Collections.emptySet())
				.friends(Collections.emptySet())
				.tokens(Collections.emptySet())
				.name(request.getName())
				.phone(request.getPhone())
				.password(passwordEncoder.encode(request.getPassword()))
				.state(UserState.NOT_CONFIRMED)
				.build();

		return userMapper.fromEntity(userRepository.save(newUser));
	}
}
