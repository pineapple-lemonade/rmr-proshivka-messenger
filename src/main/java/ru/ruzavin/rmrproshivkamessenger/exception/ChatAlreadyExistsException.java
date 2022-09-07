package ru.ruzavin.rmrproshivkamessenger.exception;

public class ChatAlreadyExistsException extends RuntimeException {
	public ChatAlreadyExistsException() {
		super("chat already exists");
	}
}
