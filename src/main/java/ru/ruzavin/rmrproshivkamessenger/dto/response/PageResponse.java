package ru.ruzavin.rmrproshivkamessenger.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(builderMethodName = "pageResponseBuilder")
public class PageResponse<T extends Collection> extends AbstractResponse {

	@Schema(name = "page", description = "current page")
	Integer page;

	@Schema(name = "size", description = "current size")
	Integer size;

	@Schema(name = "total_elements", description = "amount of available elements in DB")
	Long totalElements;

	@Schema(name = "total_pages", description = "amount of available pages")
	Integer totalPages;

	@Schema(name = "data", description = "data of response")
	T data;
}
