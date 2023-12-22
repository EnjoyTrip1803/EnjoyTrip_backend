package com.ssafy.trip.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class SocketController {
    private Map<Integer, Set<Integer>> rooms = new HashMap<>();
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/chat")
    public void socketChat(Chat chat) {
        chat.setCreateAt(LocalDateTime.now());
        simpMessageSendingOperations.convertAndSend("/topic/channel/"+ chat.getPlanId(), chat);
    }

    @MessageMapping("/plan")
    public void socketPlan(Plan plan){
        simpMessageSendingOperations.convertAndSend("/topic/channel/"+ plan.getPlanId(), plan);
    }

    @MessageMapping("/enter")
    public void socketEnter(Room room) {
        Integer planId = room.getPlanId();
        Integer userId = room.getUserId();

        // 방이 없을때
        if (!rooms.containsKey(planId)){
            rooms.put(planId, new HashSet<>());
        }

        rooms.get(planId).add(userId);
        Member member = new Member();
        member.setType("room");
        member.setMembers(rooms.get(planId));
        simpMessageSendingOperations.convertAndSend("/topic/channel/"+ planId, member);
    }

    @MessageMapping("/exit")
    public void socketExit(Room room) {
        Integer planId = room.getPlanId();
        Integer userId = room.getUserId();
        rooms.get(planId).remove(userId);

        Member member = new Member();
        member.setType("room");
        member.setMembers(rooms.get(room.getPlanId()));
        simpMessageSendingOperations.convertAndSend("/topic/channel/"+ planId, member);
    }




}