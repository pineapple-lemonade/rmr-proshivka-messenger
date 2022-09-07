package ru.ruzavin.rmrproshivkamessenger.exception;

public class FriendAlreadyAddedException extends RuntimeException {
	public FriendAlreadyAddedException(String phone) {
		super("friend with phone " + phone + " already added");
	}
}
