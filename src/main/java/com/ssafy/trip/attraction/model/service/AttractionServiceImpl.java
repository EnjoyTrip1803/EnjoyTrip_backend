package com.ssafy.trip.attraction.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.attraction.model.AreaCode;
import com.ssafy.trip.attraction.model.Attraction;
import com.ssafy.trip.attraction.model.AttractionDescription;
import com.ssafy.trip.attraction.model.ContentType;
import com.ssafy.trip.attraction.model.SearchCondition;
import com.ssafy.trip.attraction.model.TripAttraction;
import com.ssafy.trip.attraction.model.TripMember;
import com.ssafy.trip.attraction.model.TripPlan;
import com.ssafy.trip.attraction.model.dao.AttractionDao;
import com.ssafy.trip.user.model.User;

@Service
public class AttractionServiceImpl implements AttractionService {

	private AttractionDao dao;
	
	@Autowired
	public AttractionServiceImpl(AttractionDao attractionDao) {
		super();
		this.dao = attractionDao;
	}

	@Override
	public List<AreaCode> getAreaCode(int areaCode) {
		if(areaCode == 0) {
			return dao.getSido();
		}
		return dao.getGugun(areaCode);
	}

	@Override
	public List<Attraction> listAttraction(SearchCondition serchCondition) {
		return dao.listAttraction(serchCondition);
	}


	public Attraction getAttraction(int contentId) {
		return dao.getAttraction(contentId);
	}
	@Override
	public AttractionDescription detailAttraction(int contentId) {
		return dao.detailAttraction(contentId);
	}

	@Override
	public void registTripPlan(TripPlan trip) throws SQLException {
		dao.registTripPlan(trip);

		TripMember member = new TripMember();
		System.out.println("planId : " + trip.getPlanId());
		member.setPlanId(trip.getPlanId());
		member.setUserId(trip.getUserId());
		member.setStatus(2);
		dao.registTripMember(member);
	}

	@Override
	public void updateTripPlan(TripPlan trip) throws SQLException {
		dao.updateTripPlan(trip);
	}

	@Override
	public TripPlan findTripPlan(int planId) throws SQLException {
		return dao.findTripPlan(planId);
	}
	
	@Override
	public void deleteTripPlan(int planId) throws SQLException {
		dao.deleteTripPlan(planId);
	}

	@Override
	public List<TripPlan> listPublicTripPlan() throws SQLException {
		return dao.listPublicTripPlan();
	}

	@Override
	public List<TripPlan> listUserTripPlan(int userId) throws SQLException {
		return dao.listUserTripPlan(userId);
	}

	@Override
	public void registTripAttraction(TripAttraction tripAttraction) throws SQLException {
		dao.registTripAttraction(tripAttraction);
	}

	@Override
	public void deleteTripAttraction(TripAttraction tripAttraction) throws SQLException {
		dao.deleteTripAttraction(tripAttraction);
	}

	@Override
	public List<Attraction> listTripAttraction(int planId) throws SQLException {
		return dao.listTripAttraction(planId);
	}

	@Override
	public void registTripMember(TripMember tripMember) throws SQLException {
		dao.registTripMember(tripMember);
	}
	
	@Override
	public void updataTripMemberStatus(TripMember tripMember) throws SQLException {
		dao.updataTripMemberStatus(tripMember);
	}
	
	@Override
	public TripMember findTripMember(TripMember tripMember) throws SQLException {
		return dao.findTripMember(tripMember);
	}

	@Override
	public void deleteTripMember(TripMember tripMember) throws SQLException {
		dao.deleteTripMember(tripMember);
	}

	@Override
	public List<User> listTripMember(int planId) throws SQLException {
		return dao.listTripMember(planId);
	}

	@Override
	public List<ContentType> getContentType() {
		return dao.getContentType();
	}

	@Override
	public List<Integer> listInvitation(int userId) throws SQLException {
		return dao.listInvitation(userId);
	}

}
