package com.cts.exceptions;

public class InvalidAuditTypeException extends RuntimeException {
	public InvalidAuditTypeException(String message) {
		super(message);
	}
}
