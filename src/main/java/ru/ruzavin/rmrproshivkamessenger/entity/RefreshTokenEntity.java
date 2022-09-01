package ru.ruzavin.rmrproshivkamessenger.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "refresh_token")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenEntity extends AbstractEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "token_id")
	UUID tokenId;

	@Column(name = "content", nullable = false)
	String content;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "user_token_fk"))
	UserEntity user;
}
