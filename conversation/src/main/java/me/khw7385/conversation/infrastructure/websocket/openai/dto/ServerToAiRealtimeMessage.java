package me.khw7385.conversation.infrastructure.websocket.openai.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import me.khw7385.conversation.application.port.outbound.Message;
import me.khw7385.conversation.infrastructure.enums.RealtimeEventType;

import static me.khw7385.conversation.infrastructure.enums.RealtimeEventType.INPUT_AUDIO_BUFFER_APPEND;
import static me.khw7385.conversation.infrastructure.enums.RealtimeEventType.SESSION_UPDATE;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServerToAiRealtimeMessage(
        @JsonProperty("event_id") String eventId,
        RealtimeEventType type,
        String audio,
        Session session
) implements Message{
    public static ServerToAiRealtimeMessage createAudioAppendMessage(String base64chunk){
        return ServerToAiRealtimeMessage.builder()
                .type(INPUT_AUDIO_BUFFER_APPEND)
                .audio(base64chunk)
                .build();
    }

    public static ServerToAiRealtimeMessage createSessionUpdateMessage(String instructions){
        return ServerToAiRealtimeMessage.builder()
                .type(SESSION_UPDATE)
                .session(new Session(instructions))
                .build();
    }

    private record Session(
            String instructions
    ){
    }
}
