package ru.ruzavin.rmrproshivkamessenger.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

	@Schema(name = "name", description = "name of user")
	String name;

	@Schema(name = "password")
	String password;
}
