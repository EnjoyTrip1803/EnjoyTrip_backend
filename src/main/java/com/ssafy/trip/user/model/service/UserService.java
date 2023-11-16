package com.ssafy.trip.user.model.service;

import java.util.Map;

import com.ssafy.trip.user.model.User;

public interface UserService {
	void joinUser(User user) throws Exception;
	User loginUser(Map<String, String> map) throws Exception;
	User findByUserId(String id) throws Exception;
	void updateUser(User user) throws Exception;
	void deleteUser(String id) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;
	User userInfo(String userId) throws Exception;
}
