package com.ssafy.trip.socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    private String type;
    private int planId;
    private String userName;
    private String message;
    private LocalDateTime createAt;
}
