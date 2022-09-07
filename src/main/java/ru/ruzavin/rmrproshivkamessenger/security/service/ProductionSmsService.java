package ru.ruzavin.rmrproshivkamessenger.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruzavin.rmrproshivkamessenger.client.SmsClient;
import ru.ruzavin.rmrproshivkamessenger.config.SmsConfigProperties;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SendSmsResponse;
import ru.ruzavin.rmrproshivkamessenger.entity.UserEntity;
import ru.ruzavin.rmrproshivkamessenger.exception.UserNotExistsException;
import ru.ruzavin.rmrproshivkamessenger.repository.UserRepository;

import static ru.ruzavin.rmrproshivkamessenger.util.SmsCodeGeneratorUtil.generateSmsCode;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "sms.enabled", havingValue = "true")
public class ProductionSmsService implements SmsService{

	private final UserRepository userRepository;

	private final SmsClient smsClient;

	private final SmsConfigProperties smsConfigProperties;

	@Transactional
	@Override
	public SendSmsResponse sendSms(String phone) {
		String smsCode = generateSmsCode();

		UserEntity user = userRepository.findUserEntityByPhone(phone)
				.orElseThrow(() -> new UserNotExistsException("user with such phone doesn't exists"));
		user.setSmsCode(smsCode);
		userRepository.save(user);

		String message = smsConfigProperties.getSmsText().concat(" ").concat(smsCode);

		smsClient.performAuthentication();

		return smsClient.sendSmsCode(phone, smsConfigProperties.getSmsSign(), message);
	}

	@Transactional
	@Override
	public Boolean checkCode(String code, String phone) {
		UserEntity user = userRepository.findUserEntityByPhone(phone)
				.orElseThrow(() -> new UserNotExistsException("user with such phone doesn't exists"));

		if (user.getSmsCode().equals(code)) {
			user.setSmsCode(null);
			userRepository.save(user);

			return true;
		}

		return false;
	}
}
