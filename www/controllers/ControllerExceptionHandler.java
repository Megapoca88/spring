package sv.edu.udb.www.controllers;



import sv.edu.udb.www.transversal.Message;
import sv.edu.udb.www.transversal.Response;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@ControllerAdvice
public class ControllerExceptionHandler {

	
protected static final Logger logger = LogManager.getLogger(ControllerExceptionHandler.class);
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	
	Response<Object> ExceptionHandler(Exception e){
		String idFailure = UUID.randomUUID().toString();
		
		Response<Object> response = new Response<>();
		response.setMessage(String.format(Message.ErrorInesperado, idFailure));
		response.setIsWarning(false);
		response.setIsSuccess(false);
		
		logger.error(e.getMessage(), e);
		
		return response;
	}
	
	
}
