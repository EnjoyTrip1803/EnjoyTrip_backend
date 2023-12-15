package com.ssafy.trip.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class SocketController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/chat")
    public void socketChat(Chat chat) {
        chat.setCreateAt(LocalDateTime.now());
        simpMessageSendingOperations.convertAndSend("/topic/channel/"+ chat.getPlanId(), chat);
    }

    @MessageMapping("/plan")
    public void socketPlan(){
//        simpMessageSendingOperations.convertAndSend("/topic/channel/방번호", response값);
    }




}
