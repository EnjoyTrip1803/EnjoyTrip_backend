package com.ssafy.trip.invitation.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.invitation.model.Invitation;

@Mapper
public interface InvitationDao {
	List<Invitation> getInvitationByName(String name);
	int createInvitation(Invitation Invitation);
	void deleteInvitation(int InvitationId);
}
