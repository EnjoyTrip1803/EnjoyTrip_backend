package com.ssafy.trip.invitation.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "invitation(초대 정보)", description = "초대장 구성 데이터")
public class Invitation{
	private int invitationId;
    private String senderName;
    private String receiverName;
    private Integer planId;
}
