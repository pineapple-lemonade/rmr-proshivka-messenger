package ru.ruzavin.rmrproshivkamessenger.security.util;

import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public class AuthorizationHeaderUtil {

	private static final String AUTH_HEADER_NAME = "Authorization";

	private static final String BEARER_NAME = "Bearer ";

	public static boolean hasAuthorizationToken(HttpServletRequest request) {
		var header = request.getHeader(AUTH_HEADER_NAME);

		return header != null && header.startsWith(BEARER_NAME);
	}

	public static String getToken(HttpServletRequest request) {
		var authHeader = request.getHeader(AUTH_HEADER_NAME);

		return authHeader.substring(BEARER_NAME.length());
	}
}
