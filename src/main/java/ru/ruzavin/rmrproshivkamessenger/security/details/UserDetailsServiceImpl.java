package ru.ruzavin.rmrproshivkamessenger.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ruzavin.rmrproshivkamessenger.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		return new UserDetailsImpl(userRepository.getUserEntityByName(name)
				.orElseThrow(() -> new UsernameNotFoundException("user with name ".concat(name).concat(" not found"))));
	}
}
