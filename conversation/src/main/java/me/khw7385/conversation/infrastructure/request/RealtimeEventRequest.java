package me.khw7385.conversation.infrastructure.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import me.khw7385.conversation.infrastructure.enums.RealtimeEventType;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RealtimeEventRequest(
        @JsonProperty("event_id") String eventId,
        RealtimeEventType type,
        String audio
) {
    public static RealtimeEventRequest of(RealtimeEventType type, String base64chunk){
        return RealtimeEventRequest.builder()
                .type(type)
                .audio(base64chunk)
                .build();
    }
}
