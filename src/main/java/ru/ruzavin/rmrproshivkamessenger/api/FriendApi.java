package ru.ruzavin.rmrproshivkamessenger.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ruzavin.rmrproshivkamessenger.dto.model.UserModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.AddFriendRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.ErrorResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.PageResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SuccessResponse;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;

import java.util.List;

@RequestMapping("/api/v1/friend")
public interface FriendApi {

	@Operation(summary = "get user friends list",
			parameters = {
					@Parameter(name = "page_size", description = "size of page response"),
					@Parameter(name = "page_number", description = "number of page")})
	@ApiResponse(responseCode = "200", description = "user friends successfully received",
			content = @Content(schema = @Schema(implementation = PageResponse.class)))
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	PageResponse<List<UserModel>> getFriendsList(@RequestParam(value = "page_size", required = false) int pageSize,
	                                             @RequestParam(value = "page_number", required = false) int pageNumber,
	                                             @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails);

	@Operation(summary = "add friend to user's list")
	@ApiResponse(responseCode = "201", description = "friend successfully added to user's list",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class, description = "model of friend")))
	@ApiResponse(responseCode = "400", description = "friend already added",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "400", description = "user with such phone doesn't exist",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	SuccessResponse<UserModel> addFriend(@RequestBody AddFriendRequest request,
	                                     @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails);
}
