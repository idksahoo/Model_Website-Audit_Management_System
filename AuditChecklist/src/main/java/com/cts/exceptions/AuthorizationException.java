package com.cts.exceptions;

@SuppressWarnings("serial")
public class AuthorizationException extends RuntimeException {

	public AuthorizationException(String message) {
		super(message);
	}

}
