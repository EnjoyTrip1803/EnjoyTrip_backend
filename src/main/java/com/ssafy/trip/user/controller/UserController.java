package com.ssafy.trip.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.user.model.User;
import com.ssafy.trip.user.model.service.UserService;
import com.ssafy.trip.util.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin("*")
@Api(tags = {"유저 컨트롤러 API V1"})
public class UserController {
	
	private UserService userService;
	private JWTUtil jwtUtil;
	
	@Autowired
	public UserController(UserService userService, JWTUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	@ApiOperation(value = "유저 등록", notes = "유저 정보를 받아 등록")
	@PostMapping("/join")
	public ResponseEntity<?> userRegister(User user){
		log.debug("userRegister User : {}", user);
		try {
			userService.joinUser(user);
			Map<String, String> result = new HashMap<>();
			result.put("msg", "가입 완료");

			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	

//	@ApiOperation(value = "유저 로그인", notes = "유저 정보를 받아 로그인 처리")
//	@PostMapping("/login")
//	public ResponseEntity<?> userLogin(@RequestParam Map<String, String> map){
//		log.debug("login map : {}", map);
//		System.out.println(map);
//		try {
//			User user = userService.loginUser(map);
//			return ResponseEntity.status(HttpStatus.OK).body(user);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
	
	@ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> userLogin(@RequestBody Map<String, String> map) {
		log.debug("login user : {}", map);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			User loginUser = userService.loginUser(map);
			if(loginUser != null) {
				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
				log.debug("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);
				
//				발급받은 refresh token을 DB에 저장.
				userService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				
//				JSON으로 token 전달.
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				
				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			} 
			
		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	

	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				User user = userService.userInfo(userId);
				resultMap.put("userInfo", user);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable ("userId") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody User user, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, memberDto : {}", token, user);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(user.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(user.getUserId());
				log.debug("token : {}", accessToken);
				log.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			log.debug("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	
	
	@ApiOperation(value = "유저 로그아웃", notes = "유저 아이디를 받아 로그아웃 처리")
	@PostMapping("/logout")
	public ResponseEntity<?> userLogout(String userId){
		try {
			Map<String, String> result = new HashMap<>();
			result.put("msg", "로그아웃 완료");
			if(userId != "") return ResponseEntity.status(HttpStatus.OK).body(result);
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
			}
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "유저 정보", notes = "유저 아이디를 받아 해당 유저 정보 반환")
	@GetMapping("/{userId}")
	public ResponseEntity<?> userInfo(@PathVariable String userId){
		try {
			User user = userService.findByUserId(userId);
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "유저 정보 업데이트", notes = "수정할 데이터를 받아 유저 정보 업데이트")
	@PutMapping("/modify")
	public ResponseEntity<?> userUpdate(User user){
		try {
			userService.updateUser(user);
			Map<String, String> result = new HashMap<>();
			result.put("msg", "유저정보 수정 완료");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "유저 삭제", notes = "유저 아이디를 받아 유저 정보 삭제")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> userDelete(@PathVariable String userId){
		try {
			userService.deleteUser(userId);
			Map<String, String> result = new HashMap<>();
			result.put("msg", "유저 삭제 완료");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
