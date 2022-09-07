package ru.ruzavin.rmrproshivkamessenger.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ruzavin.rmrproshivkamessenger.dto.model.MessageModel;
import ru.ruzavin.rmrproshivkamessenger.entity.MessageEntity;

@Mapper(componentModel = "spring", imports = ChatMapper.class)
public interface MessageMapper {
	@Mapping(target = "issuerId", source = "issuer.userId")
	@Mapping(target = "chatModel", source = "chat")
	MessageModel fromEntity(MessageEntity message);
}
