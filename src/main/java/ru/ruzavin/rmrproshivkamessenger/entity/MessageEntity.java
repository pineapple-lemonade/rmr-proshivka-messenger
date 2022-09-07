package ru.ruzavin.rmrproshivkamessenger.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "message")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageEntity extends AbstractEntity{

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "message_id")
	UUID messageId;

	@Column(name = "text", nullable = false)
	String text;

	/*
	 * describes user that send message
	 */
	@ManyToOne
	@JoinColumn(name = "issuer_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "message_user_id"))
	UserEntity issuer;

	/*
	 * chat that message belongs to
	 */
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "chat_id", referencedColumnName = "chat_id", foreignKey = @ForeignKey(name = "message_chat_id"))
	ChatEntity chat;
}
