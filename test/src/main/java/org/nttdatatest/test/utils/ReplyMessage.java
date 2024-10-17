package org.nttdatatest.test.utils;

import java.time.LocalDateTime;
import java.util.List;

import org.nttdatatest.test.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ReplyMessage {
	private String uri;
	private HttpStatus httpStatus;
	private Boolean error;
	private List<String> message;
	private LocalDateTime date;
	private Cliente response;
}