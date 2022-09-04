package ru.ruzavin.rmrproshivkamessenger.service;

import org.springframework.data.domain.Page;
import ru.ruzavin.rmrproshivkamessenger.dto.model.UserModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.AddFriendRequest;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;

public interface FriendService {
	Page<UserModel> getFriendsList(int pageSize, int pageNumber, UserDetailsImpl userDetails);
	UserModel addFriend(AddFriendRequest request, UserDetailsImpl userDetails);
}
