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
public class MessageModel {

	@Schema(name = "text", description = "text of message")
	String text;

	@Schema(name = "issuerId", description = "the id of user who send this message")
	UUID issuerId;

	@Schema(name = "chatModel", description = "model of chat that describes it's type and id", implementation = ChatModel.class)
	ChatModel chatModel;
}
