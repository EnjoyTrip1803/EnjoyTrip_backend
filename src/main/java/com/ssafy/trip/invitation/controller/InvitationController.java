package com.ssafy.trip.invitation.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.invitation.model.Invitation;
import com.ssafy.trip.invitation.model.service.InvitationService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/invitation")
@CrossOrigin
@RequiredArgsConstructor
@Api(tags = {"Invitation 컨트롤러 API V1"})
public class InvitationController {

	private final InvitationService service;

	@GetMapping()
	public List<Invitation> getInvitationList(@RequestParam String name) {
		log.debug("## Controller getInvitationList call");
		log.debug("## name is " + name);

		return service.getInvitationByName(name);		
	}
	
	@PostMapping()
	public int createInvitation(@RequestBody Invitation invitation) {
		log.debug("## Controller createInvitation call");
		log.debug("## Invitation is " + invitation.toString());
		
		service.createInvitation(invitation);
		log.debug("## InvitationId is " + invitation.getInvitationId());
		return invitation.getInvitationId();
//		return service.createInvitation(invitation);
	}

	@DeleteMapping()
	public void deleteInvitation(@RequestParam int invitationId) {
		log.debug("## Controller deleteInvitation call");
		log.debug("## invitationId is " + invitationId);
		
		service.deleteInvitation(invitationId);
	}
}