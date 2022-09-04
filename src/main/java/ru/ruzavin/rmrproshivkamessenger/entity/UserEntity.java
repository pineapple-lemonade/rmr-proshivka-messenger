package ru.ruzavin.rmrproshivkamessenger.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import ru.ruzavin.rmrproshivkamessenger.entity.enums.UserState;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "system_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends AbstractEntity{

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "user_id")
	UUID userId;

	@Column(name = "name", nullable = false)
	String name;

	/*
	 * phone number of user that uses for confirmation
	 */
	@Column(name = "phone", nullable = false)
	String phone;

	@Column(name = "password", nullable = false)
	String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "state", nullable = false)
	UserState state;

	/*
	 * user refresh tokens
	 */
	@ToString.Exclude
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	Set<RefreshTokenEntity> tokens;

	@ManyToMany(mappedBy = "users")
	@ToString.Exclude
	Set<ChatEntity> chats;

	@ManyToMany
	@JoinTable(name = "user_friend", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "user_id"))
	Set<UserEntity> users;

	@ManyToMany
	@JoinTable(name = "user_friend", joinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
	Set<UserEntity> friends;
}
