package com.ssafy.trip.sse.model.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {

	SseEmitter connection(String lastEventId, HttpServletResponse response);

	void sendToClient(SseEmitter emitter, String id, Object data);
	
}
