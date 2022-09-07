package ru.ruzavin.rmrproshivkamessenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruzavin.rmrproshivkamessenger.entity.RefreshTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
	Optional<RefreshTokenEntity> getRefreshTokenEntityByContent(String content);
}
