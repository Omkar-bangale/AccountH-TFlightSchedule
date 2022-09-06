package com.flightschedule.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.flightschedule.app.services.SearchFlightException;

@ControllerAdvice
public class CustomExceptionController {

	@ExceptionHandler(value=SearchFlightException.class)
	public ResponseEntity<Object> exception(SearchFlightException searchfailed)
	{
		return new ResponseEntity<>(SearchFlightException.message, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	@ExceptionHandler(value=TokenValidationException.class)
	public ResponseEntity<Object> exception(TokenValidationException searchfailed)
	{
		return new ResponseEntity<>(TokenValidationException.message, HttpStatus.UNAUTHORIZED);
		
	}
}
