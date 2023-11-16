package com.ssafy.trip.attraction.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.attraction.model.AreaCode;
import com.ssafy.trip.attraction.model.AttractionDescription;
import com.ssafy.trip.attraction.model.SearchCondition;
import com.ssafy.trip.attraction.model.service.AttractionService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/attractionapi")
@CrossOrigin("*")
@Api(tags = {"관광지 컨트롤러 API V1"})
public class AttractionController {

	private AttractionService service;

	@Autowired
	public AttractionController(AttractionService service) {
		super();
		this.service = service;
	}

	@GetMapping(value = "list/{areaCode}")
	public ResponseEntity<?> getAreaCode(@PathVariable int areaCode) {
		log.debug("getAreaCode call");
		try {
			List<AreaCode> list = service.getAreaCode(areaCode);
			if (list != null) {
				return ResponseEntity.
						status(HttpStatus.OK).
						body(list);
			}
			return ResponseEntity.
					status(HttpStatus.OK).
					body(Collections.EMPTY_LIST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping(value = "list/")
	public ResponseEntity<?> listAttraction(SearchCondition serchCondition) {
		log.debug("listAttraction call");
		System.out.println(serchCondition);
		try {
			List<AttractionDescription> list = service.listAttraction(serchCondition);
			if (list != null) {
				return ResponseEntity.
						status(HttpStatus.OK).
						body(list);
			}
			return ResponseEntity.
					status(HttpStatus.OK).
					body(Collections.EMPTY_LIST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping(value = "detail/{contentId}")
	public ResponseEntity<?> detailAttraction(@PathVariable int contentId) {
		log.debug("detailAttraction call");
		try {
			AttractionDescription detail = service.detailAttraction(contentId);
			return ResponseEntity.
					status(HttpStatus.OK).
					body(detail);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
