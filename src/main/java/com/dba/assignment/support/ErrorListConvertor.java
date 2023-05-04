package com.dba.assignment.support;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.dba.assignment.exception.ErrorInfo;
import com.dba.assignment.exception.ErrorList;

/**
 * Helper class which convert the spring specific <code>Errors</code> collection
 * to API specific {@link ErrorList} collection.
 */
public class ErrorListConvertor {

	/**
	 * Instantiates a new error list converter.
	 */
	private ErrorListConvertor() {
	}

//	/**
//	 * Convert to API field error list.
//	 *
//	 * @param errors        the errors
//	 * @param messageSource the message source
//	 * @param locale        the locale
//	 * @return the error list
//	 */
//	public static ErrorList convertToAPIFieldErrorList(Errors errors, MessageSource messageSource, Locale locale) {
//		ErrorList errorList = new ErrorList();
//		for (FieldError fieldError : errors.getFieldErrors()) {
//			String errorMessage = messageSource.getMessage(fieldError, locale);
//			errorList.addError(new ErrorInfo("INVALID_DATA", fieldError.getField(), errorMessage));
//		}
//		return errorList;
//	}

	/**
	 * Convert to API error list.
	 *
	 * @param errorList     the error list
	 * @param messageSource the message source
	 * @param locale        the locale
	 * @return the error list
	 */
	public static ErrorList convertToAPIErrorList(ErrorList errorList/*, MessageSource messageSource, Locale locale*/) {
		List<ErrorInfo> errorInfoList = errorList.getErrors();
		for (ErrorInfo errorInfo : errorInfoList) {
			int index = errorInfoList.indexOf(errorInfo);
			String message = errorInfo.getMessage(); /*!= null ? errorInfo.getMessage()
					: messageSource.getMessage(errorInfo.getErrorCode(), errorInfo.getArgs(), locale);*/
//			if (errorInfo.getField() != null) {
//				errorInfoList.set(index, new ErrorInfo(errorInfo.getErrorCode(), errorInfo.getField(), message));
//			} else {
//				errorInfoList.set(index, new ErrorInfo(errorInfo.getErrorCode(), message));
//			}
			errorInfoList.set(index, new ErrorInfo(errorInfo.getErrorCode(), message));
		}
		return errorList;
	}

}
