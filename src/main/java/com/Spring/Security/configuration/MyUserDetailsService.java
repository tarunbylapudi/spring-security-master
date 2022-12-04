package com.Spring.Security.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Spring.Security.entity.UserModel;
import com.Spring.Security.repository.UserRepository;
import com.Spring.Security.entity.MyUserDetails;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	
	@Autowired
	UserRepository repository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserModel> user=repository.findByUsername(username);
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("Username not found");
		}
		return new MyUserDetails(user.get(0));
	}

}
