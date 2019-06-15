package it.uniroma3.siw.demospring.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.uniroma3.siw.demospring.model.User;
import it.uniroma3.siw.demospring.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		int a = 0;
		if (user == null) {
			throw new UsernameNotFoundException("Username not found");
		}
		
		org.springframework.security.core.userdetails.User ud = 
				new org.springframework.security.core.userdetails.User(
						username,
						user.getPassword(),
						Collections.singleton(new SimpleGrantedAuthority("user")));		
		
		return ud;
	}

	public User salva(User user) {
		return userRepository.save(user);

	}
}