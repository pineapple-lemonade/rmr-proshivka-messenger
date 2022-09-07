package ru.ruzavin.rmrproshivkamessenger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ruzavin.rmrproshivkamessenger.api.TokenApi;
import ru.ruzavin.rmrproshivkamessenger.dto.model.TokenPairModel;
import ru.ruzavin.rmrproshivkamessenger.service.TokenService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class TokenController implements TokenApi {

	private final TokenService tokenService;

	@Override
	public TokenPairModel refreshTokensAndAuthentication(HttpServletRequest request) {
		return tokenService.refreshTokens(request);
	}
}
