package ru.ruzavin.rmrproshivkamessenger.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationErrorModel {

	@Schema(description = "which field caused error")
	String field;

	@Schema(description = "object that caused error")
	String object;

	@Schema(description = "message of error")
	String message;
}
