package com.ssafy.trip.attraction.model.service;

import java.util.List;

import com.ssafy.trip.attraction.model.AreaCode;
import com.ssafy.trip.attraction.model.Attraction;
import com.ssafy.trip.attraction.model.AttractionDescription;
import com.ssafy.trip.attraction.model.ContentType;
import com.ssafy.trip.attraction.model.SearchCondition;

public interface AttractionService {
	List<AreaCode> getAreaCode(int areaCode);

	List<ContentType> getContentType();

	List<Attraction> listAttraction(SearchCondition serchCondition);

	AttractionDescription detailAttraction(int contentId);
	
}
