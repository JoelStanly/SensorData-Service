package com.aqms.sensordataservice.advice;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.aqms.sensordataservice.FileUtil;
import com.aqms.sensordataservice.controller.SensorDataController;
import com.aqms.sensordataservice.exception.ResourceNotFoundException;

@RestControllerAdvice
public class Handler {

	// Controller Advice Logging
	Logger log = FileUtil.getLogger(SensorDataController.class);

	// Bad Request Handling when the data doesn't pass the validations
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidInput(MethodArgumentNotValidException ex) {

		Map<String, String> errorMap = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		log.info("Bad Data given with the following errors:\n\n" + errorMap);

		return errorMap;

	}

	// Resource Not Found Exception Handling so that when data doesn't exists in the
	// DB
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ResourceNotFoundException.class)
	public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("errorMessage", ex.getMessage());
		log.info("No Data found with the following errors:\n\n" + errorMap);
		return errorMap;

	}

	// Internal Server Error when the sensor Plot data returned an error
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(InternalServerError.class)
	public Map<String, String> handleInternalServerError(InternalServerError ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("Error", "Occured in SensorPlot");
		return errorMap;
	}

	// Bad Request When a value cannot be contained in the required data type
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Map<String, String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("errorMessage", ex.getMessage());
		log.info("Not Readable Exception with the following errors:\n\n" + errorMap);
		return errorMap;
	}

	// Generic Exception Handler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public String genericExceptionHandler(Exception ex) {
		log.info("Generic Exception");
		return "Generic Exception: Request Failed";
	}

}
