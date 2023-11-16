package com.ssafy.trip.attraction.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.attraction.model.AreaCode;
import com.ssafy.trip.attraction.model.AttractionDescription;
import com.ssafy.trip.attraction.model.SearchCondition;

public interface AttractionService {
	List<AreaCode> getAreaCode(int areaCode);

	List<AttractionDescription> listAttraction(SearchCondition serchCondition);

	AttractionDescription detailAttraction(int contentId);
}
