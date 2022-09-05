package ru.ruzavin.rmrproshivkamessenger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.ruzavin.rmrproshivkamessenger.api.FriendApi;
import ru.ruzavin.rmrproshivkamessenger.dto.model.UserModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.AddFriendRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.PageResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SuccessResponse;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;
import ru.ruzavin.rmrproshivkamessenger.service.FriendService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class FriendController implements FriendApi {

	private final FriendService friendService;

	@Override
	public PageResponse<List<UserModel>> getFriendsList(Integer pageSize, Integer pageNumber, UserDetailsImpl userDetails) {
		Page<UserModel> friendsPage = friendService.getFriendsList(pageSize, pageNumber, userDetails);

		return PageResponse.<List<UserModel>>pageResponseBuilder()
				.data(friendsPage.getContent())
				.status(HttpStatus.OK)
				.timestamp(LocalDateTime.now())
				.message("successfully received friends list")
				.totalPages(friendsPage.getTotalPages())
				.totalElements(friendsPage.getTotalElements())
				.page(friendsPage.getNumber())
				.size(friendsPage.getSize())
				.build();
	}

	@Override
	public SuccessResponse<UserModel> addFriend(AddFriendRequest request, UserDetailsImpl userDetails) {
		return SuccessResponse.<UserModel>successResponseBuilder()
				.data(friendService.addFriend(request, userDetails))
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.CREATED)
				.message("friend successfully added")
				.build();
	}
}
