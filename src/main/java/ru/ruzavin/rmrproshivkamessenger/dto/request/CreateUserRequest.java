package ru.ruzavin.rmrproshivkamessenger.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {

	@NotBlank(message = "This field must not be empty")
	@Size(min = 2, message = "Name must contains at least 2 characters")
	@Schema(name = "name", description = "name of user")
	String name;

	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
			message = "Password must contains at least 8 characters, lower- and uppercase letters," +
					" at least 1 number, at least 1 special character")
	@Schema(name = "password", description = "password of user")
	String password;

	@Pattern(regexp = "^(\\+7|7|8)?[\\s\\-]?\\(?[489]\\d{2}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$",
			message = "Field must be phone number, for example: 79965314698")
	@NotBlank(message = "This field must not be empty")
	@Schema(name = "phone", description = "phone of user that uses for authentication")
	String phone;
}
