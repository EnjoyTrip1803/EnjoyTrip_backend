package com.ssafy.trip.attraction.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.attraction.model.AreaCode;
import com.ssafy.trip.attraction.model.AttractionDescription;
import com.ssafy.trip.attraction.model.SearchCondition;
import com.ssafy.trip.attraction.model.dao.AttractionDao;

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
	public List<AttractionDescription> listAttraction(SearchCondition serchCondition) {
		return dao.listAttraction(serchCondition);
	}

	@Override
	public AttractionDescription detailAttraction(int contentId) {
		return dao.detailAttraction(contentId);
	}

}
