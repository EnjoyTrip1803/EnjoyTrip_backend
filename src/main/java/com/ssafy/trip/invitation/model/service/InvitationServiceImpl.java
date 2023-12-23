package com.ssafy.trip.invitation.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.trip.invitation.model.Invitation;
import com.ssafy.trip.invitation.model.dao.InvitationDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

	private final InvitationDao dao;

	
	@Override
	public List<Invitation> getInvitationByName(String name) {
		return dao.getInvitationByName(name);
	}

	@Override
	public int createInvitation(Invitation invitation) {
		return dao.createInvitation(invitation);
	}

	@Override
	public void deleteInvitation(int invitationId) {
		dao.deleteInvitation(invitationId);
	}

}
