package ru.ruzavin.rmrproshivkamessenger.exception;

public class RefreshTokenNotExistsException extends RuntimeException {
	public RefreshTokenNotExistsException(String message) {
		super(message);
	}
}
