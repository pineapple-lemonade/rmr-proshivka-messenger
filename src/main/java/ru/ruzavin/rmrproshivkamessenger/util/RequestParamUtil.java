package ru.ruzavin.rmrproshivkamessenger.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ruzavin.rmrproshivkamessenger.config.PageConfigurationProperties;

@Component
@RequiredArgsConstructor
public class RequestParamUtil {

	private final PageConfigurationProperties pageConfigurationProperties;

	public Integer handlePageSize(Integer pageSize) {
		return pageSize != null ? pageSize : pageConfigurationProperties.getSize();
	}

	public Integer handlePageNumber(Integer pageNumber) {
		return pageNumber != null ? pageNumber : pageConfigurationProperties.getNumber();
	}
}
