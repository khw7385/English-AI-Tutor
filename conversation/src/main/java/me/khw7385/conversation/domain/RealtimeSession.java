package me.khw7385.conversation.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Builder
@Getter
public class RealtimeSession {
    private String clientId;
    private LocalDateTime expiresAt;

    public static RealtimeSession of(String clientId, Integer expiresAt){
        return RealtimeSession.builder()
                .clientId(clientId)
                .expiresAt(LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(expiresAt),
                        ZoneId.systemDefault()))
                .build();
    }
}
