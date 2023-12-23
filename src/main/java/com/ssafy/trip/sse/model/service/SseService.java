package com.ssafy.trip.sse.model.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.trip.invitation.model.Invitation;

public interface SseService {
	SseEmitter subscribe(String userId);
	void notify(Invitation invitation);
	void sendToClient(String userId, String event, String name); 
	SseEmitter createEmitter(String id);
//	SseEmitter connection(String lastEventId, HttpServletResponse response);
//
//	void sendToClient(SseEmitter emitter, String id, Object data);
//	
}
