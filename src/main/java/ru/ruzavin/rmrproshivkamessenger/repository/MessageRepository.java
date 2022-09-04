package ru.ruzavin.rmrproshivkamessenger.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruzavin.rmrproshivkamessenger.entity.ChatEntity;
import ru.ruzavin.rmrproshivkamessenger.entity.MessageEntity;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
	Page<MessageEntity> findAllByChat(Pageable pageable, ChatEntity chat);
}
