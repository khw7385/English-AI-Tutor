package me.khw7385.conversation.infrastructure.websocket.openai;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import me.khw7385.conversation.application.port.outbound.Message;
import me.khw7385.conversation.infrastructure.enums.RealtimeEventType;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServerToAiRealtimeMessage(
        @JsonProperty("event_id") String eventId,
        RealtimeEventType type,
        String audio
) implements Message{
    public static ServerToAiRealtimeMessage of(RealtimeEventType type, String base64chunk){
        return ServerToAiRealtimeMessage.builder()
                .type(type)
                .audio(base64chunk)
                .build();
    }
}
