package com.ssafy.trip.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class SocketController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/chat")
    public void socketChat(Chat chat, StompHeaderAccessor session) {
        chat.setCreateAt(LocalDateTime.now());
        System.out.println(session.getSessionAttributes().get("userId"));
        simpMessageSendingOperations.convertAndSend("/topic/channel/"+ chat.getPlanId(), chat);
    }

    @MessageMapping("/plan")
    public void socketPlan(Plan plan){
        simpMessageSendingOperations.convertAndSend("/topic/channel/"+ plan.getPlanId(), plan);
    }

    @MessageMapping("/enter")
    public void socketPlan(Plan plan){
        simpMessageSendingOperations.convertAndSend("/topic/channel/"+ plan.getPlanId(), plan);
    }

}
