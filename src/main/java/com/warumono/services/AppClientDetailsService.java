package com.warumono.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import com.warumono.entities.AppClient;
import com.warumono.exceptions.ClientIdNotFoundException;
import com.warumono.repositories.ClientRepository;

@Service
public class AppClientDetailsService implements ClientDetailsService
{
	@Autowired
	private ClientRepository clientRepository;
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException
	{
		Optional<AppClient> client = clientRepository.findOneById(clientId);
		
		if(client.isPresent())
		{
			AppClient appClient = client.get();
			
			BaseClientDetails clientDetails = new BaseClientDetails();
			clientDetails.setClientId(clientId);
			clientDetails.setClientSecret(appClient.getSecret());
			clientDetails.setScope(appClient.getScopes());
			clientDetails.setAuthorizedGrantTypes(appClient.getGrantTypes());
			
			return clientDetails;
		}
		else
		{
			throw new ClientIdNotFoundException("Not found client with id '".concat(clientId).concat("'"));
		}
	}
}
