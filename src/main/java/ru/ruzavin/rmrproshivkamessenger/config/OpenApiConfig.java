package ru.ruzavin.rmrproshivkamessenger.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.ruzavin.rmrproshivkamessenger.security.constants.SecurityConstants.AUTHENTICATION_URL;

@Configuration
public class OpenApiConfig {
	private static final String BEARER_AUTH_SCHEMA_NAME = "bearerAuth";
	private static final String STRING_TYPE = "string";

	@Bean
	public OpenAPI customOpenAPI() {

		return new OpenAPI()
				.addSecurityItem(buildSecurity())
				.paths(buildAuthenticationPath())
				.components(buildComponents())
				.info(buildInfo());
	}

	private Paths buildAuthenticationPath() {
		return new Paths()
				.addPathItem(AUTHENTICATION_URL, buildAuthenticationPathItem());
	}

	private PathItem buildAuthenticationPathItem() {
		return new PathItem().post(
				new Operation()
						.addTagsItem("Authentication")
						.requestBody(buildAuthenticationRequestBody())
						.responses(buildAuthenticationResponses()));

	}

	private RequestBody buildAuthenticationRequestBody() {
		return new RequestBody().content(
				new Content()
						.addMediaType("application/json",
								new MediaType()
										.schema(new Schema<>()
												.$ref("EmailAndPassword"))));
	}

	private ApiResponses buildAuthenticationResponses() {
		return new ApiResponses()
				.addApiResponse("200",
						new ApiResponse()
								.content(new Content()
										.addMediaType("application/json",
												new MediaType()
														.schema(new Schema<>()
																.$ref("Tokens")))));
	}

	private SecurityRequirement buildSecurity() {
		return new SecurityRequirement().addList(BEARER_AUTH_SCHEMA_NAME);
	}

	private Info buildInfo() {
		return new Info().title("RMR").version("0.1");
	}

	private Components buildComponents() {
		Schema<?> emailAndPassword = new Schema<>()
				.type("object")
				.description("Name and password")
				.addProperty("name", new Schema<>().type(STRING_TYPE))
				.addProperty("password", new Schema<>().type(STRING_TYPE));

		Schema<?> tokens = new Schema<>()
				.type("object")
				.description("Access and Refresh tokens")
				.addProperty("accessToken", new Schema<>().type(STRING_TYPE).description("access token"))
				.addProperty("refreshToken", new Schema<>().type(STRING_TYPE).description("refresh token"));

		SecurityScheme securityScheme = new SecurityScheme()
				.name(BEARER_AUTH_SCHEMA_NAME)
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT");

		return new Components()
				.addSchemas("EmailAndPassword", emailAndPassword)
				.addSchemas("Tokens", tokens)
				.addSecuritySchemes(BEARER_AUTH_SCHEMA_NAME, securityScheme);
	}

}
