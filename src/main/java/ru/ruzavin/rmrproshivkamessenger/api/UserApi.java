package ru.ruzavin.rmrproshivkamessenger.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ruzavin.rmrproshivkamessenger.dto.model.UserModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.CreateUserRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.ErrorResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SuccessResponse;

import javax.validation.Valid;

@RequestMapping("/api/v1/user")
public interface UserApi {

	@Operation(summary = "create user")
	@ApiResponse(responseCode = "200", description = "user successfully created",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class)))
	@ApiResponse(responseCode = "400", description = "user with such phone already exists",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PostMapping("/sign-up")
	@ResponseStatus(HttpStatus.CREATED)
	SuccessResponse<UserModel> createUser(@Valid @RequestBody CreateUserRequest request);
}
