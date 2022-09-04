package ru.ruzavin.rmrproshivkamessenger.security.details;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ruzavin.rmrproshivkamessenger.entity.UserEntity;
import ru.ruzavin.rmrproshivkamessenger.entity.enums.UserState;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

	@Getter
	private final UserEntity user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !user.getState().equals(UserState.BANNED);
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !user.getState().equals(UserState.BANNED);
	}

	@Override
	public boolean isEnabled() {
		return user.getState().equals(UserState.CONFIRMED);
	}
}
