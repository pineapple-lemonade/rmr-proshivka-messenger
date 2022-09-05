package ru.ruzavin.rmrproshivkamessenger.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "default.page")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class PageConfigurationProperties {
	Integer size;
	Integer number;
}
