package ru.ruzavin.rmrproshivkamessenger.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.ruzavin.rmrproshivkamessenger.dto.model.TokenPairModel;
import ru.ruzavin.rmrproshivkamessenger.entity.RefreshTokenEntity;
import ru.ruzavin.rmrproshivkamessenger.repository.RefreshTokenRepository;
import ru.ruzavin.rmrproshivkamessenger.repository.UserRepository;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	@Value("${application.token.access.expires.time}")
	private long accessTokenExpiresTime;

	@Value("${application.token.refresh.expires.time}")
	private long refreshTokenExpiresTime;

	@Value("${jwt.secret}")
	private String secret;

	private final UserRepository userRepository;

	private final RefreshTokenRepository refreshTokenRepository;

	private final UserDetailsService userDetailsServiceImpl;

	public TokenPairModel generateTokens(UserDetailsImpl userDetails, String issuer) {
		Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));

		String accessToken = JWT.create()
				.withSubject(userDetails.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiresTime))
				.withIssuer(issuer)
				.sign(algorithm);

		String refreshToken = JWT.create()
				.withSubject(userDetails.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiresTime))
				.withIssuer(issuer)
				.sign(algorithm);

		TokenPairModel tokens = TokenPairModel.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();

		saveRefreshTokenInDB(userDetails, tokens);

		return tokens;
	}

	public Authentication buildAuthentication(String token) {
		ParsedToken parsedToken = parse(token);
		UserDetailsImpl userDetails =
				(UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(parsedToken.getEmail());

		return new UsernamePasswordAuthenticationToken(userDetails,
				userDetails.getPassword(),
				Collections.emptyList());
	}

	private ParsedToken parse(String token) throws JWTVerificationException {
		Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));

		JWTVerifier jwtVerifier = JWT.require(algorithm).build();

		DecodedJWT decodedJWT = jwtVerifier.verify(token);

		String email = decodedJWT.getSubject();

		return ParsedToken.builder()
				.email(email)
				.build();
	}

	public void saveRefreshTokenInDB(UserDetailsImpl userDetails, TokenPairModel tokens) {
		var user = userRepository.findUserEntityByName(userDetails.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("user with name "
						.concat(userDetails.getUsername()).concat(" not found")));

		var refreshToken = RefreshTokenEntity.builder()
				.content(tokens.getRefreshToken())
				.user(user)
				.build();

		refreshTokenRepository.save(refreshToken);
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	private static class ParsedToken {
		private String email;
		private String role;
	}


}
