package ru.ruzavin.rmrproshivkamessenger.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ruzavin.rmrproshivkamessenger.dto.model.ChatModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.CreateGroupChatRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.ErrorResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.PageResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SuccessResponse;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/chat")
public interface ChatApi {

	@Operation(summary = "create chat with given user",
			parameters = @Parameter(name = "user_id", description = "id of user which chat we want to create"))
	@ApiResponse(responseCode = "201", description = "chat successfully created",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class)))
	@ApiResponse(responseCode = "200", description = "chat already created",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class)))
	@ApiResponse(responseCode = "400", description = "user don't have this person in friend's list",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "400", description = "given user doesn't exists",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PostMapping("/{user_id}")
	@ResponseStatus(HttpStatus.CREATED)
	SuccessResponse<ChatModel> createChat(@PathVariable("user_id") UUID userId,
	                                      @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails);

	@Operation(summary = "create group chat with given users")
	@ApiResponse(responseCode = "201", description = "chat successfully created",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class)))
	@ApiResponse(responseCode = "200", description = "chat already created",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class)))
	@ApiResponse(responseCode = "400", description = "user don't have this person in friend's list",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	SuccessResponse<ChatModel> createGroupChat(@RequestBody CreateGroupChatRequest request,
	                                           @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails);

	@Operation(summary = "get messages from given chat",
			parameters = {@Parameter(name = "page_size", description = "size of page"),
					@Parameter(name = "page_number", description = "number of page")})
	@ApiResponse(responseCode = "200", description = "chat successfully received",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class)))
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	PageResponse<List<ChatModel>> getLatestChats(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails);
}
