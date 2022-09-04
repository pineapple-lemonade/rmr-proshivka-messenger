package ru.ruzavin.rmrproshivkamessenger.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ruzavin.rmrproshivkamessenger.dto.model.MessageModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.SendMessageRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.ErrorResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.PageResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SuccessResponse;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/message")
public interface MessageApi {

	@Operation(summary = "send message to chat",
			parameters = @Parameter(name = "chat_id", description = "id of chat to whom message we want send"))
	@ApiResponse(responseCode = "200", description = "message successfully sent",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class)))
	@ApiResponse(responseCode = "400", description = "chat doesn't belong to issuer",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PostMapping("/{chat_id}")
	@ResponseStatus(HttpStatus.CREATED)
	SuccessResponse<MessageModel> sendMessageToChat(@PathVariable("chat_id") UUID chatId, SendMessageRequest request,
	                                                @Parameter(hidden = true)
	                                                @AuthenticationPrincipal UserDetailsImpl userDetails);

	@Operation(summary = "get messages from given chat",
			parameters = {@Parameter(name = "chat_id", description = "id of chat to whom message we want send"),
					@Parameter(name = "page_size", description = "size of page"),
					@Parameter(name = "page_number", description = "number of page")})
	@ApiResponse(responseCode = "200", description = "message successfully received",
			content = @Content(schema = @Schema(implementation = PageResponse.class)))
	@ApiResponse(responseCode = "400", description = "chat doesn't belong to user",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@GetMapping("/{chat_id}")
	@ResponseStatus(HttpStatus.OK)
	PageResponse<List<MessageModel>> getMessagesFromChat(@PathVariable("chat_id") UUID chatId,
	                                                     @RequestParam(value = "page_size", required = false) int pageSize,
	                                                     @RequestParam(value = "pag_number", required = false) int pageNumber,
	                                                     @Parameter(hidden = true)
	                                                     @AuthenticationPrincipal UserDetailsImpl userDetails);
}
