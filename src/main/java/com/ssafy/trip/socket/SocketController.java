package com.ssafy.trip.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SocketController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/socket")
    public void socket(){
//        simpMessageSendingOperations.convertAndSend("/topic/방번호", response값);
    }
}
