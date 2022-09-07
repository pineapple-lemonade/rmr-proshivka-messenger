package ru.ruzavin.rmrproshivkamessenger.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class AbstractResponse {

	@Schema(name = "message", description = "message of response")
	String message;

	@Schema(name = "timestamp", description = "date when response created")
	OffsetDateTime timestamp;

	@Schema(name = "status", description = "http status of response")
	HttpStatus status;
}
