package com.warumono.exceptions;

import org.springframework.security.access.AuthorizationServiceException;

public class ClientIdNotFoundException extends AuthorizationServiceException
{
	private static final long serialVersionUID = 1L;

	public ClientIdNotFoundException(String msg)
	{
		super(msg);
	}

	public ClientIdNotFoundException(String msg, Throwable cause)
	{
		super(msg, cause);
	}

}
