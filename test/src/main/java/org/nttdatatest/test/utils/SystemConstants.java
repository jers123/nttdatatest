package org.nttdatatest.test.utils;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.ResponseEntity;

public class SystemConstants {
	public static final String LOCAL_ORIGIN_PATH = "http://localhost:8090";
	public static final String PUBLIC_ORIGIN_PATH = "http://localhost:4200";
	
	public static final String CLIENTE_PATH = "/cliente";
	public static final String GET_PATH = "/{tipo}/{numero}";
	
	// HEADERS
	public static final String ACCEPT = "Accept";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String LOCATION = "Location";
	
	//VARIABLES
	public static final String TYPE = "tipo";
	public static final String NUMBER = "numero";
	
	public static ResponseEntity<ReplyMessage> answer(ReplyMessage replyMessage) {
		return ResponseEntity
				.status(replyMessage.getHttpStatus())
				.header(LOCATION, replyMessage.getUri())
				.header(ACCEPT, APPLICATION_JSON_VALUE)
				.body(replyMessage);
	}
}