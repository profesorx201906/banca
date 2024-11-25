package com.unab.banca.Validation.Exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.unab.banca.Validation.Entity.Error;
import com.unab.banca.Validation.Entity.ErrorResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ValidationHandler {

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(NoSuchElementException exc) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		return buildResponseEntity(httpStatus, exc);
	}

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(DuplicateKeyException exc) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		return buildResponseEntity(httpStatus, exc);
	}

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(IllegalArgumentException exc) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		return buildResponseEntity(httpStatus, exc);
	}

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(MissingRequestHeaderException ex) {
		List<Error> error = new ArrayList<>();
		error.add(new Error("Encabezados", "Falto incluir los encabezados requeridos"));
		return buildResponseEntity(HttpStatus.UNAUTHORIZED,
				new RuntimeException("no incluyo encabezados"), error);
	}

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(EmptyResultDataAccessException exc) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		return buildResponseEntity(httpStatus, new RuntimeException("Registro no encontrado"));
	}

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(ConstraintViolationException exc) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		return buildResponseEntity(httpStatus, new RuntimeException("Existen registro relacionados"));
	}

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(MethodArgumentTypeMismatchException exc) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		// Aplica cuando en el URL se envia un argumento invalido, por ejemplo String
		// por Integer
		return buildResponseEntity(httpStatus, new RuntimeException("Tipo de Argumento invalido"));
	}

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(InvalidDataAccessResourceUsageException ex) {
		List<Error> error = new ArrayList<>();
		error.add(new Error("Datos", "No existen registros"));
		return buildResponseEntity(HttpStatus.UNAUTHORIZED,
				new RuntimeException("No existen registros"), error);
	}

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(UniqueException ex) {
		List<Error> error = new ArrayList<>();
		error.add(ex.getError());
		return buildResponseEntity(HttpStatus.BAD_REQUEST,
				ex, error);
	}

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(NoFoundException ex) {
		List<Error> error = new ArrayList<>();
		error.add(ex.getError());
		return buildResponseEntity(HttpStatus.NOT_FOUND,
				ex, error);
	}

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(NoAuthorizeException ex) {
		List<Error> error = new ArrayList<>();
		error.add(ex.getError());
		return buildResponseEntity(HttpStatus.UNAUTHORIZED,
				ex, error);
	}

	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException ex) {
		List<Error> error = new ArrayList<>();
		error.add(new Error("Body", "El dato no viene adjunto"));
		return buildResponseEntity(HttpStatus.BAD_REQUEST, new RuntimeException("Datos body"), error);
	}

	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(InvalidDataException ex) {
		List<Error> errores = new ArrayList<>();
		ex.getResult().getAllErrors().forEach((error) -> {
			Error err = new Error();
			err.setType("Campo : " + ((FieldError) error).getField());
			err.setMessage(error.getDefaultMessage());
			errores.add(err);
		});
		return buildResponseEntity(HttpStatus.PARTIAL_CONTENT, ex, errores);

	}

	private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus httpStatus, Exception exc) {
		return buildResponseEntity(httpStatus, exc, null);
	}

	private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus httpStatus, Exception exc,
			List<Error> errors) {
		ErrorResponse error = new ErrorResponse();
		error.setMessage("USRMSG-" + exc.getMessage());
		error.setStatus(httpStatus.value());
		error.setTimestamp(new Date());
		error.setErrors(errors);
		return new ResponseEntity<>(error, httpStatus);

	}
}
