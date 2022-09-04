package ru.ruzavin.rmrproshivkamessenger.security.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SendSmsResponse;

@Service
@ConditionalOnProperty(name = "sms.enabled", havingValue = "false")
public class LocalSmsService implements SmsService {
	/*
	 * in local sms service we don't send sms
	 */
	@Override
	public SendSmsResponse sendSms(String phone) {
		return new SendSmsResponse();
	}

	@Override
	public Boolean checkCode(String code, String phone) {
		return code.equals("1111");
	}
}
