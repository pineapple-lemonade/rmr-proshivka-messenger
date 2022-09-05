package ru.ruzavin.rmrproshivkamessenger.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ruzavin.rmrproshivkamessenger.entity.ChatEntity;
import ru.ruzavin.rmrproshivkamessenger.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {

	@Query("select distinct chat from ChatEntity chat join chat.users as u " +
			"where u.userId in (:userIds) group by chat.chatId having count(u.userId) = :size")
	Optional<ChatEntity> findChatEntityByUsersIds(List<UUID> userIds, Long size);

	Page<ChatEntity> findAllByUsersInOrderByLatestMessageDesc(Pageable pageable, Set<UserEntity> users);
}
