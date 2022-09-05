package ru.ruzavin.rmrproshivkamessenger.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.ruzavin.rmrproshivkamessenger.security.filters.JwtAuthenticationFilter;
import ru.ruzavin.rmrproshivkamessenger.security.filters.JwtAuthorizationFilter;

import static ru.ruzavin.rmrproshivkamessenger.security.constants.SecurityConstants.*;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	private final UserDetailsService userDetailsServiceImpl;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationProvider refreshTokenAuthenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
	                                               JwtAuthenticationFilter jwtAuthenticationFilter,
	                                               JwtAuthorizationFilter jwtAuthorizationFilter) throws Exception {
		httpSecurity.csrf().disable();
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		httpSecurity.authorizeRequests().antMatchers(REFRESH_TOKEN_URL).permitAll();
		httpSecurity.authorizeRequests().antMatchers(SIGN_UP_URL).permitAll();
		httpSecurity.authorizeRequests().antMatchers(AUTHENTICATION_URL).permitAll();

		httpSecurity.addFilter(jwtAuthenticationFilter);
		httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

	@Autowired
	public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
		builder.authenticationProvider(refreshTokenAuthenticationProvider);
		builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
	}
}
