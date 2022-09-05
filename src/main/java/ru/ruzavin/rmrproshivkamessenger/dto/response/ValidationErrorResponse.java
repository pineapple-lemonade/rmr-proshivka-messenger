package ru.ruzavin.rmrproshivkamessenger.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.ruzavin.rmrproshivkamessenger.dto.model.ValidationErrorModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Schema(name = "Validation error response")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationErrorResponse extends ErrorResponse{
	@Schema(description = "list of validation errors")
	List<ValidationErrorModel> errors;
}
