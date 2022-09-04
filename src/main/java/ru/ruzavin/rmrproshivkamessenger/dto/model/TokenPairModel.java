package ru.ruzavin.rmrproshivkamessenger.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenPairModel {

	@Schema(name = "accessToken", description = "JWT that granted access to resources, live shortly")
	String accessToken;

	@Schema(name = "refreshToken", description = "JWT that granted access to resources, live longer")
	String refreshToken;
}
