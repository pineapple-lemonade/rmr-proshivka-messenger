package ru.ruzavin.rmrproshivkamessenger.security.provider;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.ruzavin.rmrproshivkamessenger.security.authentication.RefreshTokenAuthentication;
import ru.ruzavin.rmrproshivkamessenger.security.exception.RefreshTokenException;
import ru.ruzavin.rmrproshivkamessenger.security.util.JwtUtil;

@RequiredArgsConstructor
@Slf4j
@Component
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {

	private final JwtUtil jwtUtil;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		var token = (String) authentication.getCredentials();
		try {
			return jwtUtil.buildAuthentication(token);
		} catch (JWTVerificationException e) {
			log.error("error with message {}", e.getMessage(), e);
			throw new RefreshTokenException(e.getMessage(), e);
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return RefreshTokenAuthentication.class.isAssignableFrom(authentication);
	}
}
