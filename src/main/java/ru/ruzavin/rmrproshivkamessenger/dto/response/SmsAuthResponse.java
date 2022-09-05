package ru.ruzavin.rmrproshivkamessenger.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmsAuthResponse {
	Boolean success;
	String data;
	String message;
}
