package com.ssafy.trip.attraction.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.attraction.model.AreaCode;
import com.ssafy.trip.attraction.model.Attraction;
import com.ssafy.trip.attraction.model.AttractionDescription;
import com.ssafy.trip.attraction.model.ContentType;
import com.ssafy.trip.attraction.model.SearchCondition;

@Mapper
public interface AttractionDao {
	List<AreaCode> getSido();
	List<AreaCode> getGugun(int areaCode);
	List<ContentType> getContentType();
	List<Attraction > listAttraction(SearchCondition serchCondition);
	AttractionDescription detailAttraction(int contentId);
}
