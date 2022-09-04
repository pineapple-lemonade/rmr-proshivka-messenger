package ru.ruzavin.rmrproshivkamessenger.service;

import ru.ruzavin.rmrproshivkamessenger.dto.model.UserModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.CreateUserRequest;

public interface UserService {
	UserModel createUser(CreateUserRequest request);
}
