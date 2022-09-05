package ru.ruzavin.rmrproshivkamessenger.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.ruzavin.rmrproshivkamessenger.security.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.ruzavin.rmrproshivkamessenger.security.constants.SecurityConstants.*;
import static ru.ruzavin.rmrproshivkamessenger.security.util.AuthorizationHeaderUtil.getToken;
import static ru.ruzavin.rmrproshivkamessenger.security.util.AuthorizationHeaderUtil.hasAuthorizationToken;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (request.getServletPath().equals(AUTHENTICATION_URL)
				|| request.getServletPath().equals(REFRESH_TOKEN_URL)
				|| request.getServletPath().equals(SIGN_UP_URL)) {
			filterChain.doFilter(request, response);
		} else {
			if (hasAuthorizationToken(request)) {
				String jwt = getToken(request);

				try {
					Authentication authenticationToken = jwtUtil.buildAuthentication(jwt);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				} catch (JWTVerificationException e) {
					log.error("error with message {}", e.getMessage(), e);
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

}
