package com.warumono;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.warumono.entities.AppClient;
import com.warumono.entities.AppUser;
import com.warumono.repositories.ClientRepository;
import com.warumono.repositories.UserRepository;

@SpringBootApplication
public class SpringBootOAuth2AuthorizationServerTutorialApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SpringBootOAuth2AuthorizationServerTutorialApplication.class, args);
	}

	//*
	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, ClientRepository clientRepository)
	{
		String adminUsername = "admin@me.com";
		String adminPassword = new BCryptPasswordEncoder().encode("admin");
		
		String userUsername = "user@me.com";
		String userPassword = new BCryptPasswordEncoder().encode("user");
		
		String oneClientId = "oneclient";
		String oneClientSecret = new BCryptPasswordEncoder().encode("onesecret");
		
		String twoClientId = "twoclient";
		String twoClientSecret = new BCryptPasswordEncoder().encode("twosecret");
		
		return args ->
		{
			userRepository.save(new AppUser(1L, adminUsername, adminPassword, "USER,ADMIN"));
			userRepository.save(new AppUser(2L, userUsername, userPassword, "USER"));
			
			clientRepository.save(new AppClient(oneClientId, oneClientSecret, "read,write", "authorization_code,refresh_token,implicit,password,client_credentials"));
			clientRepository.save(new AppClient(twoClientId, twoClientSecret, "read", "authorization_code,client_credentials"));
		};
	}
	/*/
	//*/
}
