package ru.ruzavin.rmrproshivkamessenger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ruzavin.rmrproshivkamessenger.api.UserApi;
import ru.ruzavin.rmrproshivkamessenger.dto.model.UserModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.CreateUserRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SuccessResponse;
import ru.ruzavin.rmrproshivkamessenger.service.UserService;

import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

	private final UserService userService;

	@Override
	public SuccessResponse<UserModel> createUser(CreateUserRequest request) {
		return SuccessResponse.<UserModel>successResponseBuilder()
				.message("user successfully created")
				.status(HttpStatus.CREATED)
				.timestamp(OffsetDateTime.now())
				.data(userService.createUser(request))
				.build();
	}
}
