package com.ssafy.trip.invitation.model.service;

import java.util.List;

import com.ssafy.trip.invitation.model.Invitation;

public interface InvitationService {
	List<Invitation> getInvitationByName(String name);
	int createInvitation(Invitation invitation);
	void deleteInvitation(int invitationId);
}
