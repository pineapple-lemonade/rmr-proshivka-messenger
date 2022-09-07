package ru.ruzavin.rmrproshivkamessenger.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateGroupChatRequest {

	@Schema(name = "userIds", description = "ids of users which group chat we want to create")
	List<UUID> userIds;
}
