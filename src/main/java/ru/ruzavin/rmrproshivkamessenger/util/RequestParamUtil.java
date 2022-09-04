package ru.ruzavin.rmrproshivkamessenger.util;

import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

@UtilityClass
public class RequestParamUtil {
	@Value("${default.page.size}")
	private Integer defaultPageSizeValue;

	@Value("${default.page.number}")
	private Integer defaultPageNumberValue;

	private static Integer defaultPageSize;
	private static Integer defaultPageNumber;

	@PostConstruct
	private void requestParamUtil() {
		defaultPageSize = defaultPageSizeValue;
		defaultPageNumber = defaultPageNumberValue;
	}

	public static void handlePageSizeAndPageNumber(Integer pageSize, Integer pageNumber) {
		pageNumber = pageNumber != null ? pageNumber : defaultPageNumber;
		pageSize = pageSize != null ? pageSize : defaultPageSize;
	}
}
