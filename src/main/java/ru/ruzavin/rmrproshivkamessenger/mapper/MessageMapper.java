package ru.ruzavin.rmrproshivkamessenger.mapper;

import org.mapstruct.Mapper;
import ru.ruzavin.rmrproshivkamessenger.dto.model.MessageModel;
import ru.ruzavin.rmrproshivkamessenger.entity.MessageEntity;

@Mapper(componentModel = "spring")
public interface MessageMapper {
	MessageModel fromEntity(MessageEntity message);
}
