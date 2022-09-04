package ru.ruzavin.rmrproshivkamessenger.mapper;

import org.mapstruct.Mapper;
import ru.ruzavin.rmrproshivkamessenger.dto.model.ChatModel;
import ru.ruzavin.rmrproshivkamessenger.entity.ChatEntity;

@Mapper(componentModel = "spring")
public interface ChatMapper {
	ChatModel fromEntity(ChatEntity chatEntity);
}
