package ru.ruzavin.rmrproshivkamessenger.mapper;

import org.mapstruct.Mapper;
import ru.ruzavin.rmrproshivkamessenger.dto.model.UserModel;
import ru.ruzavin.rmrproshivkamessenger.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserModel fromEntity(UserEntity user);
}
