package com.dba.assignment.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.NoArgsConstructor;

/**
 * The Class ErrorInfo.
 */
@NoArgsConstructor
public class ErrorInfo {

	/** The error code. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String errorCode;

	/** The message. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;

	/** The field. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String field;
	
	/** The args. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object[] args;

	/**
	 * Constructor.
	 *
	 * @param errorCode the error code
	 * @param messages  the messages
	 */
	public ErrorInfo(final String errorCode, final String messages) {
		this.errorCode = errorCode;
		this.message = messages;
	}

	/**
	 * Instantiates a new error info.
	 *
	 * @param errorCode the error code
	 * @param field the field
	 * @param messages the messages
	 */
	public ErrorInfo(String errorCode, String field, String messages) {
		this(errorCode, messages);
		this.field = field;
	}

	/**
	 * Instantiates a new error info.
	 *
	 * @param errorCode the error code
	 * @param args the args
	 */
	public ErrorInfo(String errorCode, Object[] args) {
		this.errorCode = errorCode;
		this.args = args;
	}

	
	public ErrorInfo(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * Application specific error code used to group types of messages.
	 * 
	 * @return Error Code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Message description of the error.
	 *
	 * @return Description message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * Gets the args.
	 *
	 * @return the args
	 */
	public Object[] getArgs() {
		return args;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuilder errorJson = new StringBuilder("{ \"errorCode\": \"" + errorCode + "\", \"message\":\"" + message + "\"");
		errorJson.append(field != null ? ", \"field\":\"" + field + "\" }" : "}");
		return errorJson.toString();
	}

}
