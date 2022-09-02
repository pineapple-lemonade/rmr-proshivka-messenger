package ru.ruzavin.rmrproshivkamessenger.exception;

public class ChatMismatchException extends RuntimeException {
	public ChatMismatchException() {
		super("chat doesn't belong to issuer");
	}
}
