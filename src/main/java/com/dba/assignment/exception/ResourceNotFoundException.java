package com.dba.assignment.exception;

/**
 * Exception used when a request could not be fulfilled because the data was
 * being searched/looked for was not found.
 * 
 */
public class ResourceNotFoundException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with a single ErrorInfo object
	 * 
	 * @param error
	 */
	public ResourceNotFoundException(ErrorInfo error) {
		super(error);
	}

	/**
	 * Constructor for a single ErrorInfo object with a cause
	 * 
	 * @param error
	 * @param cause
	 */
	public ResourceNotFoundException(ErrorInfo error, Throwable cause) {
		super(error, cause);
	}

	/**
	 * Constructor with an errors object and cause
	 * 
	 * @param errors - the Errors object
	 */
	public ResourceNotFoundException(ErrorList errors) {
		super(errors);
	}

	/**
	 * Constructor with the Errors object and the cause
	 * 
	 * @param errors
	 * @param cause
	 */
	public ResourceNotFoundException(ErrorList errors, Throwable cause) {
		super(errors, cause);
	}

	/**
	 * Constructor with an errors object and cause
	 * 
	 * @param message - String message
	 * @param errors  - the Errors object
	 */
	public ResourceNotFoundException(String message, ErrorList errors) {
		super(message, errors);
	}

	/**
	 * Constructor with an errors object and cause
	 * 
	 * @param message - String message
	 * @param errors  - the Errors object
	 * @param cause   - the cause
	 */
	public ResourceNotFoundException(String message, ErrorList errors, Throwable cause) {
		super(message, errors, cause);
	}

}
