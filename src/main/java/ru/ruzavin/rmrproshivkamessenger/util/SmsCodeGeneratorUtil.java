package ru.ruzavin.rmrproshivkamessenger.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class SmsCodeGeneratorUtil {

	private static final Random RANDOM = new Random();

	private static final int RANDOM_OFFSET = 10000;

	public static String generateSmsCode() {
		return String.format("%04d", RANDOM.nextInt(RANDOM_OFFSET));
	}
}
