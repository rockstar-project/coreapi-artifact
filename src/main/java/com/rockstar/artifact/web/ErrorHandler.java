package com.rockstar.artifact.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.rockstar.artifact.model.Error;
import com.rockstar.artifact.model.ErrorCode;
import com.rockstar.artifact.model.InvalidSchemaException;
import com.rockstar.artifact.model.NotFoundException;

@ControllerAdvice
public class ErrorHandler {

	@Inject private MessageSource messageSource;
	
	public ErrorHandler() {
	}
	
	@ExceptionHandler({NotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody Error handleNotFound(NotFoundException notfoundex) {
		ErrorCode errorCode = ErrorCode.NotFound;
		return new Error(errorCode.name(), notfoundex.getMessage());
	}
	
	
	@ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public @ResponseBody Error handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException mediatypenotacceptableex) {
		ErrorCode errorCode = ErrorCode.MediaTypeNotAcceptable;
		return new Error(errorCode.name(), this.getText(errorCode, mediatypenotacceptableex.getSupportedMediaTypes()));
	}
	
	@ExceptionHandler({MissingServletRequestParameterException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody Error handleMissingRequestParameterException(MissingServletRequestParameterException missingparameterex) {
		ErrorCode errorCode = ErrorCode.MissingOrInvalidParameter;
		return new Error(errorCode.name(), this.getText(errorCode, missingparameterex.getParameterName()));
	}
	
	@ExceptionHandler({InvalidSchemaException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody Error handleInvalidSchemaException(InvalidSchemaException invalidschemaex) {
		ErrorCode errorCode = ErrorCode.ValidationError;
		return new Error(errorCode.name(), this.getText(errorCode, invalidschemaex.getMessage()));
	}
	
	@ExceptionHandler({ServletRequestBindingException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody Error handleRequestBindingException(ServletRequestBindingException requestbindingex, HttpServletResponse response) throws IOException {
		ErrorCode errorCode = ErrorCode.MissingOrInvalidParameter;
		return new Error(errorCode.name(), this.getText(errorCode));
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody Error handleNotValid(MethodArgumentNotValidException notvalidex) {
		String fieldErrorCode = null;
		String validationMessage = null;
		ErrorCode errorCode = ErrorCode.ValidationError;
		FieldError fieldError = null;
		Error errorDto = null;
		
		if (notvalidex.getBindingResult().hasFieldErrors()) {
			fieldError = notvalidex.getBindingResult().getFieldError();
			fieldErrorCode = String.format("%s.%s.%s", fieldError.getCode(), fieldError.getObjectName(), fieldError.getField());
			validationMessage = this.getText(fieldErrorCode, fieldError.getArguments());
			errorDto = new Error(errorCode.name(), validationMessage);
		}
		return errorDto;
	}
	
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody Error handleInternalServer(Exception exception) {
		exception.printStackTrace();
		ErrorCode errorCode = ErrorCode.InternalError;
		return new Error(errorCode.name(), this.getText(errorCode, exception.getMessage()));
	}
	
	@ExceptionHandler({HttpMessageNotReadableException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody Error handleMessageNotReadableException(HttpMessageNotReadableException messagenotreadableex) {
		Error errorDto = null;
		String unrecognizedPropertyName = null;
		Throwable mostSpecificCause = messagenotreadableex.getMostSpecificCause();
		UnrecognizedPropertyException unrecognizedPropertyException = null;
		
		if (mostSpecificCause != null) {
			if (mostSpecificCause instanceof UnrecognizedPropertyException) {
				unrecognizedPropertyException = (UnrecognizedPropertyException) mostSpecificCause;
				unrecognizedPropertyName = unrecognizedPropertyException.getPropertyName();
				errorDto = new Error(ErrorCode.UnrecognizedProperty.name(), this.getText(ErrorCode.UnrecognizedProperty, unrecognizedPropertyName));
			} else {
				errorDto = new Error(ErrorCode.UnreadableMessage.name(), this.getText(ErrorCode.UnreadableMessage));
			}
		}
		return errorDto;
	}
	
	private String getText(ErrorCode errorCode, Object... arguments) {
		return this.getText(errorCode.name(), arguments);
	}
	
	private String getText(String error, Object...arguments) {
		return this.messageSource.getMessage(error, arguments, LocaleContextHolder.getLocale());
	}
	
}
