package com.aashif.kitchen.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.aashif.kitchen.payloads.ApiResponse;

public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse res=new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(res, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	
	public ResponseEntity<Map<String, String>> handleMethodArgNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> res = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((err)->{
			String fName = ((FieldError)err).getField();
			String msg = err.getDefaultMessage();
			res.put(fName, msg);
		});
		
		return new ResponseEntity<Map<String,String>>(res,HttpStatus.BAD_REQUEST);
		}
}
