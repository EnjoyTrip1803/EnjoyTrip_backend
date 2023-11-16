package com.ssafy.trip.user.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.user.model.User;
import com.ssafy.trip.user.model.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	
	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void joinUser(User user) throws Exception {
		userDao.joinUser(user);
	}

	@Override
	public User loginUser(Map<String, String> map) throws Exception {
		return userDao.loginUser(map);
	}

	@Override
	public User findByUserId(String id) throws Exception {
		return userDao.findByUserId(id);
	}

	@Override
	public void updateUser(User user) throws Exception {
		userDao.updateUser(user);
	}

	@Override
	public void deleteUser(String id) throws Exception {
		userDao.deleteUser(id);
	}
	
	@Override
	public User userInfo(String userId) throws Exception {
		return userDao.userInfo(userId);
	}
	
	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		userDao.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return userDao.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		userDao.deleteRefreshToken(map);
	}
}
