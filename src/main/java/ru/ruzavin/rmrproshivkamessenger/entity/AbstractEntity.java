package ru.ruzavin.rmrproshivkamessenger.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@Where(clause = "delete_date is null")
public class AbstractEntity {

	@Column(name = "created_date", nullable = false, updatable = false)
	@CreationTimestamp
	OffsetDateTime createdDate;

	@Column(name = "updated_date")
	@UpdateTimestamp
	OffsetDateTime updatedDate;

	@Column(name = "deleted_date")
	OffsetDateTime deletedDate;

}
