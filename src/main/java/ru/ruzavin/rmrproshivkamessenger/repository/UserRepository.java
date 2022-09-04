package ru.ruzavin.rmrproshivkamessenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruzavin.rmrproshivkamessenger.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	Optional<UserEntity> findUserEntityByName(String name);
	List<UserEntity> findAllByUserIdIn(List<UUID> ids);
	Optional<UserEntity> findUserEntityByPhone(String phone);
}
