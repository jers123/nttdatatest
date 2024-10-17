package org.nttdatatest.test.exception;

import static org.nttdatatest.test.utils.Constants.HTTP_MESSAGE1;
import static org.nttdatatest.test.utils.Constants.HTTP_MESSAGE2;
import static org.nttdatatest.test.utils.SystemConstants.answer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nttdatatest.test.utils.ReplyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApiExceptionHandler {
	@Autowired
	private ReplyMessage replyMessage;

	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	@ResponseBody
	public ResponseEntity<ReplyMessage> httpRequestMethodException(HttpRequestMethodNotSupportedException exception, WebRequest webRequest) {
		List<String> messages = new ArrayList<>();
		messages.add(HTTP_MESSAGE1 + supportedMethods(exception) + HTTP_MESSAGE2 + exception.getMethod());
		replyMessage.setUri(webRequest.getDescription(false).replace("uri=",""));
		replyMessage.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
		replyMessage.setError(true);
		replyMessage.setMessage(messages);
		replyMessage.setResponse(null);
		replyMessage.setDate(LocalDateTime.now());
		return answer(replyMessage);
	}

	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseBody
	public ResponseEntity<ReplyMessage> validationFieldsException(MethodArgumentNotValidException exception, WebRequest webRequest) {
		Map<String, String> mapErrors = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			String clave = ((FieldError) error).getField();
			String valor = error.getDefaultMessage();
			mapErrors.put(clave, valor);
		});
		Collection<String> errors = mapErrors.values();
		List<String> messages = new ArrayList<>(errors);
		replyMessage.setUri(webRequest.getDescription(false).replace("uri=",""));
		replyMessage.setHttpStatus(HttpStatus.BAD_REQUEST);
		replyMessage.setError(true);
		replyMessage.setMessage(messages);
		replyMessage.setResponse(null);
		replyMessage.setDate(LocalDateTime.now());
		return answer(replyMessage);
	}

	@ExceptionHandler({HttpMessageNotReadableException.class})
	@ResponseBody
	public ResponseEntity<ReplyMessage> validationFieldsExceptionBySQL(HttpMessageNotReadableException exception, WebRequest webRequest) {
		List<String> messages = new ArrayList<>();
		String error = exception.getMessage().trim();
		error = error.replace("JSON parse error: ", "");
		if(error.substring(0,20).equals("Unexpected character")) {
			messages.add("Error a leer los datos de entrada, revise que el formato JSON este correcto");
		}
		if(error.substring(0,32).equals("Cannot deserialize value of type")) {
			String[] errors = error.split(" ");
			errors[5] = errors[5].replace("`", "");
			errors[5] = errors[5].split("\\.")[2];
			messages.add("No se puede guardar un valor de tipo " + errors[7] + " en una variable de tipo " + errors[5]);
		}
		replyMessage.setUri(webRequest.getDescription(false).replace("uri=",""));
		replyMessage.setHttpStatus(HttpStatus.BAD_REQUEST);
		replyMessage.setError(true);
		replyMessage.setMessage(messages);
		replyMessage.setResponse(null);
		replyMessage.setDate(LocalDateTime.now());
		return answer(replyMessage);
	}

	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	@ResponseBody
	public ResponseEntity<ReplyMessage> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, WebRequest webRequest) {
		List<String> messages = new ArrayList<>();
		messages.add("Se esperaba que el parámetro " + exception.getName() + " recibiera un tipo de dato " +
				exception.getRequiredType().getSimpleName() + " pero recibió un tipo de dato " + exception.getValue().getClass().getSimpleName());

		replyMessage.setUri(webRequest.getDescription(false).replace("uri=",""));
		replyMessage.setHttpStatus(HttpStatus.BAD_REQUEST);
		replyMessage.setError(true);
		replyMessage.setMessage(messages);
		replyMessage.setResponse(null);
		replyMessage.setDate(LocalDateTime.now());
		return answer(replyMessage);
	}

	private String supportedMethods(HttpRequestMethodNotSupportedException exception) {
		String method = exception.getSupportedHttpMethods().toString();
		method = method.replace("[","");
		method = method.replace("]","");
		return method;
	}
}