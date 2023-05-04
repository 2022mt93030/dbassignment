package com.dba.assignment.support;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.Errors;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.dba.assignment.exception.ApplicationException;
import com.dba.assignment.exception.BadRequestException;
import com.dba.assignment.exception.ErrorInfo;
import com.dba.assignment.exception.ErrorList;
import com.dba.assignment.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/** The message source. */
//	protected final MessageSource messageSource;

	/** The Constant ERROR_MESSAGE. */
	protected static final String ERROR_MESSAGE = "[{}] with error Id, error code and message [{}]";

	/** The Constant UNPROCESSABLE_INPUT_DATA. */
	private static final String UNPROCESSABLE_INPUT_DATA = "unprocessable.input.data";

	/** The Constant ACCESS_DENIED. */
	private static final String ACCESS_DENIED = "access.denied";

	/** The Constant AUTHENTICATION_FAILED. */
	private static final String AUTHENTICATION_FAILED = "authentication.failed";

	/** The Constant UNSUPPORTED_HTTP_METHOD. */
	private static final String UNSUPPORTED_HTTP_METHOD = "unsupported.http.method";

	/** The Constant INTERNAL_SERVER_ERROR. */
	private static final String INTERNAL_SERVER_ERROR = "internal.server.error";

	/** The Constant INVALID_ARGUMENT_TYPE. */
	private static final String INVALID_ARGUMENT_TYPE = "invalid.value.for";

	/** The Constant INVALID_DATA_ACCESS_API_USAGE. */
	private static final String INVALID_DATA_URL_PARAM = "invalid.data.url.param";
	
	/** The Constant PARAMETER_MISSING. */
	public static final String PARAMETER_MISSING = "Parameter Missing";

	/**
	 * Instantiates a new global exception handler.
	 *
	 * @param messageSource the message source
	 */
//	@Autowired
//	public GlobalExceptionHandler(MessageSource messageSource) {
//		this.messageSource = messageSource;
//	}

	/**
	 * Handle application exception.
	 *
	 * @param e      the e
	 * @param locale the locale
	 * @return the response entity
	 */
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<String> handleApplicationException(MethodArgumentNotValidException e/*, Locale locale*/) {
//		Errors errors = e.getBindingResult();
//		ErrorList errorList = ErrorListConvertor.convertToAPIFieldErrorList(errors, this.messageSource, locale);
//		return getErrorResponseEntity(errorList, locale, HttpStatus.BAD_REQUEST, e.getClass().getName());
//	}

	/**
	 * Handler for NotFoundException.
	 *
	 * @param e      the e
	 * @param locale the locale
	 * @return ResponseEntity
	 */
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public ResponseEntity<String> handleApplicationException(ResourceNotFoundException e, Locale locale) {
//		return applicationErrorResponse(e, locale, HttpStatus.NOT_FOUND);
//	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleApplicationException(ResourceNotFoundException e) {
		return applicationErrorResponse(e, HttpStatus.NOT_FOUND);
	}

	/**
	 * Application error response.
	 *
	 * @param e          the e
	 * @param locale     the locale
	 * @param httpStatus the http status
	 * @return the response entity
	 */
//	private ResponseEntity<String> applicationErrorResponse(ApplicationException e, Locale locale,
//			HttpStatus httpStatus) {
//		ErrorList errorList = ErrorListConvertor.convertToAPIErrorList(e.getErrors()/*, this.messageSource, locale*/);
//		return getErrorResponseEntity(errorList, locale, httpStatus, e.getClass().getName());
//	}
	
	private ResponseEntity<String> applicationErrorResponse(ApplicationException e, 
			HttpStatus httpStatus) {
		ErrorList errorList = ErrorListConvertor.convertToAPIErrorList(e.getErrors()/*, this.messageSource, locale*/);
		return getErrorResponseEntity(errorList, httpStatus, e.getClass().getName());
	}

	/**
	 * Gets the error response entity.
	 *
	 * @param errorList  the error list
	 * @param locale     the locale
	 * @param httpStatus the http status
	 * @param className  the class name
	 * @return the error response entity
	 */
//	private ResponseEntity<String> getErrorResponseEntity(ErrorList errorList, Locale locale, HttpStatus httpStatus,
//			String className) {
//		log.debug(ERROR_MESSAGE, className, errorList.toString());
//		return new ResponseEntity<>(errorList.toString(), CommonHttpHeader.getContentLanguageHeaders(locale),
//				httpStatus);
//	}
	
	private ResponseEntity<String> getErrorResponseEntity(ErrorList errorList,  HttpStatus httpStatus,
			String className) {
		log.debug(ERROR_MESSAGE, className, errorList.toString());
		return new ResponseEntity<>(errorList.toString(), httpStatus);
	}

	/**
	 * Handler for ApplicationException Note that the application exceptions are
	 * logged as warnings.
	 *
	 * @param e      the e
	 * @param locale the locale
	 * @return ResponseEntity
	 */
//	@ExceptionHandler(ApplicationException.class)
//	public ResponseEntity<String> handleApplicationException(final ApplicationException e, Locale locale) {
//		return applicationErrorResponse(e, locale, HttpStatus.BAD_REQUEST);
//	}
	
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<String> handleApplicationException(final ApplicationException e) {
		return applicationErrorResponse(e, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handler for BadRequestException.
	 *
	 * @param e      the e
	 * @param locale the locale
	 * @return ResponseEntity
	 */
//	@ExceptionHandler(BadRequestException.class)
//	public ResponseEntity<String> handleApplicationException(final BadRequestException e, Locale locale) {
//		return applicationErrorResponse(e, locale, HttpStatus.BAD_REQUEST);
//	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> handleApplicationException(final BadRequestException e) {
		return applicationErrorResponse(e, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle unprosseasable msg exception.
	 *
	 * @param msgNotReadable the msg not readable
	 * @param locale         the locale
	 * @return the response entity
	 */
//	@ExceptionHandler(value = HttpMessageNotReadableException.class)
//	public ResponseEntity<String> handleUnprosseasableMsgException(HttpMessageNotReadableException msgNotReadable,
//			Locale locale) {
//		ErrorList errorList = buildErrorList(locale, UNPROCESSABLE_INPUT_DATA);
//		return getErrorResponseEntity(errorList, locale, HttpStatus.BAD_REQUEST, msgNotReadable.getClass().getName());
//	}

	/**
	 * Builds the error list.
	 *
	 * @param locale    the locale
	 * @param errorCode the error code
	 * @return the error list
	 */
//	private ErrorList buildErrorList(Locale locale, String errorCode) {
//		ErrorList errorList = new ErrorList();
//		errorList.addError(new ErrorInfo(errorCode));
//		return ErrorListConvertor.convertToAPIErrorList(errorList, this.messageSource, locale);
//	}
	
//	private ErrorList buildErrorList(Locale locale, String errorCode) {
//		ErrorList errorList = new ErrorList();
//		errorList.addError(new ErrorInfo(errorCode));
//		return ErrorListConvertor.convertToAPIErrorList(errorList);
//	}
	
	private ErrorList buildErrorList(String errorCode) {
		ErrorList errorList = new ErrorList();
		errorList.addError(new ErrorInfo(errorCode));
		return ErrorListConvertor.convertToAPIErrorList(errorList);
	}

	/**
	 * Handle access denied exception.
	 *
	 * @param e      the e
	 * @param locale the locale
	 * @return the response entity
	 */
//	@ExceptionHandler(AccessDeniedException.class)
//	public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e, Locale locale) {
//		ErrorList errorList = buildErrorList(locale, ACCESS_DENIED);
//		return getErrorResponseEntity(errorList, locale, HttpStatus.FORBIDDEN, e.getClass().getName());
//	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
		ErrorList errorList = buildErrorList(ACCESS_DENIED);
		return getErrorResponseEntity(errorList, HttpStatus.FORBIDDEN, e.getClass().getName());
	}

	/**
	 * Handle access denied exception.
	 *
	 * @param e      the e
	 * @param locale the locale
	 * @return the response entity
	 */
//	@ExceptionHandler(AuthenticationException.class)
//	public ResponseEntity<String> handleAccessDeniedException(AuthenticationException e, Locale locale) {
//		ErrorList errorList = buildErrorList(locale, AUTHENTICATION_FAILED);
//		return getErrorResponseEntity(errorList, locale, HttpStatus.UNAUTHORIZED, e.getClass().getName());
//	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<String> handleAccessDeniedException(AuthenticationException e) {
		ErrorList errorList = buildErrorList(AUTHENTICATION_FAILED);
		return getErrorResponseEntity(errorList, HttpStatus.UNAUTHORIZED, e.getClass().getName());
	}

	/**
	 * Operation to set the header that should be used when returning exceptions.
	 * Otherwise the Resource specified mime-type will be used.
	 *
	 * @return the content type headers
	 */
	protected HttpHeaders getContentTypeHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	/**
	 * Handle application exception.
	 *
	 * @param e      the e
	 * @param locale the locale
	 * @return the response entity
	 */
//	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//	public ResponseEntity<String> handleApplicationException(HttpRequestMethodNotSupportedException e, Locale locale) {
//		log.warn(ERROR_MESSAGE, e.getClass().getName(), e.getStackTrace());
//		ErrorList errorList = buildErrorList(locale, UNSUPPORTED_HTTP_METHOD);
//		return getErrorResponseEntity(errorList, locale, HttpStatus.METHOD_NOT_ALLOWED, e.getClass().getName());
//	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<String> handleApplicationException(HttpRequestMethodNotSupportedException e) {
		log.warn(ERROR_MESSAGE, e.getClass().getName(), e.getStackTrace());
		ErrorList errorList = buildErrorList(UNSUPPORTED_HTTP_METHOD);
		return getErrorResponseEntity(errorList, HttpStatus.METHOD_NOT_ALLOWED, e.getClass().getName());
	}

	/**
	 * Handle number format exception.
	 *
	 * @param e      the e
	 * @param locale the locale
	 * @return the response entity
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleApplicationException(final MethodArgumentTypeMismatchException e) {
		log.warn(ERROR_MESSAGE, e.getClass().getName(), e.getStackTrace());
		final var error = new ErrorInfo(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getName(),
				INVALID_ARGUMENT_TYPE + e.getName());
		final var errorList = new ErrorList();
		errorList.addError(error);
		return new ResponseEntity<>(errorList.toString(), getContentTypeHeaders(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle invalid data access API usage exception. This handler handles
	 * InvalidDataAccessApiUsageException which occurs for invalid sort param alias
	 * in a query
	 * 
	 * @param e      the e
	 * @param locale the locale
	 * @return the response entity
	 */
//	@ExceptionHandler(value = { InvalidDataAccessApiUsageException.class, PropertyReferenceException.class,
//			DataAccessResourceFailureException.class })
//	public ResponseEntity<String> handleInvalidDataAccessApiUsageException(Exception e, Locale locale) {
//		ErrorList errorList = buildErrorList(locale, INVALID_DATA_URL_PARAM);
//		log.error("Invalid API usage exception Occured ", e.getMessage(), e);
//		return getErrorResponseEntity(errorList, locale, HttpStatus.BAD_REQUEST, e.getClass().getName());
//	}

	/**
	 * Handle exception.
	 *
	 * @param e      the e
	 * @param locale the locale
	 * @return the response entity
	 */
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<String> handleException(Exception e, Locale locale) {
//		ErrorList errorList = buildErrorList(locale, INTERNAL_SERVER_ERROR);
//		log.error(
//				"Unhandled exception [{}] with ID [{}] and message [{}] was caught. Returning HTTP 500 - Internal Server Error",
//				e.getClass().getName(), errorList.getErrorId(), e.getMessage(), e);
//		return getErrorResponseEntity(errorList, locale, HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getName());
//	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		ErrorList errorList = buildErrorList(INTERNAL_SERVER_ERROR);
		log.error(
				"Unhandled exception [{}] with ID [{}] and message [{}] was caught. Returning HTTP 500 - Internal Server Error",
				e.getClass().getName(), errorList.getErrorId(), e.getMessage(), e);
		return getErrorResponseEntity(errorList, HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getName());
	}
	
	
	/**
	 * Handle missing params.
	 *
	 * @param e the e
	 * @return the response entity
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<String> handleMissingParams(final MissingServletRequestParameterException e) {
		log.error(ERROR_MESSAGE, e.getParameterName());
		final var error = new ErrorInfo(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getParameterName(),
				PARAMETER_MISSING);
		final var errorList = new ErrorList();
		errorList.addError(error);
		return new ResponseEntity<>(errorList.toString(), getContentTypeHeaders(), HttpStatus.BAD_REQUEST);
	}

}
