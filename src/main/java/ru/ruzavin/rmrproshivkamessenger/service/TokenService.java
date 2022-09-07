package ru.ruzavin.rmrproshivkamessenger.service;

import ru.ruzavin.rmrproshivkamessenger.dto.model.TokenPairModel;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
	TokenPairModel refreshTokens(HttpServletRequest request);
}
