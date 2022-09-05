package ru.ruzavin.rmrproshivkamessenger.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "sms")
@Component
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmsConfigProperties {
	String email;
	String apiKey;
	String smsText;
	String smsSign;
}
