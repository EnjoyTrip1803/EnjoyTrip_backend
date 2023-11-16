package com.ssafy.trip.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "User (유저정보)", description = "아이디, 비번, 이름을 가진 Domain Class")
public class User {
	@ApiModelProperty(value = "유저 아이디")
	private String userId;
	@ApiModelProperty(value = "유저 비밀번호")
	private String userPassword;
	@ApiModelProperty(value = "유저 이름")
	private String userName;
	@ApiModelProperty(value = "이메일아이디")
	private String emailId;
	@ApiModelProperty(value = "이메일도메인")
	private String emailDomain;
	@ApiModelProperty(value = "가입일자")
	private String joinDate;
	@ApiModelProperty(value = "refreshToken")
	private String refreshToken;

}
