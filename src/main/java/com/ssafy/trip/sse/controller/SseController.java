package com.ssafy.trip.sse.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.trip.attraction.model.TripMember;
import com.ssafy.trip.sse.model.service.SseService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@Api(tags = {"SSE 컨트롤러 API V1"})
public class SseController {

	private final SseService service;

	@Operation(summary = "SSE 연결")
	@GetMapping(value = "/subscribe", produces = "text/event-stream")
	public SseEmitter subscribe(@RequestParam int userId,
			@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
		log.debug("## SseController subscribe call");
		log.debug("## userId is " + userId);

		return service.subscribe(userId);		
	}
	
	@PostMapping(value= "/dispatch")
	public void dispatch(@RequestBody TripMember tripMember) {
		log.debug("## SseController dispatch call");
		log.debug("## data is " + tripMember.toString());
		
		service.notify(tripMember);
	}
	
}