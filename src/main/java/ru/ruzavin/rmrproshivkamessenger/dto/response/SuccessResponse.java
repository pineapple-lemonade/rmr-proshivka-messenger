package ru.ruzavin.rmrproshivkamessenger.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder(builderMethodName = "successResponseBuilder")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> extends AbstractResponse {

	@Schema(name = "data", description = "data that arrived with response")
	T data;
}
