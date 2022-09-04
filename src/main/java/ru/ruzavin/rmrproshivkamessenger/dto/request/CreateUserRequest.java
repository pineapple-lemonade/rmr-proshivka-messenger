package ru.ruzavin.rmrproshivkamessenger.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
	@Schema(name = "name", description = "name of user")
	String name;

	@Schema(name = "password", description = "password of user")
	String password;

	@Schema(name = "phone", description = "phone of user that uses for authentication")
	String phone;
}
