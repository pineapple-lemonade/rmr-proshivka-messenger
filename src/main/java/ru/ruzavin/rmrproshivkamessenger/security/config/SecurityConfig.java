package ru.ruzavin.rmrproshivkamessenger.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.ruzavin.rmrproshivkamessenger.security.filters.JwtAuthenticationFilter;
import ru.ruzavin.rmrproshivkamessenger.security.filters.JwtAuthorizationFilter;

import static ru.ruzavin.rmrproshivkamessenger.security.constants.SecurityConstants.*;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsServiceImpl;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationProvider refreshTokenAuthenticationProvider;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	private final JwtAuthorizationFilter jwtAuthorizationFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(refreshTokenAuthenticationProvider);
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests().antMatchers(REFRESH_TOKEN_URL).permitAll();
		http.authorizeRequests().antMatchers(SIGN_UP_URL).permitAll();
		http.authorizeRequests().antMatchers(AUTHENTICATION_URL).permitAll();

		http.addFilter(jwtAuthenticationFilter);
		http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
