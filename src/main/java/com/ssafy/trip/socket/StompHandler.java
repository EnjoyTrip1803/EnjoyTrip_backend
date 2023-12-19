package com.ssafy.trip.socket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class StompHandler implements ChannelInterceptor {

    private Map<Integer, Set<String>> rooms = new HashMap<>();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        switch (accessor.getCommand()) {
            case CONNECT:
                // 유저가 Websocket으로 connect()를 한 뒤 호출됨
                System.out.println("---연결---");
                Map<String, Object> attributes = accessor.getSessionAttributes();
                attributes.put("userId", accessor.getFirstNativeHeader("userId"));
                System.out.println(accessor.getFirstNativeHeader("userId"));
                accessor.setSessionAttributes(attributes);
                System.out.println(message.getPayload());
                System.out.println(accessor.getSessionAttributes());
                System.out.println("---------");
                break;
            case DISCONNECT:
                System.out.println("---연결끊김---");
                System.out.println(message.getHeaders());
                System.out.println("---------");
                // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
                break;
            case SEND:
                System.out.println("---전송---");
                byte[] payload = (byte[]) message.getPayload();
                String text = new String(payload, StandardCharsets.UTF_8);
                System.out.println(text);
                System.out.println("---------");
            default:
                break;
        }


        return message;
    }
}