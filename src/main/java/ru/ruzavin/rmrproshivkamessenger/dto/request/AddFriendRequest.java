package ru.ruzavin.rmrproshivkamessenger.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddFriendRequest {

	@Schema(name = "phone", description = "phone number of friend")
	String phone;
}
