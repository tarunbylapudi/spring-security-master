package com.Spring.Security.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.Spring.Security.entity.UserModel;
import com.Spring.Security.repository.UserRepository;

@Component
public class UsernmePwdAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository repository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		List<UserModel> user = repository.findByUsername(username);
		if (user.size() > 0) {
			if (passwordEncoder.matches(password, user.get(0).getPassword())) {
				
				return new UsernamePasswordAuthenticationToken(username, password, getGrantedAuthorities(user.get(0).getRoles()));
			} else {
				throw new BadCredentialsException("Wrong Password");
			}

		} else {
			throw new BadCredentialsException("User is not Registred");
		}

	}
	
	private List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : roles) {
        	grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return grantedAuthorities;
    }
	
	

	@Override
	public boolean supports(Class<?> authenticationType) {
		return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
	}

}
