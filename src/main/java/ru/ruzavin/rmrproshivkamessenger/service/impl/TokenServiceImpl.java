package ru.ruzavin.rmrproshivkamessenger.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruzavin.rmrproshivkamessenger.dto.model.TokenPairModel;
import ru.ruzavin.rmrproshivkamessenger.exception.RefreshTokenNotExistsException;
import ru.ruzavin.rmrproshivkamessenger.repository.RefreshTokenRepository;
import ru.ruzavin.rmrproshivkamessenger.security.authentication.RefreshTokenAuthentication;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;
import ru.ruzavin.rmrproshivkamessenger.security.util.JwtUtil;
import ru.ruzavin.rmrproshivkamessenger.service.TokenService;

import javax.servlet.http.HttpServletRequest;

import static ru.ruzavin.rmrproshivkamessenger.security.constants.SecurityConstants.REFRESH_TOKEN_URL;
import static ru.ruzavin.rmrproshivkamessenger.security.util.AuthorizationHeaderUtil.getToken;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	private final JwtUtil jwtUtil;

	private final AuthenticationProvider refreshTokenAuthenticationProvider;

	@Transactional
	@Override
	public TokenPairModel refreshTokens(HttpServletRequest request) {
		var refreshToken = getToken(request);

		var refreshTokenFromDB =
				refreshTokenRepository.getRefreshTokenEntityByContent(refreshToken)
						.orElseThrow(() -> new RefreshTokenNotExistsException("refresh token not found"));

		refreshTokenRepository.delete(refreshTokenFromDB);

		var user = refreshTokenFromDB.getUser();
		var tokens = jwtUtil.generateTokens(new UserDetailsImpl(user), REFRESH_TOKEN_URL);

		performAuthentication(refreshToken);

		return tokens;
	}

	private void performAuthentication(String refreshToken) {
		RefreshTokenAuthentication refreshTokenAuthentication = new RefreshTokenAuthentication(refreshToken);
		SecurityContextHolder.getContext()
				.setAuthentication(refreshTokenAuthenticationProvider.authenticate(refreshTokenAuthentication));
	}
}
