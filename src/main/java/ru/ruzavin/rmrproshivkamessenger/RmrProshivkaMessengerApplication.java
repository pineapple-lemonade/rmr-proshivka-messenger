package ru.ruzavin.rmrproshivkamessenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.ruzavin.rmrproshivkamessenger")
@EnableFeignClients
public class RmrProshivkaMessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmrProshivkaMessengerApplication.class, args);
	}

}
