package com.dba.assignment.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The Class ErrorList.
 */

public class ErrorList {

	/** The errors. */
	private List<ErrorInfo> errors = new ArrayList<>();

	/** The error id. */
	private String errorId;

	/**
	 * Constructor.
	 */
	public ErrorList() {
		this.errorId = UUID.randomUUID().toString();
	}

	/**
	 * Add individual error into error list.
	 *
	 * @param error the error
	 */
	public void addError(final ErrorInfo error) {
		errors.add(error);
	}

	/**
	 * returns list of errors.
	 * 
	 * @return List<ErrorInfo>
	 */
	public List<ErrorInfo> getErrors() {
		return errors;
	}

	/**
	 * returns error count.
	 * 
	 * @return int
	 */
	public int getErrorCount() {
		return errors.size();
	}

	/**
	 * Get an auto-generated unique error Id specific to this error object.
	 *
	 * @return Unique Error ID
	 */
	public String getErrorId() {
		return errorId;
	}

	@Override
	public String toString() {
		return "{ \"errors\": [" + errorInfoString() + "], \"errorId\":\"" + errorId + "\", \"errorCount\":"
				+ getErrorCount() + "}";
	}

	public String errorInfoString() {
		List<String> errorsInString = errors.stream().map(ErrorInfo::toString)
				.collect(Collectors.toList());
		return String.join(",", errorsInString);

	}

}
