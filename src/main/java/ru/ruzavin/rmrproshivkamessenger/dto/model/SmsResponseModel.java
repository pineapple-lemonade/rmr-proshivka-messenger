package ru.ruzavin.rmrproshivkamessenger.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmsResponseModel {
	Integer id;
	String from;
	String number;
	String text;
	Integer status;
	String extendStatus;
	String channel;
	String cost;
	Integer dateCreate;
	Integer dateSend;
}
