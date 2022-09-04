package ru.ruzavin.rmrproshivkamessenger.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SendSmsResponse;

@FeignClient(value = "smsAero", url = "http://${sms.email}:${sms.api-key}@gate.smsaero.ru/v2/sms")
public interface SmsClient {

	@GetMapping("/send")
	SendSmsResponse sendSmsCode(@RequestParam(name = "number") String number,
	                            @RequestParam(name = "sign") String sign,
	                            @RequestParam(name = "text") String text);
}
