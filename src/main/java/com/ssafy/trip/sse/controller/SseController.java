package com.ssafy.trip.sse.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class SseController {

//	private final NotificationService notificationService;

	private static Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
	
	@Operation(summary = "SSE 연결")
	@GetMapping(value = "/subscribe", produces = "text/event-stream")
	public SseEmitter subscribe(@RequestParam String userId,
			@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
		log.debug("## subscribe call");
		log.debug("## userId is " + userId);
//		log.debug("## Emitters is " + sseEmitters);
		
		
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		
		try {
			sseEmitter.send(SseEmitter.event().name("INIT"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sseEmitters.put(userId, sseEmitter);
		
		sseEmitter.onCompletion(()-> sseEmitters.remove(userId));
		sseEmitter.onTimeout(()-> sseEmitters.remove(userId));
		sseEmitter.onError((e)-> sseEmitters.remove(userId));
		
		
		return sseEmitter;
//        return notificationService.subscribe(userId, lastEventId);
	}
	
	@PostMapping(value= "/dispatch")
	public void dispatch(@RequestBody HashMap<String, String> map) {
		log.debug("## dispatch call");
		log.debug("## data is " + map.toString());
		
		String data = new JSONObject(map).toString();
		log.debug("## data is " + data);
		SseEmitter emitter = sseEmitters.get(map.get("receiver"));
		try {
			emitter.send(SseEmitter.event().name("invite").data(data));
		} catch (IOException e) {
			sseEmitters.remove(map.get(map.get("receiver")));
		}
	}
	
}