package ru.ruzavin.rmrproshivkamessenger.security.service;

import ru.ruzavin.rmrproshivkamessenger.dto.response.SendSmsResponse;

public interface SmsService {
	SendSmsResponse sendSms(String phone);
	Boolean checkCode(String code, String phone);
}
