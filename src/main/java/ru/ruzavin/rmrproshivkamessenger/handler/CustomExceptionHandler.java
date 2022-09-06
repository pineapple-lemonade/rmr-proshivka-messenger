package ru.ruzavin.rmrproshivkamessenger.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ruzavin.rmrproshivkamessenger.dto.model.ValidationErrorModel;
import ru.ruzavin.rmrproshivkamessenger.dto.response.ErrorResponse;
import ru.ruzavin.rmrproshivkamessenger.dto.response.ValidationErrorResponse;
import ru.ruzavin.rmrproshivkamessenger.exception.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

	@ExceptionHandler(ChatAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleChatAlreadyExistsException(ChatAlreadyExistsException exception) {
		return wrapException(HttpStatus.BAD_REQUEST, exception);
	}

	@ExceptionHandler(ChatMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleChatMismatchException(ChatMismatchException exception) {
		return wrapException(HttpStatus.BAD_REQUEST, exception);
	}

	@ExceptionHandler(ChatNotExistsException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleChatNotExistsException(ChatNotExistsException exception) {
		return wrapException(HttpStatus.NOT_FOUND, exception);
	}

	@ExceptionHandler(FriendAlreadyAddedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleFriendAlreadyAdded(FriendAlreadyAddedException exception) {
		return wrapException(HttpStatus.BAD_REQUEST, exception);
	}

	@ExceptionHandler(FriendNotExistsException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleFriendNotExists(FriendNotExistsException exception) {
		return wrapException(HttpStatus.NOT_FOUND, exception);
	}

	@ExceptionHandler(RefreshTokenNotExistsException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleRefreshTokenNotExists(RefreshTokenNotExistsException exception) {
		return wrapException(HttpStatus.NOT_FOUND, exception);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleUserAlreadyExists(UserAlreadyExistsException exception) {
		return wrapException(HttpStatus.BAD_REQUEST, exception);
	}

	@ExceptionHandler(UserNotExistsException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleUserAlreadyExists(UserNotExistsException exception) {
		return wrapException(HttpStatus.NOT_FOUND, exception);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<ValidationErrorModel> validationErrorDtos = new ArrayList<>();

		e.getBindingResult().getAllErrors().forEach(objectError -> {
			String errorMessage = objectError.getDefaultMessage();
			ValidationErrorModel errorDto = ValidationErrorModel.builder()
					.message(errorMessage)
					.build();

			if (objectError instanceof FieldError) {
				String fieldName = ((FieldError) objectError).getField();
				errorDto.setField(fieldName);
			} else if (objectError instanceof ObjectError) {
				String objectName = objectError.getObjectName();
				errorDto.setObject(objectName);
			}
			validationErrorDtos.add(errorDto);
		});

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ValidationErrorResponse.builder()
						.errors(validationErrorDtos)
						.message("validation error")
						.timestamp(OffsetDateTime.now())
						.status(HttpStatus.BAD_REQUEST)
						.build());
	}

	private <T extends Exception> ErrorResponse wrapException(HttpStatus status, T exception) {
		log.error("error with message {}", exception.getMessage(), exception);

		return ErrorResponse.builder()
				.message(exception.getMessage())
				.timestamp(OffsetDateTime.now())
				.status(status)
				.build();
	}
}
