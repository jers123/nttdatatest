package org.nttdatatest.test.controller;

import static org.nttdatatest.test.utils.SystemConstants.answer;

import org.nttdatatest.test.annotation.RestApi;
import org.nttdatatest.test.service.ClienteService;
import org.nttdatatest.test.utils.ReplyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestApi
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@GetMapping()
	public ResponseEntity<ReplyMessage> getCliente(@RequestParam String tipo, @RequestParam Integer numero) {
		try {
			return answer(service.getCliente(tipo, numero));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}