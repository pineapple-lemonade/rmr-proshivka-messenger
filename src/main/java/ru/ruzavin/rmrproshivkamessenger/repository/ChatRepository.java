package ru.ruzavin.rmrproshivkamessenger.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruzavin.rmrproshivkamessenger.entity.ChatEntity;
import ru.ruzavin.rmrproshivkamessenger.entity.UserEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {
	Optional<ChatEntity> findChatEntityByUsersIn(Set<UserEntity> users);

	Page<ChatEntity> findAllByUsersInOrderByLatestMessageDesc(Pageable pageable, Set<UserEntity> users);
}
