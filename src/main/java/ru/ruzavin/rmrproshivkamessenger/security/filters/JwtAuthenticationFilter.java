package ru.ruzavin.rmrproshivkamessenger.security.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import ru.ruzavin.rmrproshivkamessenger.dto.model.TokenPairModel;
import ru.ruzavin.rmrproshivkamessenger.dto.request.LoginRequest;
import ru.ruzavin.rmrproshivkamessenger.dto.response.SendSmsResponse;
import ru.ruzavin.rmrproshivkamessenger.security.details.UserDetailsImpl;
import ru.ruzavin.rmrproshivkamessenger.security.service.SmsService;
import ru.ruzavin.rmrproshivkamessenger.security.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static ru.ruzavin.rmrproshivkamessenger.security.constants.SecurityConstants.AUTHENTICATION_URL;

@Component
@DependsOn("securityConfig")
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final ObjectMapper objectMapper;

	private final JwtUtil jwtUtil;

	private final SmsService smsService;

	public JwtAuthenticationFilter(ObjectMapper objectMapper,
	                               JwtUtil jwtUtil,
	                               AuthenticationConfiguration authenticationConfiguration,
	                               SmsService smsService) throws Exception {
		setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
		this.setFilterProcessesUrl(AUTHENTICATION_URL);
		this.objectMapper = objectMapper;
		this.jwtUtil = jwtUtil;
		this.smsService = smsService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		LoginRequest loginRequest = getLoginRequest(request);

		String username = loginRequest.getName();
		username = (username != null) ? username : "";
		username = username.trim();
		String password = loginRequest.getPassword();
		password = (password != null) ? password : "";

		if (StringUtils.isEmpty(loginRequest.getSmsCode())) {
			SendSmsResponse sendSmsResponse = smsService.sendSms(loginRequest.getPhone());
			log.info("sms was sent {}", sendSmsResponse);

			throw new AuthenticationServiceException("Request don't have sms code");
		}

		if (smsService.checkCode(loginRequest.getSmsCode(), loginRequest.getPhone())) {
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
			setDetails(request, authRequest);

			return this.getAuthenticationManager().authenticate(authRequest);
		} else {
			throw new AuthenticationServiceException("Sms don't verified");
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		writeTokens(request, response, (UserDetailsImpl) authResult.getPrincipal());
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

	private void writeTokens(HttpServletRequest request, HttpServletResponse response, UserDetailsImpl userDetails) throws IOException {
		response.setContentType("application/json");

		TokenPairModel tokens = jwtUtil.generateTokens(userDetails, request.getRequestURL().toString());

		objectMapper.writeValue(response.getOutputStream(), tokens);
	}

	private LoginRequest getLoginRequest(HttpServletRequest request) {
		LoginRequest loginRequest;
		try {
			loginRequest = objectMapper.readValue(getRequestBody(request), LoginRequest.class);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
		return loginRequest;
	}

	private String getRequestBody(HttpServletRequest request) {
		String requestBody;
		try {
			requestBody = request.getReader()
					.lines()
					.collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return requestBody;
	}

}
