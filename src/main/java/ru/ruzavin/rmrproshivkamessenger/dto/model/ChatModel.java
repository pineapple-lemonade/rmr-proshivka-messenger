package ru.ruzavin.rmrproshivkamessenger.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ruzavin.rmrproshivkamessenger.entity.enums.ChatType;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatModel {

	@Schema(name = "chatId", description = "id of chat")
	UUID chatId;

	@Schema(name = "type", description = "type of chat", implementation = ChatType.class)
	ChatType type;
}
