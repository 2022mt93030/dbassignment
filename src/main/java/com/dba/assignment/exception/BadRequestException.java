package com.dba.assignment.exception;

/**
 * The Class BadRequestException.
 */
public class BadRequestException extends ApplicationException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with a single ErrorInfo object.
	 *
	 * @param error the error
	 */
	public BadRequestException(final ErrorInfo error) {
		super(error);
	}

	/**
	 * Constructor for a single ErrorInfo object with a cause.
	 *
	 * @param error the error
	 * @param cause the cause
	 */
	public BadRequestException(final ErrorInfo error, final Throwable cause) {
		super(error, cause);
	}

	/**
	 * Constructor with an errors object and cause.
	 *
	 * @param errors - the Errors object
	 */
	public BadRequestException(final ErrorList errors) {
		super(errors);
	}

	/**
	 * Constructor with the Errors object and the cause.
	 *
	 * @param errors the errors
	 * @param cause  the cause
	 */
	public BadRequestException(final ErrorList errors, final Throwable cause) {
		super(errors, cause);
	}

	/**
	 * Constructor with an errors object and cause.
	 *
	 * @param message - String message
	 * @param errors  - the Errors object
	 */
	public BadRequestException(final String message, final ErrorList errors) {
		super(message, errors);
	}

	/**
	 * Constructor with an errors object and cause.
	 *
	 * @param message - String message
	 * @param errors  - the Errors object
	 * @param cause   - the cause
	 */
	public BadRequestException(final String message, final ErrorList errors, final Throwable cause) {
		super(message, errors, cause);
	}

}
