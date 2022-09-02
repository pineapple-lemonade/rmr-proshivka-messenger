package ru.ruzavin.rmrproshivkamessenger.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserModel {

	@Schema(name = "userId", description = "id of user")
	UUID userId;

	@Schema(name = "phone", description = "phone number of user")
	String phone;

	@Schema(name = "name", description = "name of user")
	String name;
}
