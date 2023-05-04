package com.dba.assignment.exception;

/**
 * The Class ApplicationException.
 */
public class ApplicationException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The errors. */
	private ErrorList errors = new ErrorList();

	/**
	 * Constructor with a single ErrorInfo object.
	 *
	 * @param error the error
	 */
	public ApplicationException(final ErrorInfo error) {
		super();
		errors.addError(error);
	}

	/**
	 * Constructor with a single ErrorInfo object and the cause.
	 *
	 * @param error the error
	 * @param cause the cause
	 */
	public ApplicationException(final ErrorInfo error, final Throwable cause) {
		super(cause);
		errors.addError(error);
	}

	/**
	 * Constructor with just the Errors object.
	 *
	 * @param errors the errors
	 */
	public ApplicationException(final ErrorList errors) {
		super();
		this.errors = errors;
	}

	/**
	 * Constructor with the Errors object and the cause.
	 *
	 * @param errors the errors
	 * @param cause  the cause
	 */
	public ApplicationException(final ErrorList errors, final Throwable cause) {
		super(cause);
		this.errors = errors;
	}

	/**
	 * Constructor with just the Errors object.
	 *
	 * @param message the message
	 * @param errors  the errors
	 */
	public ApplicationException(final String message, final ErrorList errors) {
		super(message);
		this.errors = errors;
	}

	/**
	 * Constructor with just the Errors object.
	 *
	 * @param message - the message String
	 * @param errors  the errors
	 * @param cause   - the cause
	 */
	public ApplicationException(final String message, final ErrorList errors, final Throwable cause) {
		super(message, cause);
		this.errors = errors;
	}

	/**
	 * Gets the full list of errors that are part of the Exception.
	 * 
	 * @return list of errors
	 */
	public ErrorList getErrors() {
		return errors;
	}

}
