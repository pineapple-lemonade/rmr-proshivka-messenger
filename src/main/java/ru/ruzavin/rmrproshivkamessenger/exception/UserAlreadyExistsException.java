package ru.ruzavin.rmrproshivkamessenger.exception;

public class UserAlreadyExistsException extends RuntimeException {
	public UserAlreadyExistsException() {
		super("user with such phone already exists");
	}
}
