//package com.ssafy.trip.socket;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//@Component
//@RequiredArgsConstructor
//public class RoomManager {
//
//    private final SimpMessageSendingOperations simpMessageSendingOperations;
//    private Map<Integer, Set<String>> rooms = new HashMap<>();
//
//    // 방에 사용자 추가
//    public void addUserToRoom(Integer roomId, String username) {
//        rooms.computeIfAbsent(roomId, k -> new HashSet<>()).add(username);
//        sendRoomParticipantsUpdate(roomId);
//    }
//
//    // 방에서 사용자 제거
//    public void removeUserFromRoom(String roomId, String username) {
//        Set<String> participants = rooms.get(roomId);
//        if (participants != null) {
//            participants.remove(username);
//            sendRoomParticipantsUpdate(roomId);
//        }
//    }
//
//    // 해당 방의 사용자 목록 전송
//    private void sendRoomParticipantsUpdate(Integer roomId) {
//        Set<String> participants = rooms.get(roomId);
//        if (participants != null) {
//            String destination = "/topic/rooms/" + roomId + "/participants";
//            messagingTemplate.convertAndSend(destination, participants);
//        }
//    }
//}