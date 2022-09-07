package ru.ruzavin.rmrproshivkamessenger.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ruzavin.rmrproshivkamessenger.dto.model.TokenPairModel;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api/token")
public interface TokenApi {

	@GetMapping(value = "/refresh")
	@ResponseStatus(HttpStatus.OK)
	TokenPairModel refreshTokensAndAuthentication(HttpServletRequest request);
}
