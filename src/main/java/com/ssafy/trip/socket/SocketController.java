package com.ssafy.trip.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SocketController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/chat")
    public void socketChat(){
//        simpMessageSendingOperations.convertAndSend("/topic/channel/방번호", response값);
    }

    @MessageMapping("/plan")
    public void socketPlan(){
//        simpMessageSendingOperations.convertAndSend("/topic/channel/방번호", response값);
    }


}
