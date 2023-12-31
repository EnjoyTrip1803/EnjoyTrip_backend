package com.ssafy.trip.sse.model.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.trip.attraction.model.TripMember;

public interface SseService {
	SseEmitter subscribe(int userId);
	void notify(TripMember tripMember);
	void sendToClient(int userId, String event, String name); 
	SseEmitter createEmitter(int id);
//	SseEmitter connection(String lastEventId, HttpServletResponse response);
//
//	void sendToClient(SseEmitter emitter, String id, Object data);
//	
}
