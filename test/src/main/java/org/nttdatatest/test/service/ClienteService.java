package org.nttdatatest.test.service;

import static org.nttdatatest.test.utils.Constants.CLIENTE_EXISTS;
import static org.nttdatatest.test.utils.Constants.CLIENTE_NOT_EXISTS;
import static org.nttdatatest.test.utils.Constants.TYPE_NOT_VALID;
import static org.nttdatatest.test.utils.SystemConstants.CLIENTE_PATH;
import static org.nttdatatest.test.utils.SystemConstants.NUMBER;
import static org.nttdatatest.test.utils.SystemConstants.TYPE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.nttdatatest.test.model.Cliente;
import org.nttdatatest.test.utils.ReplyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	@Autowired
	private ReplyMessage replyMessage;
	
	public ReplyMessage getCliente(String tipo, Integer numero) {
		replyMessage.setUri(CLIENTE_PATH + "?" + TYPE + "=" + tipo + "&" + NUMBER);
		replyMessage.setError(true);
		replyMessage.setResponse(null);
		List<String> messages = new ArrayList<>();
		if (tipo.toUpperCase().equals("C") || tipo.toUpperCase().equals("P")) {
			Cliente cliente = new Cliente("C", 23445322, "Jhenny", "Patricia", "Pérez", "Holgin", "3108891469", "Calle 100 #30 - 01", "Bogotá");
			if(tipo.toUpperCase().equals(cliente.getTipoDocumento()) && numero.equals(cliente.getNumeroDocumento())) {
				replyMessage.setHttpStatus(HttpStatus.OK);
				replyMessage.setResponse(cliente);
				replyMessage.setError(false);
				messages.add(CLIENTE_EXISTS);
			} else {
				messages.add(CLIENTE_NOT_EXISTS);
				replyMessage.setHttpStatus(HttpStatus.NOT_FOUND);
			}
		} else {
			messages.add(TYPE_NOT_VALID);
			replyMessage.setHttpStatus(HttpStatus.BAD_REQUEST);
		}
		replyMessage.setMessage(messages);
		replyMessage.setDate(LocalDateTime.now());
		return replyMessage;
	}
}