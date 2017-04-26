package com.warumono.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.warumono.entities.AppUser;
import com.warumono.repositories.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Optional<AppUser> user = userRepository.findOneByUsername(username);
		
		if(user.isPresent())
		{
			AppUser appUser = user.get();
			
			return new User(appUser.getUsername(), appUser.getPassword(), true, true, true, true, appUser.getAuthorities());
		}
		else
		{
			throw new UsernameNotFoundException("Not found user with username '".concat(username).concat("'"));
		}
	}
}
