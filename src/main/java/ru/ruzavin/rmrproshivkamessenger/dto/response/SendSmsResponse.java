package ru.ruzavin.rmrproshivkamessenger.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ruzavin.rmrproshivkamessenger.dto.model.SmsResponseModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendSmsResponse {
	Boolean success;
	List<SmsResponseModel> data;
	String message;
}
