package ru.ruzavin.rmrproshivkamessenger.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import ru.ruzavin.rmrproshivkamessenger.entity.enums.ChatType;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "chat")
public class ChatEntity extends AbstractEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "chat_id")
	UUID chatId;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	ChatType type;

	@ToString.Exclude
	@OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
	Set<MessageEntity> messages;

	/*
	 * chat may have a lot of users because chat can be group or private
	 */
	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "chat_id"),
	inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
	name = "chat_user")
	@ToString.Exclude
	Set<UserEntity> users;

	@Column(name = "latest_message")
	OffsetDateTime latestMessage;
}
