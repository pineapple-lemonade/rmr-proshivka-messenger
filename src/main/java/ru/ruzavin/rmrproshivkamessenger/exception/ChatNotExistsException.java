package ru.ruzavin.rmrproshivkamessenger.exception;

public class ChatNotExistsException extends RuntimeException {
	public ChatNotExistsException() {
		super("chat doesn't exists");
	}
}
